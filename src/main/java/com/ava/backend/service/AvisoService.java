package com.ava.backend.service;

import com.ava.backend.exception.BusinessRuleException;
import com.ava.backend.exception.ResourceNotFoundException;
import com.ava.backend.model.Aviso;
import com.ava.backend.model.Pessoa;
import com.ava.backend.model.Secretaria;
import com.ava.backend.repository.AvisoRepository;
import com.ava.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvisoService {

    @Autowired
    private AvisoRepository avisoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Aviso criar(String titulo, String conteudo, Long secretariaId) {
        Pessoa criador = pessoaRepository.findById(secretariaId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Regra de negócio: Apenas Secretaria pode publicar avisos institucionais
        if (!(criador instanceof Secretaria)) {
            throw new BusinessRuleException("Apenas membros da secretaria podem criar avisos institucionais");
        }

        Aviso aviso = new Aviso();
        aviso.setTitulo(titulo);
        aviso.setConteudo(conteudo);
        aviso.setDataPublicacao(LocalDateTime.now());
        aviso.setCriadoPor((Secretaria) criador);

        return avisoRepository.save(aviso);
    }

    public List<Aviso> listarTodosMaisRecentes() {
        return avisoRepository.findAllByOrderByDataPublicacaoDesc();
    }

    public Aviso atualizar(Long id, String titulo, String conteudo) {
        Aviso aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviso não encontrado"));

        if (titulo != null) {
            aviso.setTitulo(titulo);
        }

        if (conteudo != null) {
            aviso.setConteudo(conteudo);
        }

        return avisoRepository.save(aviso);
    }

    public void excluir(Long id) {
        Aviso aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviso não encontrado"));
        avisoRepository.delete(aviso);
    }
}
