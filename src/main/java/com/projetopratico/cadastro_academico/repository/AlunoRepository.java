package com.projetopratico.cadastro_academico.repository;


import com.projetopratico.cadastro_academico.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
