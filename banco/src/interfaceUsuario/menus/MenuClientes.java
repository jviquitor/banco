package interfaceUsuario.menus;

import agencia.Agencia;
import agencia.exceptions.InsercaoException;
import cartao.Fatura;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import cliente.ClientePessoa;
import cliente.Endereco;
import cliente.exceptions.GerenteJaExistenteException;
import cliente.exceptions.GerenteNaoEncontradoException;
import cliente.exceptions.TiposClientes;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.exceptions.ValorInvalido;
import interfaceUsuario.verificadores.dados.VerificadorClientes;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;
import transacao.Boleto;
import transacao.Transacao;
import utilsBank.GerenciadorBanco;
import utilsBank.arquivo.exception.EscritaArquivoException;

import java.util.ArrayList;

import static interfaceUsuario.menus.MenuUsuario.*;
import static transacao.Boleto.criarBoleto;

public class MenuClientes {
    protected static void menuCliente() {
        boolean loop = true;
        Cliente cliente = InterfaceUsuario.getClienteAtual();
        String tipoConta = GerenciadorBanco.getTipoConta(cliente.getConta());
        boolean isClientePessoa = true;
        imprimirBorda("=");
        System.out.println("Bem vindo " + cliente.getNome());

        while (loop) {
            int quantidadeNotificacoes = cliente.getConta().getNotificacoes().size();
            if (cliente instanceof ClientePessoa) {
                isClientePessoa = true;
                imprimirMenu(true, quantidadeNotificacoes);
            } else if (cliente instanceof ClienteEmpresa) {
                isClientePessoa = false;
                imprimirMenu(false, quantidadeNotificacoes);
            }

            try {
                String value = TECLADO.nextLine();
                Transacao t;
                TiposClientes tiposClientes;
                if (isClientePessoa) {
                    tiposClientes = TiposClientes.CLIENTE_PESSOA;
                } else {
                    tiposClientes = TiposClientes.CLIENTE_EMPRESA;
                }
                switch (value) {
                    case "0":
                        loop = false;
                        break;
                    case "1":
                        System.out.println("SALDO >> " + cliente.getConta().getSaldo());
                        break;
                    case "2":
                        MenuDinheiroGuardado.gerenciarDinheiroGuardado(cliente);
                        break;
                    case "3":
                        MenuTransacoes.receberDadosTransacoes(TRANSFERENCIA, tipoConta);
                        t = cliente.getConta().transferir();
                        t.gerarComprovante();
                        GerenciadorBanco.imprimirDireitos();
                        break;
                    case "4":
                        MenuBoletos.pagarBoleto();
                        System.out.println("Boleto pago!");
                        break;
                    case "5":
                        MenuTransacoes.receberDadosTransacoes(DEPOSITO, tipoConta);
                        t = cliente.getConta().depositar();
                        t.gerarComprovante();
                        GerenciadorBanco.imprimirDireitos();
                        break;
                    case "6":
                        MenuEmprestimo.menuEmprestimo();
                        break;
                    case "7":
                        MenuTransacoes.agendamentoTransferencia(tipoConta);
                        t = cliente.getConta().agendarTransacao();
                        t.gerarComprovante();
                        GerenciadorBanco.imprimirMensagemTransferenciaAgendada();
                        GerenciadorBanco.imprimirDireitos();
                        break;
                    case "8":
                        MenuCartoes.gerenciarCarteira(cliente);
                        break;
                    case "9":
                        System.out.println(cliente.getConta().getChavesPix().toString());
                        break;
                    case "10":
                        MenuTransacoes.adicionarChavePix(tiposClientes);
                        if (cliente.getConta().modificarChavePix()) {
                            System.out.println("Chave Pix modificada com sucesso");
                            System.out.println(cliente.getConta().getChavesPix());
                        }
                        break;
                    case "11":
                        MenuBoletos.gerarBoleto();
                        Boleto boleto = criarBoleto();
                        System.out.println("BOLETO GERADO");
                        System.out.println(boleto);
                        break;
                    case "12":
                        boolean menuLoopHistorico = true;
                        while (menuLoopHistorico) {

                            System.out.println("[0]: Cancelar");
                            System.out.println("[1]: Mostrar Transacoes");
                            System.out.println("[2]: Mostrar Faturas");

                            switch (TECLADO.nextLine()) {
                                case "0":
                                    menuLoopHistorico = false;
                                    break;
                                case "1":
                                    ArrayList<Transacao> transacoesCliente = cliente.getConta().getHistorico().getTransacoes();
                                    if (transacoesCliente.isEmpty()) {
                                        System.out.println("Nenhuma transacao ocorrida.");
                                    } else {
                                        for (Transacao transacao : transacoesCliente) {
                                            imprimirBorda("=");
                                            System.out.println(transacao);
                                        }
                                    }

                                    menuLoopHistorico = false;
                                    break;
                                case "2":
                                    ArrayList<Fatura> faturaCliente = cliente.getConta().getHistorico().getFaturas();
                                    if (faturaCliente.isEmpty()) {
                                        System.out.println("Nenhuma fatura paga");
                                    } else {
                                        for (Fatura fatura : cliente.getConta().getHistorico().getFaturas()) {
                                            imprimirBorda("=");
                                            System.out.println(fatura);
                                        }
                                    }

                                    menuLoopHistorico = false;
                                    break;
                            }
                        }
                        break;
                    case "13":
                        MenuBoletos.mostrarBoletos();
                        break;
                    case "14":
                        mostrarNotificacoes();
                        cliente.getConta().resetarNotificacoes();
                        break;
                    case "15":
                        if (!isClientePessoa) {
                            String identificacaoNovoGerente = identificarNovoGerente();
                            try {
                                assert cliente instanceof ClienteEmpresa;
                                if (((ClienteEmpresa) cliente).addGerentes(identificacaoNovoGerente)) {
                                    System.out.println("Novo gerente adicionado com sucesso!");
                                }
                            } catch (GerenteJaExistenteException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                        break;
                    case "16":
                        if (!isClientePessoa) {
                            String identificacaoNovoGerente = identificarNovoGerente();
                            assert cliente instanceof ClienteEmpresa;
                            try {
                                if (((ClienteEmpresa) cliente).removerGerentes(identificacaoNovoGerente)) {
                                    System.out.println("Gerente removido com sucesso!");
                                }
                            } catch (GerenteNaoEncontradoException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                        break;
                    default:
                        GerenciadorBanco.imprimirErroOpcao();
                        break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                    Agencia.getInstance().atualizarArquivos();
                } catch (EscritaArquivoException ex) {
                    System.out.println("Ocorreu um erro ao atualizar os nosso banco de dados. Verifique sua conexao e tente novamente.");
                }
            }
        }
    }

    protected static Cliente criarCliente() throws InsercaoException, EscritaArquivoException, RuntimeException, ValorInvalido {
        imprimirBorda("=");
        String entrada;
        do {
            System.out.print("Tipo de cliente:\n" +
                    "[0] - Cancelar\n" +
                    "[1] - Pessoa fisica\n" +
                    "[2] - Pessoa juridica\n" +
                    "> \n");
            entrada = TECLADO.nextLine();
        } while (!VerificadorEntrada.verificarEntradaTipo(entrada));

        TiposClientes tipo;
        switch (entrada) {
            case "0":
                throw new RuntimeException("Criacao de cliente cancelada.");
            case "1":
                tipo = TiposClientes.CLIENTE_PESSOA;
                break;
            case "2":
                tipo = TiposClientes.CLIENTE_EMPRESA;
                break;
            default:
                throw new RuntimeException("Ocorreu um erro");
        }

        String tag = (tipo == TiposClientes.CLIENTE_PESSOA) ? "CPF" : "CNPJ";
        System.out.println("[DIGITE CORRETAMENTE AS INFORMACOES A SEGUIR, NUMERO MAX DE CARACTERES ACEITO >> " + VerificadorEntrada.MAX_CARACTERES_ENTRADA);
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
                tag,
                "Senha",
        };
        String[] entradaEndereco;
        String[] entradaGeral;
        int contador = 0;
        do {
            entradaEndereco = usuarioEntradas(cabecalhoEndereco);
            if (contador > 0) {
                System.out.println("Insira o endereco corretamente");
            }
            contador++;
        } while (!VerificadorClientes.verificarEndereco(entradaEndereco));

        contador = 0;
        do {
            entradaGeral = usuarioEntradas(cabecalhoGeral);
            if (contador > 0) {
                System.out.println("Insira o as informacoes corretamente");
            }
            contador++;
        } while (!VerificadorClientes.informacoesClientes(entradaGeral, tipo));

        Endereco endereco = new Endereco(
                entradaEndereco[0],
                Integer.parseInt(entradaEndereco[1]),
                entradaEndereco[2]
        );

        Cliente cliente;
        int idade = Integer.parseInt(entradaGeral[3]);
        if (tipo == TiposClientes.CLIENTE_PESSOA) {
            cliente = new ClientePessoa(
                    entradaGeral[0],
                    entradaGeral[1],
                    entradaGeral[2],
                    idade,
                    endereco,
                    entradaGeral[4],
                    entradaGeral[5]
            );
        } else {
            cliente = new ClienteEmpresa(
                    entradaGeral[0],
                    entradaGeral[1],
                    entradaGeral[2],
                    idade,
                    endereco,
                    entradaGeral[4],
                    entradaGeral[5]
            );
        }

        Agencia.getInstance().addCliente(cliente);
        return cliente;
    }

    private static String identificarNovoGerente() {
        imprimirBorda("=");
        String[] cabecalhoNovoGerente = {
                "Digite a identificacao do novo gerente",
        };
        String[] entrada = usuarioEntradas(cabecalhoNovoGerente);

        while (!VerificadorClientes.verificarIdentidadeGerente(entrada[0])) {
            entrada = usuarioEntradas(cabecalhoNovoGerente);
        }
        return entrada[0];
    }
}
