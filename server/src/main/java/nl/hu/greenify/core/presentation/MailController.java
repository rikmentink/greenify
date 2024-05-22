package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.MailService;
import nl.hu.greenify.core.presentation.dto.MailDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody MailDto emailDTO) {
        mailService.sendSimpleMessage(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody());
    }
}
