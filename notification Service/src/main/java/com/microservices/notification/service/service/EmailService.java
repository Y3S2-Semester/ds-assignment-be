package com.microservices.notification.service.service;

import com.microservices.notification.service.dto.MailSenderDto;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendTimetableEmail(MailSenderDto mailSenderDto) throws MessagingException;
}
