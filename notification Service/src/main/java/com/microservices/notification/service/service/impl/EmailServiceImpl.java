package com.microservices.notification.service.service.impl;

import com.microservices.notification.service.dto.MailSenderDto;
import com.microservices.notification.service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${notification.mail.cc}")
    private String cc;

    @Value("${notification.mail.bcc}")
    private String bcc;



    public void sendTimetableEmail(MailSenderDto mailSenderDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(mailSenderDto.getReceiver());
        helper.setCc(cc);
        helper.setBcc(bcc);
        helper.setSubject(mailSenderDto.getSubject());
        helper.setText(mailSenderDto.getMessage());

        javaMailSender.send(message);
    }
}
