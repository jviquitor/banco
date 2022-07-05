package interfaceUsuario;

import conta.exceptions.TipoInvalido;
import interfaceUsuario.Exceptions.ValorInvalido;
import interfaceUsuario.dados.DadosChavesPix;

import java.util.Scanner;

import static interfaceUsuario.MenuUsuario.DEPOSITO;
import static interfaceUsuario.MenuUsuario.TRANSFERENCIA;

public class VerificadorEntrada {
	public static final String STANDARD = "standard";
	public static final String PREMIUM = "premium";
	public static final String DIAMOND = "diamond";
	protected static final double RENDA_MINIMA = 200.0;
	protected static final double RENDA_MAXIMA_STANDARD = 10000.0;
	protected static final double RENDA_MAXIMA_PREMIUM = 30000.0;
	protected static final double RENDA_MINIMA_DIAMOND = 30001.0;
	protected static final String[] ENTRADAS_CHAVE_PIX = {"chave_aleatoria", "telefone", "email"};
	protected static final int DIGITOS_MAXIMO_TELEFONE = 9;
	protected static final int QUANTIDADE_IDENTIFICACAO_VALIDA = 14;
	private static final Scanner teclado = new Scanner(System.in);

	protected static boolean verificarEntradasZeroUm(String[] entradas) {
		return entradas[1].equals("0") || entradas[1].equals("1");
	}

	protected static boolean verificarEntradasZeroUm(String entrada) {
		return entrada.equals("0") || entrada.equals("1");
	}

	protected static boolean verificarDadosTransacao(String entrada, String tipoOperacao) throws ValorInvalido {
		int value = -1;
		try {
			value = Integer.parseInt(entrada);
		} catch (Exception exception) {
			System.out.println("Por favor, coloque um valor valido.");
			value = Integer.parseInt(entrada);
		}
		if (tipoOperacao.equals(TRANSFERENCIA)) {
			return verificarEntradaValor(entrada);
		} else if (tipoOperacao.equals(DEPOSITO)) {
			return value > 0.0;
		}
		//TODO VERIFICACAO DO BOLETO;
		return false;
	}

	protected static boolean verificarEntradaTipoChavePix(String entrada) {
		for (String palavra : ENTRADAS_CHAVE_PIX) {
			if (entrada.equals(palavra)) {
				return true;
			}
		}
		return false;
	}

	private static boolean verificarTelefone(String e) {
		return e.length() <= DIGITOS_MAXIMO_TELEFONE;
	}

	private static boolean verificarEmail(String e) {
		return e.contains("@");
	}

	private static boolean verificadorIdentificacao(String e) {
		int id;
		try {
			id = Integer.parseInt(e);
		} catch (NumberFormatException ex) {
			return true;
		}
		return false;
	}

	protected static boolean verificarChavePix(String entrada, String tipoChavePix) {
		System.out.println("A CHAVE INSERIDA " + entrada + " ESTA CORRETA? [1] SIM! [0] NAO, PRECISO TROCAR");
		if (teclado.nextLine().equals("0")) {
			return false;
		} else {
			switch (tipoChavePix) {
				case DadosChavesPix.TELEFONE:
					return verificarTelefone(entrada);
				case DadosChavesPix.EMAIL:
					return verificarEmail(entrada);
			}
		}
		return false;
	}

	protected static void verificarRenda(Double renda) throws ValorInvalido {
		if (renda < 0.0) {
			throw new ValorInvalido("[ERRO] Valor negativo para sua renda");
		} else if (renda < RENDA_MINIMA) {
			throw new ValorInvalido("[RENDA MINIMA NAO PERMITIDA] As regras da Agencia nao permite essa renda, por favor, consulte nossos termos de uso!");
		}
	}

	protected static String tipoDeContaPelaRenda(Double renda) {
		if (renda <= RENDA_MAXIMA_STANDARD) {
			return STANDARD;
		} else if (renda <= RENDA_MAXIMA_PREMIUM) {
			return PREMIUM;
		} else if (renda > RENDA_MAXIMA_PREMIUM) {
			return DIAMOND;
		}
		throw new TipoInvalido("O tipo de conta nao pode ser definido");
	}

	protected static boolean verificarEntradaValor(String s) throws ValorInvalido {
		double valor;
		try {
			valor = Double.parseDouble(s);
		} catch (NumberFormatException ex) {
			throw new TipoInvalido("[ERRO: VALOR INSERIDO INCORRETAMENTE] O valor de entrada deve ser maior que 0");
		}
		if (valor < 0.0) {
			throw new ValorInvalido("[ERRO] Valor negativo para operacao");
		}
		if (valor >= InterfaceUsuario.getClienteAtual().getConta().getSaldo()) {
			throw new ValorInvalido("[ERRO] Nao ha saldo suficiente para realizar essa operacao!");
		}

		return true;
	}

	protected static boolean verificarIdentidadeGerente(String s) {
		if (s.length() <= QUANTIDADE_IDENTIFICACAO_VALIDA) {
			return verificadorIdentificacao(s);
		}
		return false;
	}

	//TODO VERIFICAR DATA
	private static boolean verificarData() {
		return true;
	}

	protected static boolean verificarDadosAgendamentoTransacao(String[] s) throws ValorInvalido {
		if (verificarDadosTransacao(s[0], TRANSFERENCIA)) {
			return verificarData();
		}
		return false;
	}
}
