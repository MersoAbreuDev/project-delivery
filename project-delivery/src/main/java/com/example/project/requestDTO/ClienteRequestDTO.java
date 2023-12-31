package com.example.project.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate dataCriacao;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;


}
