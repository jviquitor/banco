package interfaceUsuario;

import agencia.Agencia;
import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import cliente.ClientePessoa;
import cliente.Endereco;
import conta.Conta;

import java.util.Scanner;

public class MenuUsuario {
    private static final Scanner teclado = new Scanner(System.in);

    public static void iniciar() {
        imprimirBorda("=", 30);
        System.out.println("[0] - Encerrar programa");
        System.out.println("[1] - Acessar conta");
        System.out.println("[2] - Criar conta");
        imprimirBorda("=", 30);
        System.out.print("\n> ");
        try {
            switch (teclado.nextLine()) {
                case "0":
                    break;
                case "1":
                    //Login
                    break;
                case "2":
                    //Criar conta
                    break;
                default:
                    //Opção inválida
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void imprimirBorda(String padrao, int tam) {
        for (int i = 0; i < tam; i++) {
            System.out.print(padrao);
        }
        System.out.println();
    }

    private static Cliente criarCliente() throws InsercaoException {
        imprimirBorda("-", 20);
        System.out.print("""
                Tipo de cliente:
                [0] - Cancelar
                [1] - Pessoa fisica
                [2] - Pessoa juridica
                >\040
                """);
        String tipo = teclado.nextLine();

        String tag = (tipo.equals("1")) ? "CPF" : "CNPJ";

        String[] cabecalhoEndereco = {
                "CEP",
                "Numero da Residencia",
                "Complemento (Opcional)",
        };
        String[] cabecalhoGeral = {
                "Nome completo",
                "Email",
                "Telefone",
                "Idade",
                "Renda",
                tag,
        };

        String[] entradaEndereco = new String[cabecalhoEndereco.length];
        for (int i = 0; i < cabecalhoEndereco.length; i++) {
            System.out.printf("%s:\n> ", cabecalhoEndereco[i]);
            entradaEndereco[i] = teclado.nextLine();
        }
        Endereco endereco = new Endereco(
                Integer.parseInt(entradaEndereco[0]),
                Integer.parseInt(entradaEndereco[1]),
                entradaEndereco[2]
        );

        String[] entradaGeral = new String[cabecalhoGeral.length];
        for (int i = 0; i < cabecalhoGeral.length; i++) {
            System.out.printf("%s:\n> ", cabecalhoGeral[i]);
            entradaGeral[i] = teclado.nextLine();
        }

        Cliente cliente;
        if (tipo.equalsIgnoreCase("1")) {
            cliente = new ClientePessoa(
                    entradaGeral[0],
                    entradaGeral[1],
                    entradaGeral[2],
                    Integer.parseInt(entradaGeral[3]),
                    endereco,
                    Double.parseDouble(entradaGeral[4]),
                    entradaGeral[5]
            );
        } else {
            cliente = new ClienteEmpresa(
                    entradaGeral[0],
                    entradaGeral[1],
                    entradaGeral[2],
                    Integer.parseInt(entradaGeral[3]),
                    endereco,
                    Double.parseDouble(entradaGeral[4]),
                    entradaGeral[5]
            );
        }
        Agencia.getInstance().addCliente(cliente);

        return cliente;
    }

    //mudar o nome para ligacao com o usuario
    private static void menuCriacaoConta() {
        imprimirBorda("-", 20);
        Double renda = inserirRenda();
        String tipo = teclado.nextLine();
    }

    private static Double inserirRenda() throws IllegalArgumentException{
        System.out.println("Por favor, Insira sua Renda");
        Double renda = teclado.nextDouble();


        if (0 < renda) {
            throw new IllegalArgumentException("O valor de renda nao pode ser menos que 0");
        } else if (renda < 200) {
            throw new IllegalArgumentException("As regras da Agencia nao permite essa renda, por favor, consulte nossos termos de uso!");
        }

        return renda;
    }

    private static Double tryInserirRenda() {
        Double renda;
        try {
            renda = inserirRenda();
        } catch (IllegalArgumentException exception) {
            renda = inserirRenda();
        }
        return renda;
    }

}
