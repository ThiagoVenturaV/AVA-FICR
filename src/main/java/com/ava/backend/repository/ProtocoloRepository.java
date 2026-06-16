package com.ava.backend.repository;

import com.ava.backend.model.Protocolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProtocoloRepository extends JpaRepository<Protocolo, Long> {
    List<Protocolo> findByAlunoId(Long alunoId);
}
