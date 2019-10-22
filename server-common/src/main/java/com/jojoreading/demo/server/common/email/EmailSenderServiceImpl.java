package com.jojoreading.demo.server.common.email;

import com.jojoreading.demo.server.common.config.MailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private MailConfig mailConfig;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailConfig.getMailFrom());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        log.info("Send mail, message={}", message);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.warn("Send mail fail", e);
            return false;
        }

        log.info("Send mail success");
        return true;
    }
}
