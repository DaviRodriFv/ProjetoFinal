package com.projetopratico.cadastro_academico.service;

import com.projetopratico.cadastro_academico.model.Professor;
import com.projetopratico.cadastro_academico.repository.ProfessorRepository;
import com.projetopratico.cadastro_academico.dto.ProfessorDTO;
import com.projetopratico.cadastro_academico.dto.DisciplinaDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor criar(Professor professor) {
        return professorRepository.save(professor);
    }

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Optional<Professor> buscarPorId(Long id) {
        return professorRepository.findById(id);
    }

    public Optional<Professor> atualizar(Long id, Professor dados) {
        return professorRepository.findById(id).map(p -> {
            p.setNome(dados.getNome());
            return professorRepository.save(p);
        });
    }

    public boolean deletar(Long id) {
        if (!professorRepository.existsById(id)) return false;
        professorRepository.deleteById(id);
        return true;
    }

    public void deletarTodos() {
        professorRepository.deleteAll();
    }

    public List<ProfessorDTO> listarTodosDTO() {
        return professorRepository.findAll().stream().map(professor -> {
            ProfessorDTO dto = new ProfessorDTO();
            dto.setId(professor.getId());
            dto.setNome(professor.getNome());
            dto.setEmail(professor.getEmail());

            List<DisciplinaDTO> disciplinasDTO = professor.getDisciplinas().stream()
                    .map(d -> new DisciplinaDTO(d.getId(), d.getNome()))
                    .collect(Collectors.toList());

            dto.setDisciplinas(disciplinasDTO);
            return dto;
        }).collect(Collectors.toList());
    }
}
