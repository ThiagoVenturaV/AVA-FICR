package com.ava.backend.controller;

import com.ava.backend.model.Disciplina;
import com.ava.backend.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
@CrossOrigin(origins = "*")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<Disciplina> criar(@RequestParam String nome, @RequestParam Long professorId) {
        return new ResponseEntity<>(disciplinaService.criar(nome, professorId), HttpStatus.CREATED);
    }

    @PostMapping("/{disciplinaId}/matricular/{alunoId}")
    public ResponseEntity<Disciplina> matricularAluno(@PathVariable Long disciplinaId, @PathVariable Long alunoId) {
        return ResponseEntity.ok(disciplinaService.matricularAluno(disciplinaId, alunoId));
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listarTodos() {
        return ResponseEntity.ok(disciplinaService.listarTodos());
    }
}
