package com.example.project.responseDTO;

import com.example.project.entity.Pedido;
import com.example.project.enums.Status;
import com.example.project.enums.StatusEntrega;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Status statusEntrega;

    private String observacao;

    private Pedido pedido;

    private LocalDate dataEntrega;
}
