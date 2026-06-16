package com.ava.backend.controller;

import com.ava.backend.dto.TarefaRequest;
import com.ava.backend.model.Tarefa;
import com.ava.backend.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody TarefaRequest request) {
        return new ResponseEntity<>(tarefaService.criar(request), HttpStatus.CREATED);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<Tarefa>> listarPorDisciplina(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(tarefaService.listarPorDisciplina(disciplinaId));
    }
}
