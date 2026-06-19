package com.ava.backend.controller;

import com.ava.backend.model.Aviso;
import com.ava.backend.service.AvisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/avisos")
@CrossOrigin(origins = "*")
public class AvisoController {

    @Autowired
    private AvisoService avisoService;

    @PostMapping
    public ResponseEntity<Aviso> criar(
            @RequestParam String titulo, @RequestParam String conteudo, @RequestParam Long secretariaId) {
        return new ResponseEntity<>(avisoService.criar(titulo, conteudo, secretariaId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Aviso>> listarTodos() {
        return ResponseEntity.ok(avisoService.listarTodosMaisRecentes());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Aviso> atualizar(
            @PathVariable Long id,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String conteudo) {
        return ResponseEntity.ok(avisoService.atualizar(id, titulo, conteudo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        avisoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
