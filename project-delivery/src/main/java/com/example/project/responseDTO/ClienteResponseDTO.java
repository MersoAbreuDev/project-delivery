package com.example.project.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

    private Long id;

    private LocalDateTime dataCriacao;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;
}
