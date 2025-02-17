package com.ongi.backend.common.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    public void sendSimpleMail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false); // HTML이 아니므로 false

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

    public void sendSimpleMailToAdmin(String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(adminEmail);
            helper.setSubject(subject);
            helper.setText(text, false); // HTML이 아니므로 false

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

    /**
     * HTML 이메일 전송
     */
    public void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // HTML 포맷이므로 true

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }
}
