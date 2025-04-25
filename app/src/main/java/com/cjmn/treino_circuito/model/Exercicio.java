package com.cjmn.treino_circuito.model;

public class Exercicio {
    private Long id;
    private String nome;
    private Integer seconds;

    public Exercicio() {
    }

    public Exercicio(Long id, String nome, Integer seconds) {
        this.id = id;
        this.nome = nome;
        this.seconds = seconds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
}
