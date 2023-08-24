package com.example.model;

public record Aluno(Integer id, String nome, String turma, Integer rm) {

    public Aluno(String nome, String turma, Integer rm){
        this(0, nome, turma, rm);
    }

    @Override
    public String toString() {
        return rm + " - " + nome + " (" + turma + ")";
    }
}
