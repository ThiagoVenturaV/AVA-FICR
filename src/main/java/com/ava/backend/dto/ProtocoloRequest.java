package com.ava.backend.dto;

import com.ava.backend.model.TipoProtocolo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocoloRequest {
    @NotNull(message = "Tipo de protocolo é obrigatório")
    private TipoProtocolo tipo;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    
    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;
}
