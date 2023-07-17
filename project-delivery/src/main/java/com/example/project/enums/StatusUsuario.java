package com.example.project.enums;

public enum StatusUsuario {
    ATIVO("ATIVO"), INATIVO("INATIVO");

    private String descricao;

    private StatusUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
