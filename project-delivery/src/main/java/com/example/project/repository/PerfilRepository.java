package com.example.project.repository;

import com.example.project.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {


    Optional<Perfil> findByNome(String nome);
}
