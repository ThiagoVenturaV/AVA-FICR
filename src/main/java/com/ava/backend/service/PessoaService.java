package com.ava.backend.service;

import com.ava.backend.dto.LoginRequest;
import com.ava.backend.dto.LoginResponse;
import com.ava.backend.dto.PessoaRequest;
import com.ava.backend.exception.BusinessRuleException;
import com.ava.backend.exception.ResourceNotFoundException;
import com.ava.backend.model.*;
import com.ava.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa cadastrar(PessoaRequest request) {
        if (pessoaRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessRuleException("Email já cadastrado");
        }
        if (pessoaRepository.findByCpf(request.getCpf()).isPresent()) {
            throw new BusinessRuleException("CPF já cadastrado");
        }

        Pessoa novaPessoa;
        switch (request.getRole().toUpperCase()) {
            case "ALUNO":
                novaPessoa = new Aluno();
                break;
            case "PROFESSOR":
                novaPessoa = new Professor();
                break;
            case "SECRETARIA":
                novaPessoa = new Secretaria();
                break;
            default:
                throw new BusinessRuleException("Role inválida. Deve ser ALUNO, PROFESSOR ou SECRETARIA.");
        }

        novaPessoa.setNome(request.getNome());
        novaPessoa.setEmail(request.getEmail());
        novaPessoa.setCpf(request.getCpf());
        novaPessoa.setSenha(request.getSenha()); // Senha em texto plano para fins acadêmicos

        return pessoaRepository.save(novaPessoa);
    }

    public LoginResponse login(LoginRequest request) {
        Pessoa pessoa = pessoaRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!pessoa.getSenha().equals(request.getSenha())) {
            throw new BusinessRuleException("Senha incorreta");
        }

        String role = "";
        if (pessoa instanceof Aluno) {
            role = "ALUNO";
        } else if (pessoa instanceof Professor) {
            role = "PROFESSOR";
        } else if (pessoa instanceof Secretaria) {
            role = "SECRETARIA";
        }

        return new LoginResponse(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), role);
    }
}
