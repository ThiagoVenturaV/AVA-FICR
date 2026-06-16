package com.ava.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ALUNO")
@Getter
@Setter
@NoArgsConstructor
public class Aluno extends Pessoa {

    @ManyToMany(mappedBy = "alunos", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Disciplina> disciplinas = new ArrayList<>();
}
