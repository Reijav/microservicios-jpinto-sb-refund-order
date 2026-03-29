package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.NotificationRestClientService;
import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRestClientService notificationRestClientService;

    public Boolean encolarEnvioHtmlMail(RequestSendMail request) {
        return notificationRestClientService.encolarEnvioHtmlMail(request);
    }
}
