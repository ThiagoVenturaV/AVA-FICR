package com.ava.backend.repository;

import com.ava.backend.model.EntregaTarefa;
import com.ava.backend.model.StatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntregaTarefaRepository extends JpaRepository<EntregaTarefa, Long> {
    List<EntregaTarefa> findByAlunoId(Long alunoId);
    List<EntregaTarefa> findByAlunoIdAndStatus(Long alunoId, StatusTarefa status);
    List<EntregaTarefa> findByTarefaId(Long tarefaId);
    
    @Query("SELECT e FROM EntregaTarefa e WHERE e.aluno.id = :alunoId AND e.tarefa.disciplina.id = :disciplinaId")
    List<EntregaTarefa> findByAlunoIdAndDisciplinaId(@Param("alunoId") Long alunoId, @Param("disciplinaId") Long disciplinaId);
}
