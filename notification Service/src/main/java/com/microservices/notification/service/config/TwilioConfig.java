package com.microservices.notification.service.config;

import com.microservices.notification.service.components.TextMessageSender;
import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${notification.text.twillio_account_sid}")
    private String twillio_account_sid;

    @Value("${notification.text.twillio_account_token}")
    private String twillio_account_token;
    @Bean
    public TwilioRestClient twilioRestClient() {
        Twilio.init(twillio_account_sid, twillio_account_token);
        return Twilio.getRestClient();
    }
    @Bean
    public TextMessageSender textMessageSender() {
        return new TextMessageSender();
    }

}