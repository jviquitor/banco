package interfaceUsuario;

import agencia.Agencia;
import agencia.exceptions.BuscaException;
import agencia.exceptions.InsercaoException;
import cartao.Fatura;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import cliente.ClientePessoa;
import cliente.Endereco;
import cliente.exceptions.GerenteJaExistenteException;
import cliente.exceptions.GerenteNaoEncontradoException;
import cliente.exceptions.LoginException;
import cliente.exceptions.TiposClientes;
import conta.Conta;
import conta.GerenciamentoCartao;
import funcionalidades.exceptions.EmprestimoException;
import interfaceUsuario.dados.*;
import interfaceUsuario.exceptions.ValorInvalido;
import transacao.Boleto;
import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.GerenciadorBanco;
import utilsBank.arquivo.exception.EscritaArquivoException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class MenuUsuario {
    public static final String DEPOSITO = "do seu Deposito";
    public static final String TRANSFERENCIA = "da sua transferencia";
    public static final String GUARDAR = "guardado";
    public static final String RESGATAR = "resgatado";
    public static final String FORMATO_DATAS = "31/12/2022";
    public static final int TAM_BORDA = 50;
    public static final String CHAVES_DISPONIVEIS = "[ESCREVA O TIPO DA CHAVE EXATAMENTE COMO ALGUMAS DESSES TIPOS, IDENTIFICACAO [CPF OU CNPJ]\n" +
            " chave_aleatoria | telefone | email | identificacao";
    public static final String CHAVES_DISPONIVEIS_ALTERACAO = "chave_aleatoria | telefone | email";
    private static final Scanner TECLADO = new Scanner(System.in);

    public static void iniciar() {
        boolean loop = true;
        while (loop) {
            imprimirBorda("=");
            System.out.println("[0] - Encerrar programa");
            System.out.println("[1] - Acessar conta");
            System.out.println("[2] - Criar conta");
            imprimirBorda("=");
            System.out.print("\n> ");
            try {
                switch (TECLADO.nextLine()) {
                    case "0":
                        loop = false;
                        break;
                    case "1":
                        logar();
                        menuCliente();
                        InterfaceUsuario.setClienteAtual(null);
                        break;
                    case "2":
                        InterfaceUsuario.setClienteAtual(criarCliente());
                        if (InterfaceUsuario.getDadosConta().isDebitoAutomatico()) {
                            MenuSetDebitoAutomatico(InterfaceUsuario.getClienteAtual().getConta().getCARTEIRA());
                        }
                        InterfaceUsuario.getClienteAtual().setChavesPix();
                        menuCliente();
                        InterfaceUsuario.setClienteAtual(null);
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

    private static void imprimirMenu(boolean isClientePessoa, int quantidadeNotificacoes) {
        imprimirBorda("=");
        System.out.println("[0] - Sair");
        System.out.println("[1] - Verificar Saldo");
        System.out.println("[2] - Gerenciar dinheiro guardado");
        System.out.println("[3] - Transferir");
        System.out.println("[4] - Pagar");
        System.out.println("[5] - Depositar");
        System.out.println("[6] - Emprestimo");
        System.out.println("[7] - Agendar transferencia");
        System.out.println("[8] - Gerenciar Cartoes");
        System.out.println("[9] - Mostrar chaves Pix");
        System.out.println("[10] - Modificar chave Pix");
        System.out.println("[11] - Gerar um boleto");
        System.out.println("[12] - Ver historico");
        System.out.println("[13] - Ver boletos gerados");
        System.out.println("[14] - Ver notificacoes [" + quantidadeNotificacoes + "]");

        if (!isClientePessoa) {
            System.out.println("[15] - Adicionar Gerentes");
            System.out.println("[16] - Remover Gerentes");

        }
        imprimirBorda("=");
        System.out.print("\n> ");

    }

    private static void menuCliente() {
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
                        menuGerenciarDinheiroGuardado(cliente);
                        break;
                    case "3":
                        MenuDadosTransacao(TRANSFERENCIA, tipoConta, tiposClientes);
                        t = cliente.getConta().transferir();
                        t.gerarComprovante();
                        GerenciadorBanco.imprimirDireitos();
                        break;
                    case "4":
                        MenuPagarBoleto();
                        System.out.println("Boleto pago!");
                        break;
                    case "5":
                        MenuDadosTransacao(DEPOSITO, tipoConta, tiposClientes);
                        t = cliente.getConta().depositar();
                        t.gerarComprovante();
                        GerenciadorBanco.imprimirDireitos();
                        break;
                    case "6":
                        menuEmprestimo();
                        break;
                    case "7":
                        menuAgendarTransferencia(tipoConta);
                        t = cliente.getConta().agendarTransacao();
                        t.gerarComprovante();
                        GerenciadorBanco.imprimirMensagemTransferenciaAgendada();
                        GerenciadorBanco.imprimirDireitos();
                        break;
                    case "8":
                        menuGerenciarCarteira(cliente);
                        break;
                    case "9":
                        System.out.println(cliente.getConta().getChavesPix().toString());
                        break;
                    case "10":
                        menuAdicionarChavePix(tiposClientes);
                        if (cliente.getConta().modificarChavePix()) {
                            System.out.println("Chave Pix modificada com sucesso");
                            System.out.println(cliente.getConta().getChavesPix());
                        }
                        break;
                    case "11":
                        MenuDadosGerarBoleto();
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
                        mostrarBoletos();
                        break;
                    case "14":
                        mostrarNotificacoes();
                        cliente.getConta().resetarNotificacoes();
                        break;
                    case "15":
                        if (!isClientePessoa) {
                            String identificacaoNovoGerente = MenuIdentificarNovoGerente();
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
                            String identificacaoNovoGerente = MenuIdentificarNovoGerente();
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

    private static Double MenuUsuarioGuardarDinheiro(String tipoOperacao) throws ValorInvalido {
        imprimirBorda("=");
        String[] cabecalhoDinheiroGuardado = {
                "Digite o valor para ser " + tipoOperacao + "!",
        };
        String[] entrada = UsuarioEntradas(cabecalhoDinheiroGuardado);

        while (!VerificadorEntrada.verificarEntradaValor(entrada[0], tipoOperacao)) {
            entrada = UsuarioEntradas(cabecalhoDinheiroGuardado);
        }
        return Double.parseDouble(entrada[0]);
    }

    private static String MenuIdentificarNovoGerente() {
        imprimirBorda("=");
        String[] cabecalhoNovoGerente = {
                "Digite a identificacao do novo gerente",
        };
        String[] entrada = UsuarioEntradas(cabecalhoNovoGerente);

        while (!VerificadorEntrada.verificarIdentidadeGerente(entrada[0])) {
            entrada = UsuarioEntradas(cabecalhoNovoGerente);
        }
        return entrada[0];
    }

    private static String[] UsuarioEntradas(String[] cabecalhoUsuario) {
        String[] entradas = new String[cabecalhoUsuario.length];

        for (int i = 0; i < cabecalhoUsuario.length; i++) {
            System.out.printf("%s:\n> ", cabecalhoUsuario[i]);
            entradas[i] = TECLADO.nextLine();
        }
        imprimirBorda("=");
        return entradas;
    }

    private static void MenuDadosTransacao(String tipoOperacao, String tipoConta, TiposClientes tiposClientes) throws BuscaException, ValorInvalido {
        imprimirBorda("=");

        String[] cabecalhoDadosTransacao = {
                "Digite o valor ",
        };
        String[] entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);

        while (!VerificadorEntrada.verificarDadosTransacao(entradaDadosTransacao[0], tipoOperacao, tipoConta)) {
            System.out.println("DIGITE OS DADOS CORRETAMENTE POR FAVOR");
            entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);
        }

        if (tipoOperacao.equals(TRANSFERENCIA)) {
            PrintUtils.avisoPix();
            String[] cabecalhoDados = {
                    "Digite o tipo da chave inserida [FORMATOS DISPONIVEIS]: " + CHAVES_DISPONIVEIS,
                    "Digite a chave",
            };
            String[] entradaDados = UsuarioEntradas(cabecalhoDados);

            while (!VerificadorEntrada.verificarChavePix(entradaDados[1], entradaDados[0], tiposClientes)) {
                System.out.println("DIGITE OS DADOS CORRETAMENTE POR FAVOR");
                entradaDados = UsuarioEntradas(cabecalhoDados);
            }
            InterfaceUsuario.setDadosTransacao(new DadosTransacao(
                            Double.parseDouble(entradaDadosTransacao[0]),
                            entradaDados[1],
                            InterfaceUsuario.getClienteAtual().getIdentificacao(),
                            entradaDados[0],
                            VerificadorEntrada.IDENTIFICACAO
                    )
            );
        } else if (tipoOperacao.equals(DEPOSITO)) {

            Cliente c = InterfaceUsuario.getClienteAtual();
            InterfaceUsuario.setDadosTransacao(new DadosTransacao(
                    Double.parseDouble(entradaDadosTransacao[0]),
                    c,
                    c)
            );
        }
    }

    private static void menuAgendarTransferencia(String tipoConta) throws BuscaException, ValorInvalido {
        imprimirBorda("=");
        String[] cabecalhoDadosTransacao = {
                "Digite o valor ",
                "Digite a data para realizar a transferencia [FORMATO CORRETO] " + FORMATO_DATAS,
        };

        String[] entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);

        while (!VerificadorEntrada.verificarDadosAgendamentoTransacao(entradaDadosTransacao, tipoConta)) {
            entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);
        }

        PrintUtils.avisoPix();
        String[] cabecalhoDados = {
                "Digite o tipo da chave inserida [FORMATOS DISPONIVEIS]: " + CHAVES_DISPONIVEIS,
                "Digite a chave",
        };
        String[] entradaDados = UsuarioEntradas(cabecalhoDados);
        InterfaceUsuario.setDadosTransacao(new DadosTransacao(
                        Double.parseDouble(entradaDadosTransacao[0]),
                        entradaDados[1],
                        InterfaceUsuario.getClienteAtual().getIdentificacao(),
                        entradaDados[0],
                        VerificadorEntrada.IDENTIFICACAO,
                        entradaDadosTransacao[1]
                )
        );
    }

    private static void MenuPagarBoleto() throws BuscaException, TransacaoException {
        System.out.print("Numero do boleto: \n> ");
        String numBoleto = TECLADO.nextLine();
        Boleto boleto = Agencia.getInstance().buscarBoleto(numBoleto);
        Conta origem = InterfaceUsuario.usuarioAtualConta();
        origem.pagarBoleto(boleto, InterfaceUsuario.getClienteAtual());
        Agencia.getInstance().apagarBoleto(boleto);
    }

    private static Boleto criarBoleto() {
        DadosBoleto dadosBoleto = InterfaceUsuario.getDadosBoleto();
        DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();

        Boleto boleto = new Boleto(dadosTransacao, dadosBoleto);
        Agencia.getInstance().addBoleto(boleto);

        return boleto;
    }

    private static void menuGerenciarDinheiroGuardado(Cliente cliente) {
        boolean loop = true;
        while (loop) {
            imprimirBorda("=");
            System.out.println("[0] - Cancelar");
            System.out.println("[1] - Guardar dinheiro");
            System.out.println("[2] - Resgatar dinheiro guardado");
            System.out.println("[3] - Mostrar dinheiro guardado");
            imprimirBorda("=");
            System.out.print("\n> ");
            try {
                String op = TECLADO.nextLine();
                double valor;
                switch (op) {
                    case "0":
                        loop = false;
                        break;
                    case "1":
                        valor = MenuUsuarioGuardarDinheiro(GUARDAR);
                        cliente.getConta().setDinheiroGuardado(valor, GUARDAR);
                        System.out.println("NOVO SALDO >> " + cliente.getConta().getSaldo());
                        System.out.println("VALOR GUARDADO >> " + cliente.getConta().getDinheiroGuardado());
                        break;
                    case "2":
                        valor = MenuUsuarioGuardarDinheiro(RESGATAR);
                        cliente.getConta().setDinheiroGuardado(valor, RESGATAR);
                        System.out.println("NOVO SALDO >> " + cliente.getConta().getSaldo());
                        System.out.println("VALOR GUARDADO >> " + cliente.getConta().getDinheiroGuardado());
                        break;
                    case "3":
                        System.out.println("VALOR GUARDADO >> " + cliente.getConta().getDinheiroGuardado());
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

    private static void menuGerenciarCarteira(Cliente cliente) {
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
                                } while (VerificadorEntrada.verificarEntradaValorFatura(String.valueOf(entrada), MenuUsuarioConstantes.PAGAR_FATURA, carteiraCli));

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
                            menuCriarMostrarCartoes(cliente);
                        }
                        break;
                    case "5":
                        menuDebitoAutomatico(carteiraCli);
                        break;
                    case "6":
                        do {
                            System.out.println("[LEMBRANDO: APENAS UMA FERRAMENTA ADMINISTRATIVA PARA GERENCIAR O BANCO]]");
                            System.out.println("DIGITE O VALOR QUE FOI GASTO NO CARTAO");
                            entrada = TECLADO.nextLine();
                        } while (VerificadorEntrada.verificarEntradaValorFatura(String.valueOf(entrada), MenuUsuarioConstantes.AUMENTAR_FATURA, carteiraCli));

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

    private static void menuCriarMostrarCartoes(Cliente cliente) {
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
                        MenuCriacaoCartao();
                        cliente.getConta().criarCartao(cliente.getNome(), InterfaceUsuario.getDadosCartao());
                        break;
                }
            } catch (Exception ignore) {
            }
        }
    }

    private static void MenuCriacaoCartao() throws ValorInvalido {
        String[] cabecalhoCartoesGeral = {
                "ESCOLHA O APELIDO DO SEU NOVO CARTAO DE CREDITO", //entrada[0]
        };

        String[] entradas = UsuarioEntradas(cabecalhoCartoesGeral);

        while (!VerificadorEntrada.verificarApelido(entradas)) {
            entradas = UsuarioEntradas(cabecalhoCartoesGeral);
        }

        InterfaceUsuario.setDadosCartao(new DadosCartao(
                entradas[0] //Apelido do cartao
        ));
    }

    private static void MenuSetDebitoAutomatico(GerenciamentoCartao carteira) {
        System.out.println("DIGITE O DIA PARA DEBITAR AUTOMATICAMENTE ENTRE [" + VerificadorEntrada.DIA_MINIMO_DEB_AUTO + " - " + VerificadorEntrada.DIA_MAX_DEB_AUTO + "]");
        String entrada = TECLADO.nextLine();

        while (!VerificadorEntrada.verificarDataDebitoAuto(entrada)) {
            System.out.println("DIA INVALIDO, POR FAVOR, INSIRA CORRETAMENTE UM DIA ENTRE [" + VerificadorEntrada.DIA_MINIMO_DEB_AUTO + " - " + VerificadorEntrada.DIA_MAX_DEB_AUTO + "]");
            entrada = TECLADO.nextLine();
        }
        carteira.setDebitoAutomatico(true, Integer.parseInt(entrada));
    }

    private static void menuDebitoAutomatico(GerenciamentoCartao carteiraCliente) {
        if (carteiraCliente.isDebitoAutomatico()) {
            System.out.printf("DIA DEBITO AUTOMATICO: %d\n", carteiraCliente.getDataDebitoAutomatico());
        }

        String DEBITO_ATIVADO = "DEBITO AUTOMATICO >> ATIVADO <<\nDESEJA DESATIVAR? [1] SIM [0] NAO";
        String DEBITO_ATIVADO_MODIFICAR = "DEBITO AUTOMATICO >> ATIVADO <<\nDESEJA MODIFICAR A DATA? [1] SIM [0] NAO";
        String DEBITO_DESATIVADO = "DEBITO AUTOMATICO >> DESATIVADO <<\nDESEJA ATIVAR? [1] SIM [0] NAO";
        String entrada;

        if (carteiraCliente.isDebitoAutomatico()) {
            System.out.println(DEBITO_ATIVADO);
            entrada = TECLADO.nextLine();
            while (VerificadorEntrada.verificarEntradasZeroUm(entrada)) {
                entrada = TECLADO.nextLine();
            }

            if (GerenciadorBanco.intToBoolean(Integer.parseInt(entrada))) {
                carteiraCliente.setDebitoAutomatico(false, -1);
            } else {
                gerenciarAtivarDesativarDebito(carteiraCliente, DEBITO_ATIVADO_MODIFICAR);
            }
        } else {
            gerenciarAtivarDesativarDebito(carteiraCliente, DEBITO_DESATIVADO);
        }
    }

    private static void gerenciarAtivarDesativarDebito(GerenciamentoCartao carteiraCliente, String ESTADO_MENSAGEM) {
        String entrada;
        System.out.println(ESTADO_MENSAGEM);
        entrada = TECLADO.nextLine();
        while (VerificadorEntrada.verificarEntradasZeroUm(entrada)) {
            entrada = TECLADO.nextLine();
        }

        if (GerenciadorBanco.intToBoolean(Integer.parseInt(entrada))) {
            MenuSetDebitoAutomatico(carteiraCliente);
        }
    }

    private static void MenuDadosGerarBoleto() throws ValorInvalido {
        String[] cabecalho = {
                "Valor",
                "Data de vencimento [EXEMPLO DE FORMATO CORRETO: " + FORMATO_DATAS + "]",
                "Multa por dias",
        };
        String[] entrada;
        do {
            entrada = UsuarioEntradas(cabecalho);
        } while (!VerificadorEntrada.verificarBoleto(entrada));

        DadosTransacao dadosTransacao = new DadosTransacao(Double.parseDouble(entrada[0]), InterfaceUsuario.getClienteAtual());
        DadosBoleto dadosBoleto = new DadosBoleto(entrada[1], Integer.parseInt(entrada[2]), false);

        InterfaceUsuario.setDadosBoleto(dadosBoleto);
        InterfaceUsuario.setDadosTransacao(dadosTransacao);
    }

    private static void menuAdicionarChavePix(TiposClientes tiposClientes) {
        String[] cabecalhoTipoPix = {
                "[ESCREVA] Escolha o tipo de chave que deseja adicionar ou modificar! [FORMATOS ACEITOS PARA TEXTO]\n  " + CHAVES_DISPONIVEIS_ALTERACAO,
        };
        String[] entradas = UsuarioEntradas(cabecalhoTipoPix);

        while (!VerificadorEntrada.verificarEntradaTipoChavePix(entradas[0])) {
            entradas = UsuarioEntradas(cabecalhoTipoPix);
        }
        if (entradas[0].equals(DadosChavesPix.CHAVE_ALEATORIA)) {
            InterfaceUsuario.setDadosChavePix(new DadosChavesPix(null, null, DadosChavesPix.CHAVE_ALEATORIA));
        } else {
            String[] cabecalhoChave = {
                    "POR FAVOR, DIGITE A CHAVE CORRETAMENTE\n",
            };
            String[] entradaChave = UsuarioEntradas(cabecalhoChave);

            while (!VerificadorEntrada.verificarChavePix(entradaChave[0], entradas[0], tiposClientes)) {
                entradas = UsuarioEntradas(cabecalhoTipoPix);
            }

            switch (entradas[0]) {
                case DadosChavesPix.TELEFONE:
                    InterfaceUsuario.setDadosChavePix(new DadosChavesPix(entradaChave[0], null, DadosChavesPix.TELEFONE));
                    break;
                case DadosChavesPix.EMAIL:
                    InterfaceUsuario.setDadosChavePix(new DadosChavesPix(null, entradaChave[0], DadosChavesPix.EMAIL));
                    break;
            }
        }
    }

    private static Cliente criarCliente() throws InsercaoException, EscritaArquivoException, RuntimeException, ValorInvalido {
        imprimirBorda("=");
        String entrada;
        do {
            System.out.print("Tipo de cliente:\n" +
                    "[0] - Cancelar\n" +
                    "[1] - Pessoa fisica\n" +
                    "[2] - Pessoa juridica\n" +
                    "> \n");
            entrada = TECLADO.nextLine();
        } while (!VerificadorEntrada.verificarTipo(entrada));

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
            entradaEndereco = UsuarioEntradas(cabecalhoEndereco);
            if (contador > 0) {
                System.out.println("Insira o endereco corretamente");
            }
            contador++;
        } while (!VerificadorEntrada.verificarEndereco(entradaEndereco));

        contador = 0;
        do {
            entradaGeral = UsuarioEntradas(cabecalhoGeral);
            if (contador > 0) {
                System.out.println("Insira o as informacoes corretamente");
            }
            contador++;
        } while (!VerificadorEntrada.verificarInformacoesCliente(entradaGeral, tipo));

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

    public static Double menuCriacaoConta() throws ValorInvalido {
        Double renda = inserirRenda();

        String entrada;
        System.out.println("Deseja debito automatico? [1] SIM [0] NAO");
        entrada = TECLADO.nextLine();
        while (VerificadorEntrada.verificarEntradasZeroUm(entrada)) {
            System.out.println("Por favor, insira corretamente a opcao!");
            entrada = TECLADO.nextLine();
        }
        boolean debitoAutomatico = GerenciadorBanco.intToBoolean(Integer.parseInt(entrada));
        MenuCriacaoCartao();

        InterfaceUsuario.setDadosConta(new DadosConta(
                VerificadorEntrada.tipoDeContaPelaRenda(renda),
                debitoAutomatico)
        );

        return renda;
    }

    private static Double inserirRenda() {
        double renda = 0.0;
        while (renda < VerificadorEntrada.RENDA_MINIMA) {
            try {
                System.out.println("Por favor, Insira sua Renda");
                renda = Double.parseDouble(TECLADO.nextLine());
                VerificadorEntrada.verificarRenda(renda);
            } catch (ValorInvalido ex) {
                System.out.println(ex.getMessage());
            }
        }
        return renda;
    }

    private static void menuEmprestimo() {
        imprimirBorda("=");
        System.out.println("[0] - Cancelar");
        System.out.println("[1] - Pedir emprestimo");
        System.out.println("[2] - Pagar emprestimo");
        imprimirBorda("=");
        System.out.print("\n> ");
        try {
            String op = TECLADO.nextLine();
            switch (op) {
                case "0":
                    break;
                case "1":
                    gerarEmprestimo();
                    break;
                case "2":
                    pagarEmprestimo();
                    break;
                default:
                    GerenciadorBanco.imprimirErroOpcao();
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void gerarEmprestimo() throws EmprestimoException, ValorInvalido {
        Conta contaAtual = InterfaceUsuario.usuarioAtualConta();
        if (contaAtual.hasEmprestimo()) {
            throw new EmprestimoException("Voce ja possui um emprestimo");
        }
        String[] cabecalho = {
                "Valor do emprestimo: ",
                "Quantidade de parcelas (ate 12x): ",
        };
        String[] entrada = UsuarioEntradas(cabecalho);

        int parcelas = Integer.parseInt(entrada[1]);
        double valor = Double.parseDouble(entrada[0]);

        if (contaAtual.getCARTEIRA().getLimiteMaximo() * 4 < valor) {
            throw new EmprestimoException("Seu limite e insuficiente para esse emprestimo");
        }

        if (!(0 < parcelas && parcelas <= 12)) {
            throw new EmprestimoException();
        }

        Agencia.getInstance().pegarEmprestimo(valor);
        contaAtual.criarEmprestimo(valor, parcelas);
        System.out.printf("EMPRESTIMO REALIZADO! (Novo saldo: %.2f)\n", contaAtual.getSaldo());
    }

    private static void pagarEmprestimo() throws EmprestimoException {
        Conta contaAtual = InterfaceUsuario.usuarioAtualConta();
        if (contaAtual.hasEmprestimo()) {
            imprimirBorda("=");
            System.out.println("[0] - Cancelar");
            System.out.printf("[1] - Pagar parcela (%.2f)\n", contaAtual.getParcelaEmprestimo());
            System.out.printf("[2] - Pagar total (%.2f)\n", contaAtual.getEmprestimo());
            imprimirBorda("=");
            System.out.print("\n> ");
            try {
                String op = TECLADO.nextLine();
                switch (op) {
                    case "0":
                        break;
                    case "1":
                        contaAtual.pagarParcelaEmprestimo();
                        System.out.printf("PARCELA PAGA! (Novo total: %.2f)\n", contaAtual.getEmprestimo());
                        break;
                    case "2":
                        contaAtual.pagarEmprestimo();
                        System.out.printf("EMPRESTIMO PAGO! (Novo saldo: %.2f)\n", contaAtual.getSaldo());
                        break;
                    default:
                        GerenciadorBanco.imprimirErroOpcao();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            throw new EmprestimoException("Voce nao possui emprestimo");
        }
    }

    private static void logar() throws LoginException, BuscaException {
        String[] cabecalho = {
                "CPF/CNPJ",
                "Senha",
        };

        String[] entrada = UsuarioEntradas(cabecalho);

        ClienteEmpresa clienteEmpresa = Agencia.getInstance().buscarEmpresa(entrada[0]);
        Cliente cliente = null;
        try {
            cliente = Agencia.getInstance().buscarCliente(entrada[0]);
        } catch (BuscaException ex) {
            if (clienteEmpresa == null) {
                throw ex;
            }
        }
        if (clienteEmpresa != null && cliente != null) {
            imprimirBorda("=");
            System.out.println("Entrar como:");
            System.out.println("[0] - Cancelar");
            System.out.println("[1] - Pessoa");
            System.out.println("[2] - Empresa");
            imprimirBorda("=");
            System.out.print("> ");
            String op = TECLADO.nextLine();
            switch (op) {
                case "1":
                    break;
                case "2":
                    cliente = clienteEmpresa;
                    break;
                default:
                    throw new LoginException("Login cancelado");
            }
        } else if (clienteEmpresa != null) {
            cliente = clienteEmpresa;
        }
        cliente.verificarSenha(entrada[1]);
        InterfaceUsuario.setClienteAtual(cliente);
        System.out.println("Login realizado com sucesso");
    }

    private static void mostrarNotificacoes() {
        Conta contaAtual = InterfaceUsuario.usuarioAtualConta();
        if (contaAtual.hasNotificacoes()) {
            for (Transacao notificacao : contaAtual.getNotificacoes()) {
                System.out.println(notificacao);
            }
        } else {
            System.out.println("Voce nao possui nenhuma notificacao");
        }
    }

    private static void mostrarBoletos() throws BuscaException {
        HashSet<Boleto> boletosGerados = Agencia.getInstance().buscarBoletosConta(InterfaceUsuario.usuarioAtualConta());
        if (boletosGerados.size() <= 0) {
            throw new BuscaException("Voce nao possui boletos gerados");
        }
        for (Boleto boleto : boletosGerados) {
            System.out.println(boleto);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void imprimirBorda(String padrao) {
        for (int i = 0; i < TAM_BORDA; i++) {
            System.out.print(padrao);
        }
        System.out.println();
    }
}
