package com.projetopratico.cadastro_academico.dto;
import com.projetopratico.cadastro_academico.model.Disciplina;
import java.util.List;


import java.util.List;

public class ProfessorDTO {
    private Long id;
    private String nome;
    private String email;
    private List<DisciplinaDTO> disciplinas;

    public ProfessorDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<DisciplinaDTO> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<DisciplinaDTO> disciplinas) { this.disciplinas = disciplinas; }
}
