package com.jpinto.notifications.dto;

import com.jpinto.notifications.constants.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSendMail {

    @Valid
    @NotEmpty(message = "Lista de emails no puede ser invalido")
    private List<@Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid mail") String> toEmail;

    @Valid
    private List<@Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid mail")String> ccEmail;

    @Valid
    @NotBlank(message = "Indique el cuerpo del mensaje")
    private String body;

    @Valid
    @NotBlank(message = "Debe indicar el asunto de la notificación")
    private String subjet;

}
