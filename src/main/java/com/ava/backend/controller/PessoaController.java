package com.ava.backend.controller;

import com.ava.backend.dto.PessoaRequest;
import com.ava.backend.model.Pessoa;
import com.ava.backend.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody PessoaRequest request) {
        return new ResponseEntity<>(pessoaService.cadastrar(request), HttpStatus.CREATED);
    }
}
