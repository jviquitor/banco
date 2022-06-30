package interfaceUsuario;

import agencia.Agencia;
import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import cliente.ClientePessoa;
import cliente.Endereco;
import cliente.exceptions.LoginException;
import conta.exceptions.TipoInvalido;
import interfaceUsuario.Exceptions.ValorInvalido;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;
import utilsBank.GerenciadorBanco;

import java.util.Scanner;

public class MenuUsuario {
	public static final String STANDARD = "standard";
	public static final String PREMIUM = "premium";
	public static final String DIAMOND = "diamond";
	private static final Scanner teclado = new Scanner(System.in);
	private static final double RENDA_MINIMA = 200.0;
	private static final double RENDA_MAXIMA_STANDARD = 10000.0;
	private static final double RENDA_MAXIMA_PREMIUM = 30000.0;
	private static final double RENDA_MINIMA_DIAMOND = 30001.0;

	public static void iniciar() {
		boolean loop = true;
		while (loop) {
			imprimirBorda("=", 30);
			System.out.println("[0] - Encerrar programa");
			System.out.println("[1] - Acessar conta");
			System.out.println("[2] - Criar conta");
			imprimirBorda("=", 30);
			System.out.print("\n> ");
			try {
				switch (teclado.nextLine()) {
					case "0":
						loop = false;
						break;
					case "1":
						//Login
						logar();
						break;
					case "2":
						criarCliente();
						menuCriacaoConta();
						break;
					default:
						//Opção inválida
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
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
				"Renda",
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
					Double.parseDouble(entradaGeral[4]),
					entradaGeral[5],
					entradaGeral[6]
			);
		} else {
			cliente = new ClienteEmpresa(
					entradaGeral[0],
					entradaGeral[1],
					entradaGeral[2],
					Integer.parseInt(entradaGeral[3]),
					endereco,
					Double.parseDouble(entradaGeral[4]),
					entradaGeral[5],
					entradaGeral[6]
			);
		}
		Agencia.getInstance().addCliente(cliente);
		InterfaceUsuario.setClienteAtual(cliente);
		return cliente;
	}

	private static void menuCriacaoConta() {
		imprimirBorda("-", 20);
		Double renda = inserirRenda();
		boolean debitoAutomatico = false;
		String[] cabecalhoCartoesGeral = {
				"Digite um apelido para seu novo cartao",
				"Deseja Cartao de Credito? [1] SIM [0] NAO",
				"Deseja Cartao de Debito? [1] SIM [0] NAO",
		};

		String[] entradaCartoesGeral = new String[cabecalhoCartoesGeral.length];
		for (int i = 0; i < cabecalhoCartoesGeral.length; i++) {
			System.out.printf("%s:\n> ", cabecalhoCartoesGeral[i]);
			entradaCartoesGeral[i] = teclado.nextLine();
		}
		if (Integer.parseInt(entradaCartoesGeral[1]) == 0) {
			System.out.println("Deseja debito automatico? [1] SIM [0] NAO");
			debitoAutomatico = GerenciadorBanco.intToBoolean(Integer.parseInt(teclado.nextLine()));
		}
		InterfaceUsuario.setDadosConta(new DadosConta(
				tipoDeContaPelaRenda(renda),
				renda,
				GerenciadorBanco.intToBoolean(Integer.parseInt(entradaCartoesGeral[0])),
				GerenciadorBanco.intToBoolean(Integer.parseInt(entradaCartoesGeral[1])),
				debitoAutomatico)
		);
		InterfaceUsuario.setDadosCartao(new DadosCartao(
				entradaCartoesGeral[0],
				entradaCartoesGeral[1],
				entradaCartoesGeral[2]
		));
		InterfaceUsuario.getClienteAtual().criarConta();
	}

	private static String tipoDeContaPelaRenda(Double renda) {
		if (renda <= RENDA_MAXIMA_STANDARD) {
			return STANDARD;
		} else if (renda <= RENDA_MAXIMA_PREMIUM) {
			return PREMIUM;
		} else if (renda > RENDA_MAXIMA_PREMIUM) {
			return DIAMOND;
		}
		throw new TipoInvalido("O tipo de conta nao pode ser definido");
	}


	private static void verificarRenda(Double renda) throws ValorInvalido {
		if (0.0 < renda) {
			throw new ValorInvalido("[ERRO] Valor negativo para sua renda");
		} else if (renda < RENDA_MINIMA) {
			throw new ValorInvalido("[RENDA MINIMA NAO PERMITIDA] As regras da Agencia nao permite essa renda, por favor, consulte nossos termos de uso!");
		}
	}

	private static Double inserirRenda() {
		double renda = 0.0;
		while (renda < RENDA_MINIMA) {
			try {
				System.out.println("Por favor, Insira sua Renda");
				renda = Double.parseDouble(teclado.nextLine());
				verificarRenda(renda);
			} catch (ValorInvalido ex) {
				System.out.println(ex.getMessage());
			}
		}
		return renda;
	}

	private static <T extends Cliente> void  logar() throws LoginException {
		String[] cabecalho = {
				"CPF/CNPJ",
				"Senha",
		};

		String[] entrada = new String[cabecalho.length];
		for (int i = 0; i < cabecalho.length; i++) {
			System.out.printf("%s:\n> ", cabecalho[i]);
			entrada[i] = teclado.nextLine();
		}

		Cliente cliente = Agencia.getInstance().buscarCliente(entrada[0]);
		if (cliente != null) {
			cliente.verificarSenha(entrada[1]);
			InterfaceUsuario.setClienteAtual(cliente);
			System.out.println("Login realizado com sucesso");
		} else {
			throw new LoginException("Cliente nao encontrado");
		}
	}
}
