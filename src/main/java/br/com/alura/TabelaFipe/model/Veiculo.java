package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(
        @JsonAlias("Valor") String valor,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") Integer ano,
        @JsonAlias("Combustivel") String tipoCombusitvel
        ) {

        @Override
        public String toString() {
                return  "Modelo: " + modelo + "\n" +
                        "Valor:" + valor + "\n" +
                        "Marca: " + marca + "\n" +
                        "Ano: " + ano + "\n" +
                        "Tipo de combusitvel: " + tipoCombusitvel + "\n";
        }
}
