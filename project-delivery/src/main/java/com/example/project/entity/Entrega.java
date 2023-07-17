package com.example.project.entity;

import com.example.project.enums.Status;
import com.example.project.enums.StatusEntrega;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_entrega")
public class Entrega implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Status statusEntrega;

    private String observacao;


    @OneToMany(mappedBy = "entrega")
    private List<Pedido> pedidos;

    @CreationTimestamp
    private LocalDate dataEntrega;


}
