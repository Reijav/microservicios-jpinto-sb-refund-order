package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.NotificationRestClientService;
import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRestClientService notificationRestClientService;

    @Retry(name = "defaultRetry", fallbackMethod = "notificationFallback")
    public Boolean encolarEnvioHtmlMail(RequestSendMail request) {
        return notificationRestClientService.encolarEnvioHtmlMail(request);
    }

    public Boolean notificationFallback(RequestSendMail request , Throwable ex){
        throw new RuntimeException("Error servicio de notificaciones :  " + ex.getMessage());
    }
}
