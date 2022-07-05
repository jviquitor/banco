package interfaceUsuario;

import agencia.Agencia;
import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import cliente.ClientePessoa;
import cliente.Endereco;
import cliente.exceptions.LoginException;
import conta.Conta;
import conta.exceptions.TipoInvalido;
import funcionalidades.exceptions.EmprestimoException;
import interfaceUsuario.Exceptions.ValorInvalido;
import interfaceUsuario.Exceptions.printUtils;
import interfaceUsuario.dados.*;
import transacao.Boleto;
import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.GerenciadorBanco;
import utilsBank.arquivo.Exception.EscritaArquivoException;

import java.util.Scanner;

public class MenuUsuario {
	public static final String DEPOSITO = "do seu Deposito";
	public static final String TRANSFERENCIA = "da sua transferencia";
	public static final String PAGAMENTO = "do seu pagamento";
	public static final String FORMATO_DATAS = "31/12/2002";
	public static final String DEBITO = "debito";
	public static final String CREDITO = "credito";
	public static final String CHAVES_DISPONIVEIS = "[ESCREVA O TIPO DA CHAVE EXATAMENTE COMO ALGUMAS DESSES TIPOS, IDENTIFICACAO [CPF OU CNPJ]\n" +
			" chave_aleatoria | telefone | email | identificacao";
	public static final String CHAVES_DISPONIVEIS_ALTERACAO = "chave_aleatoria | telefone | email";
	private static final Scanner teclado = new Scanner(System.in);

