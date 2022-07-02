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
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosChavesPix;
import interfaceUsuario.dados.DadosConta;
import interfaceUsuario.dados.DadosTransacao;
import utilsBank.GerenciadorBanco;
import java.util.Scanner;

public class MenuUsuario {
	public static final String DEPOSITO = "do seu Deposito";
	public static final String TRANSFERENCIA = "da sua transferencia";
	public static final String PAGAMENTO = "do seu pagamento";
	public static final String FORMATO_DATAS = "31/12/2002";
	public static final String BOLETO = "boleto";
	public static final String PIX = "pix";
	public static final String DEBITO = "debito";
	public static final String CREDITO = "credito";
	public static final String CHAVES_DISPONIVEIS = "chave_aleatoria | telefone | email | identificacao [CPF OU CNPJ]";
	private static final Scanner teclado = new Scanner(System.in);

	public static void iniciar() throws InsercaoException, LoginException {
		boolean loop = true;
		while (loop) {
			imprimirBorda("=", 30);
			System.out.println("[0] - Encerrar programa");
			System.out.println("[1] - Acessar conta");
			System.out.println("[2] - Criar conta");
			imprimirBorda("=", 30);
			System.out.print("\n> ");
			//try {
				switch (teclado.nextLine()) {
					case "0":
						loop = false;
						break;
					case "1":
						if (logar()) {
							menuCliente();
						};
						InterfaceUsuario.setClienteAtual(null);
						break;
					case "2":
						InterfaceUsuario.setClienteAtual(criarCliente());
						menuCliente();
						InterfaceUsuario.setClienteAtual(null);
						break;
					default:
						//Opção inválida
				}
			} //catch (Exception ex) {
				//System.out.println(ex.getMessage());
			//}
		//}
	}

