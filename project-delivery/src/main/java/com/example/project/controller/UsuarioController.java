package com.example.project.controller;

import com.example.project.requestDTO.UsuarioPasswordRequestDTO;
import com.example.project.requestDTO.UsuarioRequestDTO;
import com.example.project.responseDTO.UsuarioResponseDTO;
import com.example.project.responseDTO.UsuarioTokenResponseDTO;
import com.example.project.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> save(@RequestBody  @Valid UsuarioRequestDTO usuarioRequestDTO) {
        return ResponseEntity.ok(this.service.save(usuarioRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPeloId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.service.buscarPeloId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(this.service.buscarTodos());
    }


    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenResponseDTO> gerarTokenPeloUsuarioESenha(
            @RequestBody @Valid UsuarioPasswordRequestDTO usuarioPasswordRequestDTO) {
        return ResponseEntity.ok(this.service.gerarTokenPeloUsuarioESenha(usuarioPasswordRequestDTO));
    }

}
