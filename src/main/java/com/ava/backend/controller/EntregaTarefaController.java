package com.ava.backend.controller;

import com.ava.backend.dto.AvaliarRequest;
import com.ava.backend.dto.EntregaRequest;
import com.ava.backend.dto.RendimentoResponse;
import com.ava.backend.model.EntregaTarefa;
import com.ava.backend.model.StatusTarefa;
import com.ava.backend.service.EntregaTarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@CrossOrigin(origins = "*")
public class EntregaTarefaController {

    @Autowired
    private EntregaTarefaService entregaTarefaService;

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<EntregaTarefa>> listarPorAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(entregaTarefaService.listarPorAluno(alunoId));
    }

    @GetMapping("/aluno/{alunoId}/status/{status}")
    public ResponseEntity<List<EntregaTarefa>> listarPorAlunoEStatus(
            @PathVariable Long alunoId, @PathVariable StatusTarefa status) {
        return ResponseEntity.ok(entregaTarefaService.listarPorAlunoEStatus(alunoId, status));
    }

    @GetMapping("/tarefa/{tarefaId}")
    public ResponseEntity<List<EntregaTarefa>> listarPorTarefa(@PathVariable Long tarefaId) {
        return ResponseEntity.ok(entregaTarefaService.listarPorTarefa(tarefaId));
    }

    @PutMapping("/{entregaId}/status")
    public ResponseEntity<EntregaTarefa> atualizarStatus(
            @PathVariable Long entregaId, @RequestParam StatusTarefa status) {
        return ResponseEntity.ok(entregaTarefaService.atualizarStatus(entregaId, status));
    }

    @PutMapping("/{entregaId}/entregar")
    public ResponseEntity<EntregaTarefa> entregarTarefa(
            @PathVariable Long entregaId, @Valid @RequestBody EntregaRequest request) {
        return ResponseEntity.ok(entregaTarefaService.entregarTarefa(entregaId, request.getRespostaText()));
    }

    @PutMapping("/{entregaId}/avaliar")
    public ResponseEntity<EntregaTarefa> avaliarTarefa(
            @PathVariable Long entregaId, @Valid @RequestBody AvaliarRequest request) {
        return ResponseEntity.ok(entregaTarefaService.avaliarTarefa(entregaId, request));
    }

    @GetMapping("/aluno/{alunoId}/disciplina/{disciplinaId}/rendimento")
    public ResponseEntity<RendimentoResponse> obterRendimento(
            @PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        return ResponseEntity.ok(entregaTarefaService.obterRendimento(alunoId, disciplinaId));
    }
}
