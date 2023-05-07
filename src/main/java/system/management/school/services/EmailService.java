package system.management.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import system.management.school.models.User;
import system.management.school.repository.UserRepository;

import java.util.List;

/**
 * Sends email to all users teachers and students for friday event in a school at 7:00 AM on every friday
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    //    this function gets executed every friday at 7:00 AM
    @Scheduled(cron = "0 0 7 ? * FRI")
    public void sendEmail() {
        logger.info("Sending email to all users");
        try {
            List<User> users = userRepository.findAll();

            for (User user : users) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Friday event");
                message.setText("Hello " + user.getUsername() + " " + "you are invited to attend the program on friday in our university");
                javaMailSender.send(message);

            }
            logger.info("Email sent to all users successfully");

        } catch (MailException e) {
            logger.error("Error occurred while sending email to users", e);
        }

    }

}
