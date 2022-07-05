package interfaceUsuario;

import agencia.Agencia;
import agencia.exceptions.BuscaException;
import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import cliente.ClientePessoa;
import cliente.Endereco;
import cliente.exceptions.GerenteJaExistenteException;
import cliente.exceptions.GerenteNaoEncontradoException;
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
	public static final int TAM_BORDA = 50;
	public static final String CHAVES_DISPONIVEIS = "[ESCREVA O TIPO DA CHAVE EXATAMENTE COMO ALGUMAS DESSES TIPOS, IDENTIFICACAO [CPF OU CNPJ]\n" +
			" chave_aleatoria | telefone | email | identificacao";
	public static final String CHAVES_DISPONIVEIS_ALTERACAO = "chave_aleatoria | telefone | email";
	private static final Scanner teclado = new Scanner(System.in);

	public static void iniciar() {
		boolean loop = true;
		while (loop) {
			imprimirBorda("=", TAM_BORDA);
			System.out.println("[0] - Encerrar programa");
			System.out.println("[1] - Acessar conta");
			System.out.println("[2] - Criar conta");
			imprimirBorda("=", TAM_BORDA);
			System.out.print("\n> ");
			///TESTES APENAS Agencia.imprimirClientes();
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
//TODO BUG AGENDAR TRANSFERENCIA
// TODO BUG BOLETO
// TODO Tela dos boletos gerados

	private static void menuCliente() {
		boolean loop = true;
		Cliente cliente = InterfaceUsuario.getClienteAtual();
		boolean isClientePessoa = true;
		if (cliente != null) {
			imprimirBorda("=", TAM_BORDA);
			System.out.println("Bem vindo " + cliente.getNome());

			while (loop) {
				if (cliente instanceof ClientePessoa) {
					isClientePessoa = true;
					imprimirMenu(true);
				} else if (cliente instanceof ClienteEmpresa) {
					isClientePessoa = false;
					imprimirMenu(false);
				}

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
							double valor = MenuUsuarioGuardarDinheiro();
							cliente.getConta().setDinheiroGuardado(valor);
							break;
						case "3":
							MenuDadosTransacao(TRANSFERENCIA);
							t = cliente.getConta().transferir();

							t.gerarComprovante();
							GerenciadorBanco.imprimirDireitos();
							break;
						case "4":
							MenuPagarBoleto();
							System.out.println("Boleto pago!");
							break;
						case "5":
							MenuDadosTransacao(DEPOSITO);
							t = cliente.getConta().depositar();
							t.gerarComprovante();
							GerenciadorBanco.imprimirDireitos();
							break;
						case "6":
							menuEmprestimo();
							break;
						case "7":
							//TODO com bug agendamentoTransferiencia
							menuAgendarTransferencia();
							t = cliente.getConta().agendarTransacao();
							t.gerarComprovante();
							GerenciadorBanco.imprimirMensagemTransferenciaAgendada();
							GerenciadorBanco.imprimirDireitos();
							break;
						case "8":
							//TODO GERENCIAMENTO DOS CARTOES FATURA ETC
							break;
						case "9":
							System.out.println(cliente.getConta().getChavesPix().toString());
							break;
						case "10":
							menuAdicionarChavePix();
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
							for (Transacao transacao : cliente.getConta().getHistorico()) {
								imprimirBorda("-", TAM_BORDA);
								System.out.println(transacao);
							}
							break;
						case "13":
							if (!isClientePessoa) {
								String identificacaoNovoGerente = MenuIdentificarNovoGerente();
								assert cliente instanceof ClienteEmpresa;
								try {
									if (((ClienteEmpresa) cliente).addGerentes(identificacaoNovoGerente)) {
										System.out.println("Novo gerente adicionado com sucesso!");
									}
								} catch (GerenteJaExistenteException ex) {
									System.out.println(ex.getMessage());
								}
							}
							break;
						case "14":
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
							//Opção inválida
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
	}

	private static Double MenuUsuarioGuardarDinheiro() throws ValorInvalido {
		imprimirBorda("-", TAM_BORDA);
		String[] cabecalhoDinheiroGuardado = {
				"Digite o valor para ser guardado!",
		};
		String[] entrada = UsuarioEntradas(cabecalhoDinheiroGuardado);

		while (!VerificadorEntrada.verificarEntradaValor(entrada[0])) {
			entrada = UsuarioEntradas(cabecalhoDinheiroGuardado);
		}
		return Double.parseDouble(entrada[0]);
	}

	private static String MenuIdentificarNovoGerente() {
		imprimirBorda("-", TAM_BORDA);
		String[] cabecalhoNovoGerente = {
				"Digite a identificacao do novo gerente",
		};
		String[] entrada = UsuarioEntradas(cabecalhoNovoGerente);

		while (!VerificadorEntrada.verificarIdentidadeGerente(entrada[0])) {
			entrada = UsuarioEntradas(cabecalhoNovoGerente);
		}
		return entrada[0];
	}

	private static void imprimirMenu(boolean isClientePessoa) {
		imprimirBorda("=", TAM_BORDA);
		System.out.println("[0] - Sair");
		System.out.println("[1] - Verificar Saldo");
		System.out.println("[2] - Guardar dinheiro");
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
		//TODO System.out.println("[13] - Mostrar dinheiro Guardado");

		if (!isClientePessoa) {
			System.out.println("[13] - Adicionar Gerentes");
			System.out.println("[14] - Remover Gerentes");

		}
		imprimirBorda("=", TAM_BORDA);
		System.out.print("\n> ");

	}

	private static String[] UsuarioEntradas(String[] cabecalhoUsuario) {
		String[] entradas = new String[cabecalhoUsuario.length];

		for (int i = 0; i < cabecalhoUsuario.length; i++) {
			System.out.printf("%s:\n> ", cabecalhoUsuario[i]);
			entradas[i] = teclado.nextLine();
		}
		imprimirBorda("-", TAM_BORDA);
		return entradas;
	}

	private static void MenuDadosTransacao(String tipoOperacao) throws BuscaException, ValorInvalido {
		imprimirBorda("-", TAM_BORDA);

		String[] cabecalhoDadosTransacao = {
				"Digite o valor ",
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

	private static void menuAgendarTransferencia() throws BuscaException, ValorInvalido {
		imprimirBorda("-", TAM_BORDA);
		String[] cabecalhoDadosTransacao = {
				"Digite o valor ",
				"Digite a data para realizar a Transferencia"
		};

		String[] entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);

		while (!VerificadorEntrada.verificarDadosAgendamentoTransacao(entradaDadosTransacao)) {
			entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);
		}


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
						InterfaceUsuario.getClienteAtual().getStringIdentificacao(),
						entradaDadosTransacao[1]
				)
		);
	}

	private static void MenuPagarBoleto() throws BuscaException, TransacaoException {
		System.out.print("Numero do boleto: \n> ");
		String numBoleto = teclado.nextLine();
		Boleto boleto = Agencia.buscarBoleto(numBoleto);
		Conta origem = InterfaceUsuario.usuarioAtualConta();
		origem.pagarBoleto(boleto);
	}

	private static Boleto criarBoleto() {
		DadosBoleto dadosBoleto = InterfaceUsuario.getDadosBoleto();
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();

		Boleto boleto = new Boleto(dadosTransacao, dadosBoleto);
		Agencia.addBoleto(boleto);

		return boleto;
	}

	private static void MenuDadosGerarBoleto() {
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

		InterfaceUsuario.setDadosBoleto(dadosBoleto);
		InterfaceUsuario.setDadosTransacao(dadosTransacao);
	}

	private static void menuAdicionarChavePix() {
		String[] cabecalhoTipoPix = {
				"[ESCREVA] Escolha o tipo de chave que deseja adicionar ou modificar!\n  " + CHAVES_DISPONIVEIS_ALTERACAO,
		};
		String[] entradas = UsuarioEntradas(cabecalhoTipoPix);

		while (!VerificadorEntrada.verificarEntradaTipoChavePix(entradas[0])) {
			entradas = UsuarioEntradas(cabecalhoTipoPix);
		}
		if (entradas[0].equals(DadosChavesPix.CHAVE_ALEATORIA)) {
			InterfaceUsuario.setDadosChavePix(new DadosChavesPix(null, null, DadosChavesPix.CHAVE_ALEATORIA, true));
		} else {
			String[] cabecalhoChave = {
					"DIGITE A CHAVE CORRETAMENTE\n  " + CHAVES_DISPONIVEIS_ALTERACAO,
			};
			String[] entradaChave = UsuarioEntradas(cabecalhoChave);

			while (!VerificadorEntrada.verificarChavePix(entradaChave[0], entradas[0])) {
				entradas = UsuarioEntradas(cabecalhoTipoPix);
			}

			switch (entradas[0]) {
				case DadosChavesPix.TELEFONE:
					InterfaceUsuario.setDadosChavePix(new DadosChavesPix(entradaChave[0], null, DadosChavesPix.TELEFONE, false));
					break;
				case DadosChavesPix.EMAIL:
					InterfaceUsuario.setDadosChavePix(new DadosChavesPix(null, entradaChave[0], DadosChavesPix.EMAIL, false));
					break;
			}
		}
	}

	private static Cliente criarCliente() throws InsercaoException, EscritaArquivoException, RuntimeException, BuscaException {
		imprimirBorda("-", TAM_BORDA);
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
		int idade = Integer.parseInt(entradaGeral[3]);
		if (tipo.equalsIgnoreCase("1")) {
			if (idade < 18) {
				throw new IllegalArgumentException("Idade insuficiente");
			}
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
			if (idade < 3) {
				throw new IllegalArgumentException("Idade insuficiente");
			}
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
		imprimirBorda("-", TAM_BORDA);
		System.out.println("[0] - Cancelar");
		System.out.println("[1] - Pedir emprestimo");
		System.out.println("[2] - Pagar emprestimo");
		imprimirBorda("-", TAM_BORDA);
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
		imprimirBorda("-", TAM_BORDA);
		System.out.println("[0] - Cancelar");
		System.out.printf("[1] - Pagar parcela (%.2f)\n", contaAtual.getParcelaEmprestimo());
		System.out.printf("[2] - Pagar total (%.2f)\n", contaAtual.getEmprestimo());
		imprimirBorda("-", TAM_BORDA);
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

	private static void logar() throws LoginException, BuscaException {
		String[] cabecalho = {
				"CPF/CNPJ",
				"Senha",
		};

		String[] entrada = UsuarioEntradas(cabecalho);

		Cliente cliente = Agencia.buscarCliente(entrada[0]);
		ClienteEmpresa clienteEmpresa = Agencia.buscarEmpresa(entrada[0]);
		if (clienteEmpresa != null) {
			imprimirBorda("-", TAM_BORDA);
			System.out.println("Entrar como:");
			System.out.println("[0] - Cancelar");
			System.out.println("[1] - Pessoa");
			System.out.println("[2] - Empresa");
			imprimirBorda("-", TAM_BORDA);
			System.out.print("> ");
			String op = teclado.nextLine();
			switch (op) {
				case "1":
					break;
				case "2":
					cliente = clienteEmpresa;
					break;
				default:
					throw new LoginException("Login cancelado");
			}
		}
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
