package com.jpinto.notifications.controller;

import com.jpinto.notifications.dto.RequestSendMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {

    @PostMapping("/encolar-envio-mail")
    public ResponseEntity<Boolean> encolarEnvioHtmlMail(@RequestBody RequestSendMail request) {
        log.info(" {} Recibiendo peticion encolar-envio-mail ", Thread.currentThread());
        log.info("Mail enviado {}", request);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
