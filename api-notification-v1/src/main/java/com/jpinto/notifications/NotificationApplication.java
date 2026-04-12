package com.jpinto.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class NotificationApplication {

    @Bean("timeoutSendmail")
    @ConditionalOnProperty(name = "apply.timeout",   havingValue = "false")
    public Long timeout(){
        return 10L;
    }

    @Bean("timeoutSendmail")
    @ConditionalOnProperty(name = "apply.timeout",   havingValue = "true")
    public Long timeout2(){
        return 2000L;
    }

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

}
