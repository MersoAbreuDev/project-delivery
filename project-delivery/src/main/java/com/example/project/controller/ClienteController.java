package com.example.project.controller;

import com.example.project.filters.response.ClienteFilterResponseDTO;
import com.example.project.requestDTO.ClienteRequestDTO;
import com.example.project.responseDTO.ClienteResponseDTO;
import com.example.project.service.ClienteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/clientes")
@RestController
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody ClienteRequestDTO clienteRequestDTO){
        return ResponseEntity.ok(this.clienteService.save(clienteRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAllClients() {
        return ResponseEntity.ok(this.clienteService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable("id") Long id, @RequestBody ClienteRequestDTO clienteRequestDTO){
        return ResponseEntity.ok(this.clienteService.update(id, clienteRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ClienteFilterResponseDTO>> filterClients(
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam("nome") String nome
    ) {
        // Ajuste as datas para incluir o final do dia
        LocalDate dataFimAjustada = dataFim.plusDays(1);

        List<ClienteFilterResponseDTO> filterClientesDTO = clienteService.filterClient(nome, dataInicio, dataFimAjustada);
        return ResponseEntity.ok(filterClientesDTO);
    }
}
