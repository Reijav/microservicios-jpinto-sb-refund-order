package com.jpinto.orchestator.client.notification;

import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRestClientService {
    private final RestClient notificationRestClient;

    public Boolean encolarEnvioHtmlMail( RequestSendMail request) {
        log.info("Calling send mail.");
        return notificationRestClient.post().uri("/email/encolar-envio-mail").body(request)
                .retrieve()
                .body(Boolean.class);
    }
}
