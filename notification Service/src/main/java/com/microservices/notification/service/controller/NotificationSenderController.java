package com.microservices.notification.service.controller;

import com.microservices.notification.service.components.TextMessageSender;
import com.microservices.notification.service.dto.MailSenderDto;
import com.microservices.notification.service.dto.TextMessageDto;
import com.microservices.notification.service.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/notification")
@Slf4j
public class NotificationSenderController {

    @Autowired
    TextMessageSender textMessageSender;

    @Autowired
    EmailService mailSenderService;

    @GetMapping("/textSender")
    public void sendTextMessage(@RequestBody TextMessageDto textMessageDto){
        log.info("NotificationSenderController.sendTextMessage() invoked.");
        textMessageSender.sendMessage("+94769654581","First message");
    }
    @GetMapping("/mailSender")
    public void sendTextMessage(@RequestBody MailSenderDto mailSenderDto) throws MessagingException {
        log.info("NotificationSenderController.sendTextMessage() invoked.");
        mailSenderService.sendTimetableEmail(mailSenderDto);
    }

}
