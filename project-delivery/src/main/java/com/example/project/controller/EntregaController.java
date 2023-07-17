package com.example.project.controller;

import com.example.project.repository.EntregaRepository;
import com.example.project.requestDTO.EntregaRequestDTO;
import com.example.project.responseDTO.EntregaResponseDTO;
import com.example.project.service.EntregaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("entregas")
@RestController
public class EntregaController {

    private final EntregaService entregaService;

    private final EntregaRepository entregaRepository;

    public EntregaController(EntregaService entregaService, EntregaRepository entregaRepository) {
        this.entregaService = entregaService;
        this.entregaRepository = entregaRepository;
    }

    @PostMapping
    public ResponseEntity<EntregaResponseDTO> salvar(@RequestBody EntregaRequestDTO entregaRequestDTO) {
        return ResponseEntity.ok(entregaService.salvarEntrega(entregaRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<EntregaResponseDTO>> listAll(){
        return ResponseEntity.ok(entregaService.listAllEntregas());
    }
}
