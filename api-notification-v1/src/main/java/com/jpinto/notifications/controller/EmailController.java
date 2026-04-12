package com.jpinto.notifications.controller;

import com.jpinto.notifications.dto.RequestSendMail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.random.RandomGenerator;

@RestController
@RequestMapping("/email")
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    private final Long timeoutSendmail;

    @Value("${apply.randomerror}")
    private boolean applyRandomError;

    @PostMapping("/encolar-envio-mail")
    public ResponseEntity<Boolean> encolarEnvioHtmlMail(@RequestBody @Valid RequestSendMail request) {
        log.info(" {} Recibiendo peticion encolar-envio-mail ", Thread.currentThread());
        log.info("Mail enviado {}", request);


            try {
                Thread.sleep(timeoutSendmail);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        var valor=Math.abs( RandomGenerator.getDefault().nextInt() % 100);
        if(applyRandomError && valor > 60){
            log.error("Error random generated");
            throw new RuntimeException("Error enviando mail");
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
