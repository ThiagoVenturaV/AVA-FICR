package com.ava.backend.service;

import com.ava.backend.exception.BusinessRuleException;
import com.ava.backend.exception.ResourceNotFoundException;
import com.ava.backend.model.*;
import com.ava.backend.repository.AlunoRepository;
import com.ava.backend.repository.DisciplinaRepository;
import com.ava.backend.repository.EntregaTarefaRepository;
import com.ava.backend.repository.ProfessorRepository;
import com.ava.backend.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private EntregaTarefaRepository entregaTarefaRepository;

    public Disciplina criar(String nome, Long professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado"));

        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nome);
        disciplina.setProfessor(professor);

        return disciplinaRepository.save(disciplina);
    }

    public Disciplina matricularAluno(Long disciplinaId, Long alunoId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada"));

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        if (disciplina.getAlunos().contains(aluno)) {
            throw new BusinessRuleException("Aluno já matriculado nesta disciplina");
        }

        disciplina.getAlunos().add(aluno);
        Disciplina disciplinaSalva = disciplinaRepository.save(disciplina);

        // Ao matricular o aluno, gerar as entregas para todas as tarefas já existentes na disciplina
        List<Tarefa> tarefasExistentes = tarefaRepository.findByDisciplinaId(disciplinaId);
        for (Tarefa tarefa : tarefasExistentes) {
            EntregaTarefa entrega = new EntregaTarefa();
            entrega.setTarefa(tarefa);
            entrega.setAluno(aluno);
            entrega.setStatus(StatusTarefa.PENDENTE);
            entregaTarefaRepository.save(entrega);
        }

        return disciplinaSalva;
    }

    public List<Disciplina> listarTodos() {
        return disciplinaRepository.findAll();
    }
}
