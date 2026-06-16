package com.ava.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaRequest {
    @NotBlank(message = "Resposta textual é obrigatória para entrega")
    private String respostaText;
}
