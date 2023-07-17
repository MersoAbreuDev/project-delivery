package com.example.project.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioPasswordRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email
    @NotNull(message = "{login.not.null}")
    private String login;

    @NotBlank(message = "{password.not.blank}")
    @NotNull(message = "{password.not.null}")
    private String password;

}
