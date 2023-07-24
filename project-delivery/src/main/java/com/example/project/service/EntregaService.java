package com.example.project.service;

import com.example.project.entity.Cliente;
import com.example.project.entity.Entrega;
import com.example.project.entity.Pedido;
import com.example.project.enums.Status;
import com.example.project.handle.request.BadRequestException;
import com.example.project.handle.response.ObjectNotFoundException;
import com.example.project.repository.EntregaRepository;
import com.example.project.repository.PedidoRepository;
import com.example.project.requestDTO.EntregaRequestDTO;
import com.example.project.responseDTO.ClientePedidoResponseDTO;
import com.example.project.responseDTO.ClienteResponseDTO;
import com.example.project.responseDTO.EntregaResponseDTO;
import com.example.project.responseDTO.PedidoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void delete(Long id) {
        this.entregaRepository.findById(id).ifPresentOrElse(entrega -> {
            try {
                this.entregaRepository.delete(entrega);
            } catch (BadRequestException e) {
                throw new BadRequestException("Entrega possui pedidos vinculados.");
            }}, () -> {
            throw new ObjectNotFoundException("Entrega não encontrado.");
        });

    }

    public List<EntregaResponseDTO> findAll() {
        List<EntregaResponseDTO> listaEntregaDTO = this.entregaRepository.findAll().stream().map(entrega -> {
            PedidoResponseDTO pedidoDTO = new PedidoResponseDTO();
            for(Pedido pedido : entrega.getPedidos()) {
                ClientePedidoResponseDTO clienteDTO = new ClientePedidoResponseDTO();
                clienteDTO.setId(pedido.getCliente().getId());
                clienteDTO.setNome(pedido.getCliente().getNome());
                clienteDTO.setCpf(pedido.getCliente().getCpf());
                clienteDTO.setTelefone(pedido.getCliente().getTelefone());
                clienteDTO.setNome(pedido.getCliente().getEmail());

                pedidoDTO.setId(pedido.getId());
                pedidoDTO.setCodigoPedido(pedido.getCodigoPedido());
                pedidoDTO.setStatus(pedido.getStatus());
                pedidoDTO.setCliente(clienteDTO);
            }

            EntregaResponseDTO entregaResponseDTO = this.modelMapper.map(entrega, EntregaResponseDTO.class);
            entregaResponseDTO.setPedidoResponseDTO(pedidoDTO);
            return entregaResponseDTO;
        }).collect(Collectors.toList());
        return listaEntregaDTO;
    }






}
