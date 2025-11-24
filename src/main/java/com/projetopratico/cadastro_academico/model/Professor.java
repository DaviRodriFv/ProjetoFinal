package com.projetopratico.cadastro_academico.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email; // ✅ adicione este campo

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("professor")
    private List<Disciplina> disciplinas = new ArrayList<>();

    public Professor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; } // ✅ getter
    public void setEmail(String email) { this.email = email; } // ✅ setter

    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}

