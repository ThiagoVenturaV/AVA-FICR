package com.ava.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RendimentoResponse {
    private String disciplinaNome;
    private Double media;
    private String status; // APROVADO, PENDENTE
    private List<TarefaNotaDto> tarefas;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TarefaNotaDto {
        private String tarefaTitulo;
        private Double pontosMaximos;
        private Double notaObtida;
        private String statusEntrega;
    }
}
