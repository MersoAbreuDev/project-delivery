package com.example.project.service;

import com.example.project.entity.Perfil;
import com.example.project.handle.request.BadRequestException;
import com.example.project.handle.response.ObjectNotFoundException;
import com.example.project.repository.PerfilRepository;
import com.example.project.requestDTO.PerfilRequestDTO;
import com.example.project.responseDTO.PerfilResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilService {
    private final PerfilRepository perfilRepository;

    private final ModelMapper modelMapper;

    public PerfilService(PerfilRepository perfilRepository, ModelMapper modelMapper) {
        this.perfilRepository = perfilRepository;
        this.modelMapper = modelMapper;
    }

    public PerfilResponseDTO salvar(PerfilRequestDTO perfilRequestDTO) {
        this.perfilRepository.findByNome(perfilRequestDTO.getNome()).ifPresent(perfil -> {
            throw new BadRequestException("Perfil já cadastrado.");
        });

        Perfil perfil = this.modelMapper.map(perfilRequestDTO, Perfil.class);
        perfil = this.perfilRepository.save(perfil);
        return this.modelMapper.map(perfil, PerfilResponseDTO.class);
    }

    public List<PerfilResponseDTO> buscarTodos() {
        List<PerfilResponseDTO> perfilResponseDTOs = this.perfilRepository.findAll().stream().map(perfil -> {
            return this.modelMapper.map(perfil, PerfilResponseDTO.class);
        }).collect(Collectors.toList());
        return perfilResponseDTOs;
    }

    public void excluirPeloId(Long id) {
        this.perfilRepository.findById(id).ifPresentOrElse(perfil -> {
            try {

                this.perfilRepository.delete(perfil);
            } catch (DataIntegrityViolationException e) {
                throw new BadRequestException("Perfil vinculado ao usuario");
            }
        }, () -> {
            throw new ObjectNotFoundException("Perfil não encontrado.");
        });
    }

    public PerfilResponseDTO buscarPeloId(Long id) {
        return this.perfilRepository.findById(id).map(perfil -> {
            return modelMapper.map(perfil, PerfilResponseDTO.class);
        }).orElseThrow(() -> new ObjectNotFoundException("Perfil não encontrado."));
    }

    public PerfilResponseDTO atualizar(Long id, PerfilRequestDTO perfilRequestDTO) {
        return this.perfilRepository.findById(id).map(perfil -> {
            if (!perfil.getNome().equals(perfilRequestDTO.getNome())) {
                this.perfilRepository.findByNome(perfilRequestDTO.getNome()).ifPresent(p -> {
                    throw new BadRequestException("Perfil já cadastrado.");
                });
                perfilRequestDTO.setId(perfil.getId());
                perfil = this.modelMapper.map(perfilRequestDTO, Perfil.class);
                perfil = this.perfilRepository.save(perfil);
            }
            return this.modelMapper.map(perfil, PerfilResponseDTO.class);
        }).orElseThrow(() -> new ObjectNotFoundException("Perfil não encontrado."));
    }

    public Perfil buscarPerfilPeloId(Long id) {
        return this.perfilRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Perfil não encontrado."));
    }

}
