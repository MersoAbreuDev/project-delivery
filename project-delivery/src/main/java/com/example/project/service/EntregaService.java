package com.example.project.service;

import com.example.project.entity.Entrega;
import com.example.project.entity.Pedido;
import com.example.project.enums.Status;
import com.example.project.handle.request.BadRequestException;
import com.example.project.repository.EntregaRepository;
import com.example.project.repository.PedidoRepository;
import com.example.project.requestDTO.EntregaRequestDTO;
import com.example.project.responseDTO.ClienteResponseDTO;
import com.example.project.responseDTO.EntregaResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EntregaService {

    private final ModelMapper modelMapper;

    private final EntregaRepository entregaRepository;

    private final PedidoRepository pedidoRepository;

    public EntregaService(ModelMapper modelMapper,
                          EntregaRepository entregaRepository,
                          PedidoRepository pedidoRepository) {
        this.modelMapper = modelMapper;
        this.entregaRepository = entregaRepository;
        this.pedidoRepository = pedidoRepository;
    }



    public EntregaResponseDTO salvarEntrega(EntregaRequestDTO entregaRequestDTO) {
        if(Objects.isNull(entregaRequestDTO.getIdPedido())){
            new BadRequestException("Pedido não encontrado");
        }
        Entrega entrega = this.modelMapper.map(entregaRequestDTO, Entrega.class);
        entrega =entregaRepository.save(entrega);
        Pedido pedido = this.pedidoRepository.findById(entregaRequestDTO.getIdPedido()).get();
        pedido.setEntrega(entrega);
        pedido.setStatus(Status.ENTREGUE);
        pedidoRepository.saveAndFlush(pedido);
        EntregaResponseDTO entregaResponseDTO = new EntregaResponseDTO();
        return this.modelMapper.map(entrega, EntregaResponseDTO.class);
    }

    public List<EntregaResponseDTO> listAllEntregas() {
        List<EntregaResponseDTO> listaEntregas = this.entregaRepository.findAll().stream().map(entregas -> {
            return this.modelMapper.map(entregas, EntregaResponseDTO.class);
        }).collect(Collectors.toList());
        return listaEntregas;
    }
}