	public static void iniciar() {
		boolean loop = true;
		while (loop) {
			imprimirBorda("=", 30);
			System.out.println("[0] - Encerrar programa");
			System.out.println("[1] - Acessar conta");
			System.out.println("[2] - Criar conta");
			imprimirBorda("=", 30);
			System.out.print("\n> ");
			//APENAS TESTES Agencia.imprimirClientes();
			try {
				switch (teclado.nextLine()) {
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
						InterfaceUsuario.getClienteAtual().setChavesPix();
						menuCliente();
						InterfaceUsuario.setClienteAtual(null);
						break;
					default:
						//Opção inválida
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static void menuCliente() {
		boolean loop = true;
		Cliente cliente = InterfaceUsuario.getClienteAtual();
		if (cliente != null) {
			imprimirBorda("=", 30);
			System.out.println("Bem vindo " + cliente.getNome());
			while (loop) {
				imprimirBorda("=", 30);
				System.out.println("[0] - Sair");
				System.out.println("[1] - Verificar Saldo");
				System.out.println("[2] - Transferir");
				System.out.println("[3] - Pagar");
				System.out.println("[4] - Depositar");
				System.out.println("[5] - Emprestimo");
				System.out.println("[6] - Agendar transferencia");
				System.out.println("[7] - Gerenciar Cartoes");
				System.out.println("[8] - Mostrar chaves Pix");
				System.out.println("[9] - Modificar chave Pix");
				System.out.println("[10] - Gerar um boleto");
				imprimirBorda("=", 30);
				System.out.print("\n> ");
				try {
					String value = teclado.nextLine();
					Transacao t;
					switch (value) {
						case "0":
							System.out.println("Obrigada por acessar ao nosso Internet Banking!");
							loop = false;
							break;
						case "1":
							System.out.println("[SALDO]: " + cliente.getConta().getSaldo());
							break;
						case "2":
							MenuDadosTransacao(TRANSFERENCIA);
							t = cliente.getConta().transferir();
							t.gerarComprovante();
							GerenciadorBanco.imprimirDireitos();
							break;
						case "3":
							//TODO BOLETO ARRUMAR O TOSTRING PFV
							MenuDadosPagarBoleto();
							cliente.getConta().pagar();
							break;
						case "4":
							MenuDadosTransacao(DEPOSITO);
							t = cliente.getConta().depositar();
							t.gerarComprovante();
							GerenciadorBanco.imprimirDireitos();
							break;
						case "5":
							menuEmprestimo();
							break;
						case "6":
							MenuDadosTransacao(TRANSFERENCIA);
							t = cliente.getConta().agendarTransacao();
							t.gerarComprovante();
							GerenciadorBanco.imprimirMensagemTransferenciaAgendada();
							GerenciadorBanco.imprimirDireitos();
							break;
						case "7":
							//TODO
							break;
						case "8":
							System.out.println(cliente.getConta().getChavesPix().toString());
							break;
						case "9":
							//TODO TA DANDO BUG
							menuAdicionarChavePix();
							if (cliente.getConta().modificarChavePix()) {
								System.out.println("Chave Pix modificada com sucesso");
								System.out.println(cliente.getConta().getChavesPix());
							}
							break;
						case "10":
							MenuDadosGerarBoleto();
							break;
						default:
							//Opção inválida
					}
				} catch (Exception ex) {
					System.out.println(ex.getCause());
				}
			}
		}
	}

	private static String[] UsuarioEntradas(String[] cabecalhoUsuario) {
		String[] entradas = new String[cabecalhoUsuario.length];

		for (int i = 0; i < cabecalhoUsuario.length; i++) {
			System.out.printf("%s:\n> ", cabecalhoUsuario[i]);
			entradas[i] = teclado.nextLine();
		}
		imprimirBorda("-", 30);
		return entradas;
	}

	public static String[] MenuDadosTransacao(String tipoOperacao) {
		imprimirBorda("-", 20);

		String[] cabecalhoDadosTransacao = {
				"Digite o valor " + tipoOperacao,
		};

		String[] entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);

		while (!VerificadorEntrada.verificarDadosTransacao(entradaDadosTransacao[0], tipoOperacao)) {
			entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);
		}

		if (tipoOperacao.equals(TRANSFERENCIA)) {
			printUtils.avisoPix();
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
					InterfaceUsuario.getClienteAtual().getStringIdentificacao())
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

	public static void MenuDadosPagarBoleto() {
		String[] entradaDadosTransacao = MenuDadosTransacao(PAGAMENTO);
		//TODO fazer a criacao do corpo do boleto new boleto
	}

	public static void MenuDadosGerarBoleto() {
		String[] cabecalho = {
				"Valor",
				"Data de vencimento [EXEMPLO DE FORMATO CORRETO: " + FORMATO_DATAS + "]",
				"Multa por dias",
		};
		String[] entrada = new String[cabecalho.length];
		for (int i = 0; i < cabecalho.length; i++) {
			System.out.printf("%s: \n> ", cabecalho[i]);
			entrada[i] = teclado.nextLine();
		}
		DadosTransacao dadosTransacao = new DadosTransacao(Double.parseDouble(entrada[0]), InterfaceUsuario.getClienteAtual());
		DadosBoleto dadosBoleto = new DadosBoleto(entrada[1], Integer.parseInt(entrada[2]), false);
		Boleto boleto = new Boleto(dadosTransacao, dadosBoleto);
		Agencia.addBoleto(boleto);
		System.out.println("Boleto gerado!");
		System.out.println(boleto);
	}

	public static void menuAdicionarChavePix() {
		String[] cabecalhoTipoPix = {
				"[ESCREVA] Escolha o tipo de chave que deseja adicionar ou modificar!\n  " + CHAVES_DISPONIVEIS_ALTERACAO,
		};
		String[] entradas = UsuarioEntradas(cabecalhoTipoPix);

		while (!VerificadorEntrada.verificarEntradaTipoChavePix(entradas[0])) {
			entradas = UsuarioEntradas(cabecalhoTipoPix);
		}
		String[] cabecalhoChave = {
				"DIGITE A CHAVE CORRETAMENTE\n  " + CHAVES_DISPONIVEIS_ALTERACAO,
		};
		String[] entradaChave = UsuarioEntradas(cabecalhoChave);

		while (!VerificadorEntrada.verificarChavePix(entradaChave[0], entradas[0])) {
			entradas = UsuarioEntradas(cabecalhoTipoPix);
		}

		switch (entradas[0]) {
			case DadosChavesPix.TELEFONE:
				new DadosChavesPix(entradaChave[0], null, null, false);
				break;
			case DadosChavesPix.EMAIL:
				new DadosChavesPix(null, entradaChave[0], null, false);
				break;
			case DadosChavesPix.CHAVE_ALEATORIA:
				new DadosChavesPix(null, null, null, true);
				break;
		}

	}

	private static Cliente criarCliente() throws InsercaoException, EscritaArquivoException {
		imprimirBorda("-", 20);
		System.out.print("Tipo de cliente:\n" +
				"[0] - Cancelar\n" +
				"[1] - Pessoa fisica\n" +
				"[2] - Pessoa juridica\n" +
				"> \n");
		String tipo = teclado.nextLine();

		if (!tipo.equals("1") && !tipo.equals("2")) {
			throw new RuntimeException("Criacao cancelada");
		}

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
				tag,
				"Senha",
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
					entradaGeral[4],
					entradaGeral[5]
			);
		} else {
			cliente = new ClienteEmpresa(
					entradaGeral[0],
					entradaGeral[1],
					entradaGeral[2],
					Integer.parseInt(entradaGeral[3]),
					endereco,
					entradaGeral[4],
					entradaGeral[5]
			);
		}
		Agencia.getInstance().addCliente(cliente);
		return cliente;
	}
	public static Double menuCriacaoConta() {
		Double renda = inserirRenda();
		boolean debitoAutomatico = false;

		String[] cabecalhoCartoesGeral = {
				"Digite um apelido para seu novo cartao", //entrada[0]
				"[DIGITE] [1]: PARA SEU CARTAO SER DE CREDITO | DIGITE [0] PARA SEU NOVO CARTAO SER DE DEBITO.", //entrada[1]
		};

		String[] entradas = UsuarioEntradas(cabecalhoCartoesGeral);

		while (!VerificadorEntrada.verificarEntradasZeroUm(entradas)) {
			entradas = UsuarioEntradas(cabecalhoCartoesGeral);
		}

		String FuncaoCartao = EscolhendoFuncaoDoCartao(entradas[1]);
		String entrada;
		if (FuncaoCartao.equals(CREDITO)) {
			System.out.println("Deseja debito automatico? [1] SIM [0] NAO");
			entrada = teclado.nextLine();
			while (!VerificadorEntrada.verificarEntradasZeroUm(entrada)) {
				System.out.println("Por favor, insira corretamente a opcao!");
				entrada = teclado.nextLine();
			}
			debitoAutomatico = GerenciadorBanco.intToBoolean(Integer.parseInt(entrada));
		}

		InterfaceUsuario.setDadosConta(new DadosConta(
				VerificadorEntrada.tipoDeContaPelaRenda(renda),
				GerenciadorBanco.intToBoolean(Integer.parseInt(entradas[1])),
				debitoAutomatico)
		);
		InterfaceUsuario.setDadosCartao(new DadosCartao(
				entradas[0], //Apelido do cartao
				FuncaoCartao
		));
		return renda;
	}

	private static String EscolhendoFuncaoDoCartao(String entrada) {
		if (entrada.equals("1")) {
			return CREDITO;
		} else if (entrada.equals("0")) {
			return DEBITO;
		}
		throw new TipoInvalido("Nenhum valor correto para a funcao do cartao!");
	}

	private static Double inserirRenda() {
		double renda = 0.0;
		while (renda < VerificadorEntrada.RENDA_MINIMA) {
			try {
				System.out.println("Por favor, Insira sua Renda");
				renda = Double.parseDouble(teclado.nextLine());
				VerificadorEntrada.verificarRenda(renda);
			} catch (ValorInvalido ex) {
				System.out.println(ex.getMessage());
			}
		}
		return renda;
	}

	private static void menuEmprestimo() {
		imprimirBorda("-", 20);
		System.out.println("[0] - Cancelar");
		System.out.println("[1] - Pedir emprestimo");
		System.out.println("[2] - Pagar emprestimo");
		imprimirBorda("-", 20);
		System.out.print("\n> ");
		try {
			String op = teclado.nextLine();
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
					//Opção inválida
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static void gerarEmprestimo() throws EmprestimoException {
		String[] cabecalho = {
				"Valor do emprestimo: ",
				"Quantidade de parcelas (ate 12x): ",
		};
		String[] entrada = UsuarioEntradas(cabecalho);

		int parcelas = Integer.parseInt(entrada[1]);
		Double valor = Double.parseDouble(entrada[0]);
		Conta contaAtual = InterfaceUsuario.usuarioAtualConta();
		if (contaAtual.getCarteira().getLimiteMaximo() >= valor) {
			if (0 < parcelas && parcelas <= 12 && !contaAtual.hasEmprestimo()) {
				Agencia.getInstance().pegarEmprestimo(valor);
				contaAtual.setEmprestimo(valor);
				contaAtual.setParcelaEmprestimo(valor / parcelas);
			} else {
				throw new EmprestimoException();
			}
		} else {
			throw new EmprestimoException("Seu limite e insuficiente para esse emprestimo");
		}
	}

	private static void pagarEmprestimo() {
		Conta contaAtual = InterfaceUsuario.usuarioAtualConta();
		imprimirBorda("-", 15);
		System.out.println("[0] - Cancelar");
		System.out.printf("[1] - Pagar parcela (%.2f)\n", contaAtual.getParcelaEmprestimo());
		System.out.printf("[2] - Pagar total (%.2f)\n", contaAtual.getEmprestimo());
		imprimirBorda("-", 15);
		System.out.print("\n> ");
		try {
			String op = teclado.nextLine();
			switch (op) {
				case "0":
					break;
				case "1":
					contaAtual.pagarParcelaEmprestimo();
					break;
				case "2":
					contaAtual.pagarEmprestimo();
				default:
					//Opção inválida
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	private static <T extends Cliente> void logar() throws LoginException, BuscaException {
		String[] cabecalho = {
				"CPF/CNPJ",
				"Senha",
		};

		String[] entrada = UsuarioEntradas(cabecalho);

		Cliente cliente = Agencia.buscarCliente(entrada[0]);
		cliente.verificarSenha(entrada[1]);
		InterfaceUsuario.setClienteAtual(cliente);
		System.out.println("Login realizado com sucesso");
	}

	private static void imprimirBorda(String padrao, int tam) {
		for (int i = 0; i < tam; i++) {
			System.out.print(padrao);
		}
		System.out.println();
	}
}
