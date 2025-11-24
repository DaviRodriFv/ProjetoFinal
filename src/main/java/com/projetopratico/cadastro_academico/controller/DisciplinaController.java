package com.projetopratico.cadastro_academico.controller;

import com.projetopratico.cadastro_academico.model.Disciplina;
import com.projetopratico.cadastro_academico.model.Professor;
import com.projetopratico.cadastro_academico.service.DisciplinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    // Cria disciplina já associando professor (opcional)
    @PostMapping
    public ResponseEntity<Disciplina> criar(@RequestBody Map<String, Object> body) {
        String nome = (String) body.get("nome");
        Object profIdObj = body.get("professorId");

        Disciplina d = new Disciplina();
        d.setNome(nome);

        if (profIdObj != null) {
            Long professorId = ((Number) profIdObj).longValue();
            Professor p = new Professor();
            p.setId(professorId);
            d.setProfessor(p);
        }

        return ResponseEntity.ok(disciplinaService.criar(d));
    }

    @PostMapping("/sem-professor")
    public ResponseEntity<Disciplina> criarSemProfessor(@RequestBody Disciplina d) {
        return ResponseEntity.ok(disciplinaService.criarSemProfessor(d));
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listar() {
        return ResponseEntity.ok(disciplinaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscar(@PathVariable Long id) {
        return disciplinaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizar(@PathVariable Long id, @RequestBody Disciplina d) {
        return disciplinaService.atualizar(id, d)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/trocar-professor/{professorId}")
    public ResponseEntity<Disciplina> trocarProfessor(@PathVariable Long id, @PathVariable Long professorId) {
        return disciplinaService.trocarProfessor(id, professorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean ok = disciplinaService.deletar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/todos")
    public ResponseEntity<Void> deletarTodos() {
        disciplinaService.deletarTodos();
        return ResponseEntity.noContent().build();
    }
}
