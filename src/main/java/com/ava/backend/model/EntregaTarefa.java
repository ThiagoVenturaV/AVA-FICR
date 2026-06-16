package com.ava.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "entrega_tarefas")
@Getter
@Setter
@NoArgsConstructor
public class EntregaTarefa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tarefa_id", nullable = false)
    private Tarefa tarefa;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status = StatusTarefa.PENDENTE;
    
    private LocalDateTime dataEntrega;
    
    @Column(columnDefinition = "TEXT")
    private String respostaText;
    
    private Double nota;
    
    private String feedback;
}
