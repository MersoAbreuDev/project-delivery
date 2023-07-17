package com.example.project.requestDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LoginRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email
    @NotNull(message = "{login.not.null}")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
