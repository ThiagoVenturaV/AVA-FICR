package com.ava.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliarRequest {
    @NotNull(message = "Nota é obrigatória")
    @Min(value = 0, message = "Nota não pode ser negativa")
    private Double nota;
    
    private String feedback;
    
    @NotNull(message = "ID do professor é obrigatório")
    private Long professorId;
}
