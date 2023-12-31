package com.example.project.responseDTO;

import com.example.project.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime dataCriacao;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private List<Pedido> pedidos;
}
