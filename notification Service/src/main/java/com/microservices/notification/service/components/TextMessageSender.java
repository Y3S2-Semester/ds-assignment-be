package com.microservices.notification.service.components;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextMessageSender {

    @Autowired
    private TwilioRestClient twilioRestClient;

    @Value("${notification.text.number}")
    private String fromNumber;

    public void sendTextMessage(String toNumber, String messageBody) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+94769654581"),
                new com.twilio.type.PhoneNumber(fromNumber),
                messageBody
        ).create(twilioRestClient);
        System.out.println("Message SID: " + message.getSid());
    }
}