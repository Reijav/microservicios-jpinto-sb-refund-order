package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.NotificationRestClientService;
import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.exceptions.ServicioNotificacionesException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRestClientService notificationRestClientService;

    @Retry(name = "defaultRetry", fallbackMethod = "notificationFallback")
    public Boolean encolarEnvioHtmlMail(RequestSendMail request) {
        return notificationRestClientService.encolarEnvioHtmlMail(request);
    }

    public Boolean notificationFallback(RequestSendMail request , RuntimeException ex){
        throw new ServicioNotificacionesException("Error servicio de notificaciones :  " +  ExceptionUtils.getRootCauseMessage(ex));
    }
}
