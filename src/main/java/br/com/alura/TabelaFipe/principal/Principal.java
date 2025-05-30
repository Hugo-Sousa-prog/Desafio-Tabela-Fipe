package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumo = new ConsumoApi();
    private Scanner scan = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        var menu = """
                Digite o que deseja buscar:
                
                Carros
                
                Motos
                
                Caminhões
                """;

        System.out.println(menu);
        var opcao = scan.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        }
            else if (opcao.toLowerCase().contains("mot")){
                endereco = URL_BASE + "motos/marcas";
        } else {
                endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca para consulta:");
        var codigoMarca = scan.nextLine();

        endereco += "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do veiculo para buscar: ");
        var nomeVeiculo = scan.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo à ser consultado: ");
        var codigoModelo = scan.nextLine();

        endereco += "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);

        String enderecoBase = endereco;
        List<Veiculo> veiculos = anos.stream()
                .map(a -> {
                    var enderecoAnos = enderecoBase + "/" + a.codigo();
                    var jsonAnos = consumo.obterDados(enderecoAnos);
                    return conversor.obterDados(jsonAnos, Veiculo.class);
                }).collect(Collectors.toList());

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }
}
