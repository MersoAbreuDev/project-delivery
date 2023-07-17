package com.example.project.requestDTO;

import com.example.project.entity.Pedido;
import com.example.project.enums.Status;
import com.example.project.enums.StatusEntrega;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Status statusEntrega;

    private Long idPedido;

    private String observacao;

    private LocalDate dataEntrega;
}
