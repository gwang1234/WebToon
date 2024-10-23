package org.example.webtoonepics.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNewPasswordEmail(String toEmail, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("qoi11@naver.com");
        message.setTo(toEmail);
        message.setSubject("[웹툰 에픽스] 새 비밀번호");
        message.setText("회원님의 새 비밀번호: " + newPassword);
        mailSender.send(message);
    }
}