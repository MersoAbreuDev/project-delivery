package com.example.project.responseDTO;

import com.example.project.entity.Cliente;
import com.example.project.enums.Status;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate dataCriacao;

    private Long codigoPedido;

    private Status status;

    private ClientePedidoResponseDTO cliente;
}
