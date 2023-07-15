package com.example.project.requestDTO;

import com.example.project.entity.Cliente;
import com.example.project.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate dataCriacao;

    private Long codigoPedido;

    private Status status;

    private Long idCliente;
}
