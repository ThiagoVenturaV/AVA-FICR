package com.ava.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "avisos")
@Getter
@Setter
@NoArgsConstructor
public class Aviso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Título do aviso é obrigatório")
    private String titulo;
    
    @NotBlank(message = "Conteúdo do aviso é obrigatório")
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    
    @NotNull
    private LocalDateTime dataPublicacao = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criado_por_id", nullable = false)
    private Secretaria criadoPor;
}
