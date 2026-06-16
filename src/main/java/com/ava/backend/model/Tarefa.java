package com.ava.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
@Getter
@Setter
@NoArgsConstructor
public class Tarefa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    private String descricao;
    
    @NotNull(message = "Data de entrega é obrigatória")
    private LocalDateTime dataEntrega;
    
    @NotNull(message = "Pontos máximos são obrigatórios")
    @Min(value = 0, message = "Pontos máximos não podem ser negativos")
    private Double pontosMaximos;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criado_por_id", nullable = false)
    private Pessoa criadoPor;
}
