package com.projetopratico.cadastro_academico.controller;

import com.projetopratico.cadastro_academico.dto.DisciplinaDTO;
import com.projetopratico.cadastro_academico.dto.ProfessorDTO;
import com.projetopratico.cadastro_academico.model.Professor;
import com.projetopratico.cadastro_academico.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<Professor> criar(@RequestBody Professor professor) {
        return ResponseEntity.ok(professorService.criar(professor));
    }

    @GetMapping("/dto")
    public ResponseEntity<List<ProfessorDTO>> listarTodosDTO() {
        List<ProfessorDTO> dtos = professorService.listarTodos().stream().map(professor -> {
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

        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listar() {
        return listarTodosDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> buscarPorId(@PathVariable Long id) {
        return professorService.buscarPorId(id)
                .map(professor -> {
                    ProfessorDTO dto = new ProfessorDTO();
                    dto.setId(professor.getId());
                    dto.setNome(professor.getNome());
                    dto.setEmail(professor.getEmail());

                    List<DisciplinaDTO> disciplinasDTO = professor.getDisciplinas().stream()
                            .map(d -> new DisciplinaDTO(d.getId(), d.getNome()))
                            .collect(Collectors.toList());

                    dto.setDisciplinas(disciplinasDTO);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody Professor professor) {
        return professorService.atualizar(id, professor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean ok = professorService.deletar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/todos")
    public ResponseEntity<Void> deletarTodos() {
        professorService.deletarTodos();
        return ResponseEntity.noContent().build();
    }
}
