package interfaceUsuario.menus;

import cliente.Cliente;
import conta.GerenciamentoCartao;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.MenuUsuarioConstantes;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.exceptions.ValorInvalido;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;
import interfaceUsuario.verificadores.dados.VerificadorTransacao;
import utilsBank.GerenciadorBanco;

import static interfaceUsuario.menus.MenuUsuario.*;

public class MenuCartoes {
    protected static void gerenciadorCartoes(Cliente cliente) {
        imprimirBorda("=");
        String entrada;
        boolean loop = true;
        while (loop) {
            try {
                System.out.println("[GERENCIAMENTO CARTOES]");
                System.out.println("[0] - Cancelar");
                System.out.println("[1] - Mostrar Cartoes");
                System.out.println("[2] - Adicionar Cartao");
                entrada = TECLADO.nextLine();
                switch (entrada) {
                    case "0":
                        loop = false;
                        break;
                    case "1":
                        cliente.getConta().mostrarCartoes();
                        break;
                    case "2":
                        criacaoCartao();
                        cliente.getConta().criarCartao(cliente.getNome(), InterfaceUsuario.getDadosCartao());
                        break;
                }
            } catch (Exception ignore) {
            }
        }
    }

    protected static void criacaoCartao() throws ValorInvalido {
        String[] cabecalhoCartoesGeral = {
                "ESCOLHA O APELIDO DO SEU NOVO CARTAO DE CREDITO", //entrada[0]
        };

        String[] entradas = usuarioEntradas(cabecalhoCartoesGeral);

        while (!VerificadorEntrada.verificarApelidoCartao(entradas)) {
            entradas = usuarioEntradas(cabecalhoCartoesGeral);
        }

        InterfaceUsuario.setDadosCartao(new DadosCartao(
                entradas[0] //Apelido do cartao
        ));
    }

    protected static void gerenciarCarteira(Cliente cliente) {
        boolean loop = true;
        while (loop) {
            imprimirBorda("=");
            System.out.println("[0] - Cancelar");
            System.out.println("[1] - Mostrar Fatura");
            System.out.println("[2] - Mostrar Limite");
            System.out.println("[3] - Pagar Fatura");
            System.out.println("[4] - Criar/Mostrar Cartoes [REQUER SENHA]");
            System.out.println("[5] - Modificar Debito automatico ");
            System.out.println("[6] - [FERRAMENTA ADMINISTRADOR] Aumentar Fatura");
            imprimirBorda("=");
            System.out.print("\n> ");
            GerenciamentoCartao carteiraCli = cliente.getConta().getCARTEIRA();
            try {
                String op = TECLADO.nextLine();
                switch (op) {
                    case "0":
                        loop = false;
                        break;
                    case "1":
                        System.out.println("SUA FATURA >>> " + carteiraCli.getFatura());
                        break;
                    case "2":
                        System.out.println("SEU LIMITE MAXIMO >>> " + carteiraCli.getLimiteMaximo());
                        System.out.println("SEU LIMITE GASTO >>> " + carteiraCli.getFatura());
                        System.out.println("SEU LIMITE RESTANTE >>> " + carteiraCli.getLimiteRestante());
                        break;
                    case "3":
                        String entrada;
                        if (carteiraCli.getFatura() > 0.0) {
                            do {
                                System.out.println("Deseja pagar o valor total da fatura? [1] SIM [0] NAO");
                                entrada = TECLADO.nextLine();
                            } while (VerificadorEntrada.verificarEntradasZeroUm(entrada));

                            if (GerenciadorBanco.intToBoolean(Integer.parseInt(entrada))) {
                                if (VerificadorEntrada.verificarEntradaValorParaFatura(String.valueOf(carteiraCli.getFatura()), MenuUsuarioConstantes.VERIFICAR_VALOR_SALDO)) {
                                    cliente.pagarFatura(carteiraCli.getFatura());
                                    System.out.println("FATURA PAGA COM SUCESSO");
                                    System.out.println("SUA FATURA >>> " + carteiraCli.getFatura());
                                }
                            } else {
                                do {
                                    System.out.println("Digite o valor para pagar a fatura");
                                    entrada = TECLADO.nextLine();
                                } while (VerificadorTransacao.valorFatura(String.valueOf(entrada), MenuUsuarioConstantes.PAGAR_FATURA, carteiraCli));

                                cliente.pagarFatura(Double.parseDouble(entrada));
                                System.out.println("FATURA PAGA COM SUCESSO");
                                System.out.println("SUA FATURA >>> " + carteiraCli.getFatura());
                            }
                        } else {
                            System.out.println("SUA FATURA ESTA ZERADA! :D");
                        }
                        break;
                    case "4":
                        System.out.println("[INSIRA SUA SENHA]");
                        entrada = TECLADO.nextLine();

                        if (cliente.verificarSenha(entrada)) {
                            MenuCartoes.gerenciadorCartoes(cliente);
                        }
                        break;
                    case "5":
                        MenuDebitoAutomatico.debitoAutomatico(carteiraCli);
                        break;
                    case "6":
                        do {
                            System.out.println("[LEMBRANDO: APENAS UMA FERRAMENTA ADMINISTRATIVA PARA GERENCIAR O BANCO]]");
                            System.out.println("DIGITE O VALOR QUE FOI GASTO NO CARTAO");
                            entrada = TECLADO.nextLine();
                        } while (VerificadorTransacao.valorFatura(String.valueOf(entrada), MenuUsuarioConstantes.AUMENTAR_FATURA, carteiraCli));

                        if (cliente.getConta().aumentarFatura(Double.parseDouble(entrada))) {
                            System.out.println("FATURA ATUALIZADA COM SUCESSO");
                            System.out.println("SUA FATURA >>> " + carteiraCli.getFatura());
                        }
                        break;
                    default:
                        GerenciadorBanco.imprimirErroOpcao();
                        break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
