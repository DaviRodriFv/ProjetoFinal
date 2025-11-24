package com.projetopratico.cadastro_academico.repository;


import com.projetopratico.cadastro_academico.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}

