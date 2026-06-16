package com.ava.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String nome;
    private String email;
    private String role; // ALUNO, PROFESSOR, SECRETARIA
}
