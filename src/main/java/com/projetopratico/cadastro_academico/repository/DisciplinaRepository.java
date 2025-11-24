package com.projetopratico.cadastro_academico.repository;


import com.projetopratico.cadastro_academico.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}
