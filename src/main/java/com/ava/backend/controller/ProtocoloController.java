package com.ava.backend.controller;

import com.ava.backend.dto.ProtocoloRequest;
import com.ava.backend.model.Protocolo;
import com.ava.backend.service.ProtocoloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/protocolos")
@CrossOrigin(origins = "*")
public class ProtocoloController {

    @Autowired
    private ProtocoloService protocoloService;

    @PostMapping
    public ResponseEntity<Protocolo> criar(@Valid @RequestBody ProtocoloRequest request) {
        return new ResponseEntity<>(protocoloService.criar(request), HttpStatus.CREATED);
    }

    @PutMapping("/{protocoloId}/analisar")
    public ResponseEntity<Protocolo> analisar(
            @PathVariable Long protocoloId, @RequestParam String status, @RequestParam Long secretariaId) {
        return ResponseEntity.ok(protocoloService.analisar(protocoloId, status, secretariaId));
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<Protocolo>> listarPorAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(protocoloService.listarPorAluno(alunoId));
    }

    @GetMapping
    public ResponseEntity<List<Protocolo>> listarTodos() {
        return ResponseEntity.ok(protocoloService.listarTodos());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Protocolo> atualizar(@PathVariable Long id, @RequestBody ProtocoloRequest request) {
        return ResponseEntity.ok(protocoloService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        protocoloService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
