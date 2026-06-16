package com.ava.backend.service;

import com.ava.backend.dto.AvaliarRequest;
import com.ava.backend.dto.RendimentoResponse;
import com.ava.backend.exception.BusinessRuleException;
import com.ava.backend.exception.ResourceNotFoundException;
import com.ava.backend.model.*;
import com.ava.backend.repository.DisciplinaRepository;
import com.ava.backend.repository.EntregaTarefaRepository;
import com.ava.backend.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntregaTarefaService {

    @Autowired
    private EntregaTarefaRepository entregaTarefaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<EntregaTarefa> listarPorAluno(Long alunoId) {
        return entregaTarefaRepository.findByAlunoId(alunoId);
    }

    public List<EntregaTarefa> listarPorAlunoEStatus(Long alunoId, StatusTarefa status) {
        return entregaTarefaRepository.findByAlunoIdAndStatus(alunoId, status);
    }

    public List<EntregaTarefa> listarPorTarefa(Long tarefaId) {
        return entregaTarefaRepository.findByTarefaId(tarefaId);
    }

    public EntregaTarefa atualizarStatus(Long entregaId, StatusTarefa novoStatus) {
        EntregaTarefa entrega = entregaTarefaRepository.findById(entregaId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de entrega não encontrado"));

        if (entrega.getStatus() == StatusTarefa.ENTREGUE && novoStatus != StatusTarefa.ENTREGUE) {
            throw new BusinessRuleException("Uma tarefa já entregue não pode voltar para pendente ou em andamento");
        }

        entrega.setStatus(novoStatus);
        return entregaTarefaRepository.save(entrega);
    }

    public EntregaTarefa entregarTarefa(Long entregaId, String respostaText) {
        EntregaTarefa entrega = entregaTarefaRepository.findById(entregaId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de entrega não encontrado"));

        if (entrega.getStatus() == StatusTarefa.ENTREGUE) {
            throw new BusinessRuleException("Tarefa já foi entregue anteriormente");
        }

        entrega.setRespostaText(respostaText);
        entrega.setStatus(StatusTarefa.ENTREGUE);
        entrega.setDataEntrega(LocalDateTime.now());

        return entregaTarefaRepository.save(entrega);
    }

    public EntregaTarefa avaliarTarefa(Long entregaId, AvaliarRequest request) {
        EntregaTarefa entrega = entregaTarefaRepository.findById(entregaId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de entrega não encontrado"));

        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado"));

        // Regra de negócio: Apenas o professor responsável pela disciplina pode avaliar
        Disciplina disciplina = entrega.getTarefa().getDisciplina();
        if (!disciplina.getProfessor().getId().equals(professor.getId())) {
            throw new BusinessRuleException("Apenas o professor responsável pela disciplina '" 
                    + disciplina.getNome() + "' pode avaliar esta entrega");
        }

        // Regra de negócio: A tarefa precisa estar no status ENTREGUE para ser avaliada
        if (entrega.getStatus() != StatusTarefa.ENTREGUE) {
            throw new BusinessRuleException("Apenas tarefas com status ENTREGUE podem ser avaliadas");
        }

        // Regra de negócio: A nota não pode exceder os pontos máximos da tarefa
        double pontosMaximos = entrega.getTarefa().getPontosMaximos();
        if (request.getNota() < 0 || request.getNota() > pontosMaximos) {
            throw new BusinessRuleException("A nota de avaliação deve estar entre 0.0 e a pontuação máxima de " + pontosMaximos);
        }

        entrega.setNota(request.getNota());
        entrega.setFeedback(request.getFeedback());

        return entregaTarefaRepository.save(entrega);
    }

    public RendimentoResponse obterRendimento(Long alunoId, Long disciplinaId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada"));

        List<EntregaTarefa> entregas = entregaTarefaRepository.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId);

        if (entregas.isEmpty()) {
            return new RendimentoResponse(disciplina.getNome(), 0.0, "PENDENTE", new ArrayList<>());
        }

        double sumNotas = 0.0;
        int gradedCount = 0;
        boolean temUngraded = false;

        List<RendimentoResponse.TarefaNotaDto> tarefasDto = new ArrayList<>();
        for (EntregaTarefa e : entregas) {
            Double nota = e.getNota();
            if (nota != null) {
                sumNotas += nota;
                gradedCount++;
            } else {
                temUngraded = true;
            }
            tarefasDto.add(new RendimentoResponse.TarefaNotaDto(
                    e.getTarefa().getTitulo(),
                    e.getTarefa().getPontosMaximos(),
                    nota,
                    e.getStatus().name()
            ));
        }

        Double media = 0.0;
        String status = "PENDENTE";
        if (gradedCount > 0) {
            media = sumNotas / gradedCount;
        }
        if (!temUngraded && gradedCount == entregas.size()) {
            status = media >= 7.0 ? "Aprovado" : "Reprovado";
        }

        return new RendimentoResponse(disciplina.getNome(), media, status, tarefasDto);
    }
}
