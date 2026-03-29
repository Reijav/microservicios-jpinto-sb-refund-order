package com.jpinto.orchestator.client.talenthuman.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestEmployee {
    private Long id;
    private Long userId;
}
