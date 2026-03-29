package com.jpinto.orchestator.client.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestSendMail {

    private List<String> toEmail;

    private List<String> ccEmail;

    private String body;

    private String subjet;

}
