package system.management.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.management.school.services.EmailService;

/**
 * For testing purpose only, email will be automatically sent to all users once a week on friday at 7:00 AM
 */
@RestController
@RequestMapping("api/")
public class MailController {

    private EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> sendEmailMessage() {
        emailService.sendEmail();
        return ResponseEntity.ok("Email sending function executed successfully");
    }
}
