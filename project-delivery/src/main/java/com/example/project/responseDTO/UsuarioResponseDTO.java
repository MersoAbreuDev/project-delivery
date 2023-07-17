package com.example.project.responseDTO;

import com.example.project.enums.Status;
import com.example.project.enums.StatusUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private StatusUsuario statusUsuario;

    private PerfilResponseDTO perfil;

}
