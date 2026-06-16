package com.ava.backend.service;

import com.ava.backend.dto.ProtocoloRequest;
import com.ava.backend.exception.BusinessRuleException;
import com.ava.backend.exception.ResourceNotFoundException;
import com.ava.backend.model.*;
import com.ava.backend.repository.AlunoRepository;
import com.ava.backend.repository.PessoaRepository;
import com.ava.backend.repository.ProtocoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProtocoloService {

    @Autowired
    private ProtocoloRepository protocoloRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Protocolo criar(ProtocoloRequest request) {
        Aluno aluno = alunoRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        Protocolo protocolo = new Protocolo();
        protocolo.setTipo(request.getTipo());
        protocolo.setDescricao(request.getDescricao());
        protocolo.setAluno(aluno);
        protocolo.setStatus(StatusProtocolo.EM_ANALISE);

        return protocoloRepository.save(protocolo);
    }

    public Protocolo analisar(Long protocoloId, String statusString, Long secretariaId) {
        Protocolo protocolo = protocoloRepository.findById(protocoloId)
                .orElseThrow(() -> new ResourceNotFoundException("Protocolo não encontrado"));

        Pessoa secretaria = pessoaRepository.findById(secretariaId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário da secretaria não encontrado"));

        // Regra de negócio: Apenas usuários do tipo Secretaria podem analisar protocolos
        if (!(secretaria instanceof Secretaria)) {
            throw new BusinessRuleException("Apenas membros da secretaria podem analisar e decidir sobre protocolos");
        }

        StatusProtocolo novoStatus;
        try {
            novoStatus = StatusProtocolo.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("Status inválido. Deve ser DEFERIDO ou INDEFERIDO");
        }

        if (novoStatus == StatusProtocolo.EM_ANALISE) {
            throw new BusinessRuleException("Não é possível reverter o status de um protocolo para 'Em Análise'");
        }

        protocolo.setStatus(novoStatus);
        protocolo.setAnalisadoPor((Secretaria) secretaria);

        return protocoloRepository.save(protocolo);
    }

    public List<Protocolo> listarPorAluno(Long alunoId) {
        return protocoloRepository.findByAlunoId(alunoId);
    }

    public List<Protocolo> listarTodos() {
        return protocoloRepository.findAll();
    }
}
