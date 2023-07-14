package com.example.project.repository;

import com.example.project.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Optional<Object> findByNome(String nome);

    Optional<Object> findByCpf(String cpf);

    @Query(value = "SELECT cli FROM Cliente cli WHERE cli.nome = :nome AND DATE(cli.dataCriacao) BETWEEN :dataInicio AND :dataFim")
    List<Cliente> findAllByClienteAndDataCriacaoBetween(@Param("nome") String nome,
                                                        @Param("dataInicio") LocalDate dataInicio,
                                                        @Param("dataFim") LocalDate dataFim);



}