package com.ava.backend.service;

import com.ava.backend.dto.TarefaRequest;
import com.ava.backend.exception.BusinessRuleException;
import com.ava.backend.exception.ResourceNotFoundException;
import com.ava.backend.model.*;
import com.ava.backend.repository.DisciplinaRepository;
import com.ava.backend.repository.EntregaTarefaRepository;
import com.ava.backend.repository.PessoaRepository;
import com.ava.backend.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EntregaTarefaRepository entregaTarefaRepository;

    public Tarefa criar(TarefaRequest request) {
        Disciplina disciplina = disciplinaRepository.findById(request.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada"));

        Pessoa criador = pessoaRepository.findById(request.getCriadoPorId())
                .orElseThrow(() -> new ResourceNotFoundException("Criador não encontrado"));

        // Regra de negócio: Apenas Professores ou Secretaria podem criar tarefas
        if (!(criador instanceof Professor) && !(criador instanceof Secretaria)) {
            throw new BusinessRuleException("Apenas professores ou secretários podem criar tarefas");
        }

        // Regra de negócio: Se for professor, deve ser o professor da disciplina
        if (criador instanceof Professor && !disciplina.getProfessor().getId().equals(criador.getId())) {
            throw new BusinessRuleException("O professor criador da tarefa deve ser o responsável por esta disciplina");
        }

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());
        tarefa.setDataEntrega(request.getDataEntrega());
        tarefa.setPontosMaximos(request.getPontosMaximos());
        tarefa.setDisciplina(disciplina);
        tarefa.setCriadoPor(criador);

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        // Regra de negócio: Gerar automaticamente o quadro de tarefas para todos os alunos matriculados
        List<Aluno> alunos = disciplina.getAlunos();
        for (Aluno aluno : alunos) {
            EntregaTarefa entrega = new EntregaTarefa();
            entrega.setTarefa(tarefaSalva);
            entrega.setAluno(aluno);
            entrega.setStatus(StatusTarefa.PENDENTE);
            entregaTarefaRepository.save(entrega);
        }

        return tarefaSalva;
    }

    public List<Tarefa> listarPorDisciplina(Long disciplinaId) {
        return tarefaRepository.findByDisciplinaId(disciplinaId);
    }
}
