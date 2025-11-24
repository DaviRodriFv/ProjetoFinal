package com.projetopratico.cadastro_academico.service;

import com.projetopratico.cadastro_academico.model.Disciplina;
import com.projetopratico.cadastro_academico.model.Professor;
import com.projetopratico.cadastro_academico.repository.DisciplinaRepository;
import com.projetopratico.cadastro_academico.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository,
                             ProfessorRepository professorRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    public Disciplina criar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina criarSemProfessor(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listarTodos() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Optional<Disciplina> atualizar(Long id, Disciplina dados) {
        return disciplinaRepository.findById(id).map(d -> {
            d.setNome(dados.getNome());
            if (dados.getProfessor() != null && dados.getProfessor().getId() != null) {
                professorRepository.findById(dados.getProfessor().getId())
                        .ifPresent(d::setProfessor);
            }
            return disciplinaRepository.save(d);
        });
    }

    public Optional<Disciplina> trocarProfessor(Long disciplinaId, Long professorId) {
        Optional<Disciplina> optDisc = disciplinaRepository.findById(disciplinaId);
        Optional<Professor> optProf = professorRepository.findById(professorId);

        if (optDisc.isPresent() && optProf.isPresent()) {
            Disciplina disciplina = optDisc.get();
            disciplina.setProfessor(optProf.get());
            return Optional.of(disciplinaRepository.save(disciplina));
        }
        return Optional.empty();
    }

    public boolean deletar(Long id) {
        if (!disciplinaRepository.existsById(id)) return false;
        disciplinaRepository.deleteById(id);
        return true;
    }

    public void deletarTodos() {
        disciplinaRepository.deleteAll();
    }
}