	public static void menuCliente() {
		boolean loop = true;
		Cliente cliente = InterfaceUsuario.getClienteAtual();
		if (cliente != null) {

			while (loop) {
				imprimirBorda("=", 30);
				System.out.println("Bem vindo " + cliente.getNome());
				System.out.println("[0] - Sair");
				System.out.println("[1] - Verificar Saldo");
				System.out.println("[2] - Transferir");
				System.out.println("[3] - Pagar");
				System.out.println("[4] - Depositar");
				System.out.println("[5] - Emprestimo");
				System.out.println("[6] - Agendar transferencia");
				System.out.println("[7] - Pagar fatura");
				System.out.println("[8] - Criar chave Pix");
				System.out.println("[9] - Modificar chave Pix");
				imprimirBorda("=", 30);
				System.out.print("\n> ");
				try {
					String value = teclado.nextLine();
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
							cliente.getConta().transferir();
							break;
						case "3":
							MenuDadosTransacao(PAGAMENTO);
							cliente.getConta().pagar();
							break;
						case "4":
							MenuDadosTransacao(DEPOSITO);
							cliente.getConta().depositar();
							break;
						case "5":
							//menuEmprestimo();
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
		imprimirBorda("-", 20);
		String[] entradas = new String[cabecalhoUsuario.length];

		for (int i = 0; i < cabecalhoUsuario.length; i++) {
			System.out.printf("%s:\n> ", cabecalhoUsuario[i]);
			entradas[i] = teclado.nextLine();
		}

		return entradas;
	}

	public static void MenuDadosTransacao(String tipoOperacao) {
		imprimirBorda("-", 20);
		String[] cabecalhoDadosTransacao = {
				"Digite o valor " + tipoOperacao,
				"Digite o tipo de Transacao [FORMATO DE ESCRITA: " + MenuUsuario.BOLETO + " OU " + MenuUsuario.PIX + "]",
		};

		String[] entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);

		while(!VerificadorEntrada.verificarDadosTransacao(entradaDadosTransacao[0])) {
			//TODO o verificar Dados transacao precisa verificar se o valor da transacao e o tipo da transacao esta correto
			 entradaDadosTransacao = UsuarioEntradas(cabecalhoDadosTransacao);
		}

		if (entradaDadosTransacao[1].equals(MenuUsuario.BOLETO)) {
			String[] cabecalhoDadosBoleto = {
					"Digite o dia de vencimento do boleto [FORMATO DA DATA: " + FORMATO_DATAS + "]",
					"Digite o valor da multa por dias do boleto",
			};
			String[] entradaDadosBoleto = UsuarioEntradas(cabecalhoDadosBoleto);

			InterfaceUsuario.setDadosTransacao(new DadosTransacao(
					Double.parseDouble(entradaDadosTransacao[0]),
					entradaDadosBoleto[0],
					Integer.parseInt(entradaDadosBoleto[1]),
					MenuUsuario.BOLETO,
					InterfaceUsuario.getClienteAtual().getIdentificacao())
			);

		} else if (entradaDadosTransacao[1].equals(MenuUsuario.PIX)) {
			String[] cabecalhoDadosPix = {
					"Digite o tipo da chave inserida [FORMATOS DISPONIVEIS]: " + CHAVES_DISPONIVEIS,
					"Digite a chave",
			};
			String[] entradaDadosPix = UsuarioEntradas(cabecalhoDadosPix);

			InterfaceUsuario.setDadosTransacao(new DadosTransacao(
					Double.parseDouble(entradaDadosTransacao[0]), //VALOR
					MenuUsuario.PIX, //TIPO DA TRANSACAO
					InterfaceUsuario.getClienteAtual().getIdentificacao(), //CHAVE COBRADOR QUEM ESTA COBRANDO O DINHEIRO
					entradaDadosPix[1], //CHAVE PAGADOR
					DadosChavesPix.IDENTIFICACAO, //TIPO DE CHAVE DO COBRADOR
					entradaDadosPix[0] //TIPO CHAVE PAGADOR
			));
		}
	}

	private static Cliente criarCliente() throws InsercaoException {
		imprimirBorda("-", 20);
		System.out.print("Tipo de cliente:\n" +
						 "[0] - Cancelar\n" +
						 "[1] - Pessoa fisica\n" +
						 "[2] - Pessoa juridica\n" +
						 "> \n");
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

		while (!VerificadorEntrada.verificarEntradasCartao(entradas)) {
			entradas = UsuarioEntradas(cabecalhoCartoesGeral);
		}

		String FuncaoCartao = EscolhendoFuncaoDoCartao(entradas[1]);

		if (FuncaoCartao.equals(CREDITO)) {
			System.out.println("Deseja debito automatico? [1] SIM [0] NAO");
			//TODO tratar caso o usuario n coloque nem 1 nem 0
			debitoAutomatico = GerenciadorBanco.intToBoolean(Integer.parseInt(teclado.nextLine()));
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

	private static void menuEmprestimo() throws EmprestimoException {
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
				contaAtual.setParcelaEmprestimo(valor/parcelas);
			} else {
				throw new EmprestimoException();
			}
		} else {
			throw new EmprestimoException("Seu limite e insuficiente para esse emprestimo");
		}
	}

	private static void pagarEmprestimo() throws EmprestimoException {
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

	private static <T extends Cliente> boolean logar() throws LoginException {
		String[] cabecalho = {
				"CPF/CNPJ",
				"Senha",
		};

		String[] entrada = UsuarioEntradas(cabecalho);

		Cliente cliente = Agencia.buscarCliente(entrada[0]);
		if (cliente != null) {
			cliente.verificarSenha(entrada[1]);
			InterfaceUsuario.setClienteAtual(cliente);
			System.out.println("Login realizado com sucesso");
			return true;
		} else {
			throw new LoginException("Cliente nao encontrado");
		}
	}

	private static void imprimirBorda(String padrao, int tam) {
		for (int i = 0; i < tam; i++) {
			System.out.print(padrao);
		}
		System.out.println();
	}
}
