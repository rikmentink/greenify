package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.MailService;
import nl.hu.greenify.core.presentation.dto.MailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController extends Controller {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<?> sendEmail(@RequestBody MailDto emailDTO) {
        return this.createResponse(
            this.mailService.sendSimpleMessage(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody())
        );
    }
}
