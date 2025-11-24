package com.projetopratico.cadastro_academico.service;


import com.projetopratico.cadastro_academico.model.Aluno;
import com.projetopratico.cadastro_academico.model.Disciplina;
import com.projetopratico.cadastro_academico.repository.AlunoRepository;
import com.projetopratico.cadastro_academico.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public AlunoService(AlunoRepository alunoRepository, DisciplinaRepository disciplinaRepository) {
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public Aluno criar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno criarComDisciplinas(Aluno aluno, List<Long> disciplinaIds) {
        for (Long id : disciplinaIds) {
            disciplinaRepository.findById(id).ifPresent(aluno.getDisciplinas()::add);
        }
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<Aluno> atualizar(Long id, Aluno dados) {
        return alunoRepository.findById(id).map(a -> {
            a.setNome(dados.getNome());
            return alunoRepository.save(a);
        });
    }

    public Optional<Aluno> adicionarDisciplina(Long alunoId, Long disciplinaId) {
        Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
        Optional<Disciplina> optDisciplina = disciplinaRepository.findById(disciplinaId);

        if (optAluno.isPresent() && optDisciplina.isPresent()) {
            Aluno aluno = optAluno.get();
            aluno.getDisciplinas().add(optDisciplina.get());
            return Optional.of(alunoRepository.save(aluno));
        }
        return Optional.empty();
    }

    public boolean deletar(Long id) {
        if (!alunoRepository.existsById(id)) return false;
        alunoRepository.deleteById(id);
        return true;
    }

    public void deletarTodos() {
        alunoRepository.deleteAll();
    }
}
