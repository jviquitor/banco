package interfaceUsuario;

import conta.exceptions.TipoInvalido;
import interfaceUsuario.Exceptions.ValorInvalido;

public class VerificadorEntrada {
	public static final String STANDARD = "standard";
	public static final String PREMIUM = "premium";
	public static final String DIAMOND = "diamond";
	protected static final double RENDA_MINIMA = 200.0;
	protected static final double RENDA_MAXIMA_STANDARD = 10000.0;
	protected static final double RENDA_MAXIMA_PREMIUM = 30000.0;
	protected static final double RENDA_MINIMA_DIAMOND = 30001.0;

	protected static boolean verificarEntradasZeroUm(String[] entradas) {
		return entradas[1].equals("0") || entradas[1].equals("1");
	}
	protected static boolean verificarEntradasZeroUm(String entrada) {
		return entrada.equals("0") || entrada.equals("1");
	}
	protected static boolean verificarDadosTransacao(String entrada, String tipoOperacao) {
		if (!tipoOperacao.equals(MenuUsuario.PAGAMENTO)) {
			int value = -1;
			try {
				value = Integer.parseInt(entrada);
			} catch (Exception exception) {
				System.out.println("Por favor, coloque um valor valido.");
				value = Integer.parseInt(entrada);
			}
			return value > 0.0;
		} else  {
			return true;
		}
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
}
