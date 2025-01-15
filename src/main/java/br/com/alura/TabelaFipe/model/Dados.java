package br.com.alura.TabelaFipe.model;

public record Dados(String codigo, String nome) {
    @Override
    public String toString() {
        return  "Codigo: " + codigo +
                ", Nome: " + nome
                ;
    }
}
