package com.example.project.controller;

import com.example.project.requestDTO.PerfilRequestDTO;
import com.example.project.responseDTO.PerfilResponseDTO;
import com.example.project.service.PerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping
    public ResponseEntity<PerfilResponseDTO> salvar(@RequestBody @Valid PerfilRequestDTO perfilRequestDTO) {
        return ResponseEntity.ok(this.perfilService.salvar(perfilRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<PerfilResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(this.perfilService.buscarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPeloId(@PathVariable("id") Long id) {
        this.perfilService.excluirPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> buscarPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.perfilService.buscarPeloId(id));
    }

}
