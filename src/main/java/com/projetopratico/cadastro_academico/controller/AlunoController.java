package com.projetopratico.cadastro_academico.controller;


import com.projetopratico.cadastro_academico.model.Aluno;
import com.projetopratico.cadastro_academico.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
        return ResponseEntity.ok(alunoService.criar(aluno));
    }

    @PostMapping("/com-disciplinas")
    public ResponseEntity<Aluno> criarComDisciplinas(@RequestBody Map<String, Object> body) {
        String nome = (String) body.get("nome");
        @SuppressWarnings("unchecked")
        List<Integer> disciplinaIds = (List<Integer>) body.get("disciplinasIds");

        Aluno aluno = new Aluno();
        aluno.setNome(nome);

        List<Long> ids = disciplinaIds.stream()
                .map(Integer::longValue)
                .toList();

        Aluno criado = alunoService.criarComDisciplinas(aluno, ids);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listar() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        return alunoService.atualizar(id, aluno)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/adicionar-disciplina/{disciplinaId}")
    public ResponseEntity<Aluno> adicionarDisciplina(@PathVariable Long id, @PathVariable Long disciplinaId) {
        return alunoService.adicionarDisciplina(id, disciplinaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean ok = alunoService.deletar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/todos")
    public ResponseEntity<Void> deletarTodos() {
        alunoService.deletarTodos();
        return ResponseEntity.noContent().build();
    }
}
