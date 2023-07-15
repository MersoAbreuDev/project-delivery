package com.example.project.controller;


import com.example.project.filters.response.ClienteFilterResponseDTO;
import com.example.project.requestDTO.PedidoRequestDTO;
import com.example.project.responseDTO.PedidoResponseDTO;
import com.example.project.service.PedidoService;
import io.swagger.models.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("pedidos")
@RestController
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@RequestBody PedidoRequestDTO pedidoRequestDTO){
       return ResponseEntity.ok(this.pedidoService.save(pedidoRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll(){
        List<PedidoResponseDTO> listaPedidos = this.pedidoService.findAllPedidos();
        return ResponseEntity.ok(listaPedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.pedidoService.findByIdPedido(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> update(@PathVariable("id") Long id,@RequestBody PedidoRequestDTO pedidoRequestDTO){
        return ResponseEntity.ok(this.pedidoService.updatePedido(id, pedidoRequestDTO));
    }


}
