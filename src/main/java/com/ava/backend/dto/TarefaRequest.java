package com.ava.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TarefaRequest {
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    private String descricao;
    
    @NotNull(message = "Data de entrega é obrigatória")
    private LocalDateTime dataEntrega;
    
    @NotNull(message = "Pontos máximos são obrigatórios")
    @Min(value = 0, message = "Pontos máximos não podem ser negativos")
    private Double pontosMaximos;
    
    @NotNull(message = "ID da disciplina é obrigatório")
    private Long disciplinaId;
    
    @NotNull(message = "ID do criador é obrigatório")
    private Long criadoPorId;
}
