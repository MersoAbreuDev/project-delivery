package com.example.project.enums;

public enum Status {

    ABERTO ("Aberto"),
    RECEBIDO ("Recebido"),
    PREPARO ("Preparo"),
    TRAJETO("A caminho"),
    ENTREGUE ("Entregue");

    Status(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

}
