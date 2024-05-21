package nl.hu.greenify.security.presentation;

import nl.hu.greenify.security.application.AccountService;
import nl.hu.greenify.security.data.AccountRepository;
import nl.hu.greenify.security.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private final Account account = new Account("testuser@gmail.com", "testpassworD1!", null);

    @Test
    @DisplayName("Register account")
    public void registeringAnAccount() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\"}", account.getEmail(), account.getPassword(), firstName, lastName);

        var request = post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(account.getEmail()))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_USER"));
    }

    @Test
    @DisplayName("Cannot register if user exists")
    public void registerWithUserThatDoesntExists() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\"}", account.getEmail(), account.getEmail(), firstName, lastName);

        var request = post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        mockMvc.perform(request)
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Login with correct credentials")
    public void loginWithCorrectCredentials() throws Exception {
        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", account.getEmail(), account.getPassword());
        accountService.register(account.getEmail(), account.getPassword(), "John", "Doe");

        var request = post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @BeforeEach
    @AfterEach
    void cleanUpBeforeAndAfterEachCase() {
        accountRepository.findByEmail("testuser@gmail.com").ifPresent(accountRepository::delete);
    }

}
