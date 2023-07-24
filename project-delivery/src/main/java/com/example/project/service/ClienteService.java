package com.example.project.service;
import com.example.project.filters.response.ClienteFilterResponseDTO;
import com.example.project.handle.request.BadRequestException;
import com.example.project.handle.response.ObjectNotFoundException;
import com.example.project.requestDTO.EntregaRequestDTO;
import com.example.project.responseDTO.EntregaResponseDTO;
import org.modelmapper.ModelMapper;
import com.example.project.entity.Cliente;
import com.example.project.repository.ClienteRepository;
import com.example.project.requestDTO.ClienteRequestDTO;
import com.example.project.responseDTO.ClienteResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ClienteService {

    private ModelMapper modelMapper;
    private  ClienteRepository clienteRepository;
    public ClienteService(ModelMapper modelMapper,
                          ClienteRepository clienteRepository) {
        this.modelMapper = modelMapper;
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = this.modelMapper.map(clienteRequestDTO, Cliente.class);
        cliente = this.clienteRepository.save(cliente);
        return this.modelMapper.map(cliente, ClienteResponseDTO.class);

    }

    public List<ClienteResponseDTO> findAll() {
        List<ClienteResponseDTO> listaClientesDTO = this.clienteRepository.findAll().stream().map(clientes -> {
            return this.modelMapper.map(clientes, ClienteResponseDTO.class);
        }).collect(Collectors.toList());
        return listaClientesDTO;
    }

    public ClienteResponseDTO update(Long id, ClienteRequestDTO clienteRequestDTO) {
        return this.clienteRepository.findById(id).map(cliente -> {
            if (!equals(clienteRequestDTO.getId())) {
                this.clienteRepository.findByNome(clienteRequestDTO.getNome()).ifPresent(p -> {
                    throw new BadRequestException("Cliente já cadastrado. ");
                });
            }
            clienteRequestDTO.setId(cliente.getId());
            cliente = this.modelMapper.map(clienteRequestDTO, Cliente.class);
            cliente = this.clienteRepository.save(cliente);
            return this.modelMapper.map(cliente, ClienteResponseDTO.class);
        }).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado."));

    }

    public void delete(Long id) {
        this.clienteRepository.findById(id).ifPresentOrElse(cliente -> {
            try {

                this.clienteRepository.delete(cliente);
            } catch (BadRequestException e) {
                throw new BadRequestException("Cliente possui pedidos vinculados.");
            }
        }, () -> {
            throw new ObjectNotFoundException("Cliente não encontrado.");
        });
    }

    public List<ClienteFilterResponseDTO> filterClient(String nome, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate dataFimAjustada = dataFim.plusDays(1);

        List<Cliente> clientes = this.clienteRepository.findAllByClienteAndDataCriacaoBetween(nome, dataInicio, dataFimAjustada);
        List<ClienteFilterResponseDTO> listaClientesDTO = clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteFilterResponseDTO.class))
                .collect(Collectors.toList());

        return listaClientesDTO;
    }
}
