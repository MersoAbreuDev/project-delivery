package com.example.project.filters.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ClienteFilterResponseDTO {


    private LocalDateTime dataCriacao;

    private String nome;

    private String email;
}
