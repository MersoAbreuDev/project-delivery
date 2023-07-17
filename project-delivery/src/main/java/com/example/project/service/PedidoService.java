package com.example.project.service;

import com.example.project.entity.Cliente;
import com.example.project.entity.Pedido;
import com.example.project.filters.response.ClienteFilterResponseDTO;
import com.example.project.handle.request.BadRequestException;
import com.example.project.handle.response.ObjectNotFoundException;
import com.example.project.repository.ClienteRepository;
import com.example.project.repository.PedidoRepository;
import com.example.project.requestDTO.PedidoRequestDTO;
import com.example.project.responseDTO.ClientePedidoResponseDTO;
import com.example.project.responseDTO.ClienteResponseDTO;
import com.example.project.responseDTO.PedidoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ModelMapper modelMapper,
                         ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
        this.clienteRepository = clienteRepository;
    }

    public PedidoResponseDTO save(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = modelMapper.map(pedidoRequestDTO, Pedido.class);
        pedido.setCodigoPedido(generateCodigoPedido());
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getIdCliente())
                .orElseThrow(() -> new BadRequestException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        PedidoResponseDTO pedidoResponseDTO = modelMapper.map(pedidoSalvo, PedidoResponseDTO.class);
        pedidoResponseDTO.setCliente(modelMapper.map(cliente, ClientePedidoResponseDTO.class));
        return pedidoResponseDTO;
    }

    private Long generateCodigoPedido() {
        return ThreadLocalRandom.current().nextLong(100000, 999999);
    }

    public List<PedidoResponseDTO> findAllPedidos() {
        List<Pedido> pedidos = this.pedidoRepository.findAll();
        List<PedidoResponseDTO> listaPedidosDTO = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                cliente = clienteRepository.getOne(cliente.getId());
                ClientePedidoResponseDTO clientePedidoResponseDTO = modelMapper.map(cliente, ClientePedidoResponseDTO.class);
                PedidoResponseDTO pedidoResponseDTO = modelMapper.map(pedido, PedidoResponseDTO.class);
                pedidoResponseDTO.setCliente(clientePedidoResponseDTO);
                listaPedidosDTO.add(pedidoResponseDTO);
            }
        }
        return listaPedidosDTO;
    }


    public PedidoResponseDTO findByIdPedido(Long id) {
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado"));
        Cliente cliente = pedido.getCliente();
        ClientePedidoResponseDTO clientePedidoResponseDTO = this.modelMapper.map(cliente, ClientePedidoResponseDTO.class);
        PedidoResponseDTO pedidoResponseDTO = this.modelMapper.map(pedido, PedidoResponseDTO.class);
        pedidoResponseDTO.setCliente(clientePedidoResponseDTO);
        return pedidoResponseDTO;
    }

    public void delete(Long id) {
        this.pedidoRepository.findById(id).ifPresentOrElse(cliente -> {
            try {

                this.pedidoRepository.delete(cliente);
            } catch (BadRequestException e) {
                throw new BadRequestException("Cliente possui pedidos vinculados.");
            }
        }, () -> {
            throw new ObjectNotFoundException("Cliente não encontrado.");
        });
    }

    public PedidoResponseDTO updatePedido(Long id, PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Pedido não encontrado"));

        Cliente cliente = this.clienteRepository.findById(pedidoRequestDTO.getIdCliente()).get();


        cliente.setEmail(cliente.getEmail());
        cliente.setNome(cliente.getNome());
        cliente.setCpf(cliente.getCpf());
        cliente.setTelefone(cliente.getTelefone());

        pedido.setCodigoPedido(pedidoRequestDTO.getCodigoPedido());
        pedido.setStatus(pedidoRequestDTO.getStatus());
        pedido.setDataCriacao(pedidoRequestDTO.getDataCriacao());
        pedido.setCliente(cliente);

        PedidoResponseDTO pedidoResponseDTO = modelMapper.map(pedido, PedidoResponseDTO.class);
        ClientePedidoResponseDTO clientePedidoResponseDTO = modelMapper.map(cliente, ClientePedidoResponseDTO.class);
        pedidoResponseDTO.setCliente(clientePedidoResponseDTO);

        return pedidoResponseDTO;
    }


}
