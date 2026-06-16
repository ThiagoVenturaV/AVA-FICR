package com.ava.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "protocolos")
@Getter
@Setter
@NoArgsConstructor
public class Protocolo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo do protocolo é obrigatório")
    private TipoProtocolo tipo;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProtocolo status = StatusProtocolo.EM_ANALISE;
    
    @NotNull
    private LocalDateTime dataSolicitacao = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "analisado_por_id")
    private Secretaria analisadoPor;
}
