package utilsBank;

import utilsBank.arquivo.GerenciadorArquivo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeracaoAleatoria {
	private static final int NUMERO_CARTAO_SEM_AGENCIA = 12;
	private static final Set<String> CHAVES_NOSSO_NUMEROS = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_NOSSO_NUMEROS);
	private static final Set<String> CHAVES_GERADAS_ALEATORIA = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.CHAVES_GERADAS_ALEATORIA);
	private static final Set<String> CHAVES_GERADAS_NUMERO_CARTAO = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.CHAVES_GERADAS_NUMERO_CARTAO);
	private static final Set<String> CHAVES_ID_CONTA = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.CHAVES_ID_CONTA);

	public static String gerarNossosNumeros(int quantidadeNumeros) {
		Random random = new Random();
		StringBuilder numberRandom;
		do {
			numberRandom = new StringBuilder();
			for (int i = 0; i < quantidadeNumeros; i++) {
				numberRandom.append(random.nextInt(10));
			}

		} while (CHAVES_NOSSO_NUMEROS.contains(String.valueOf(numberRandom)));

		CHAVES_NOSSO_NUMEROS.add(String.valueOf(numberRandom));
		return numberRandom.toString();
	}

	public static String gerarNumeros(int quantidade) {
		Random random = new Random();
		StringBuilder numberRandom;

		numberRandom = new StringBuilder();
		for (int i = 0; i < NUMERO_CARTAO_SEM_AGENCIA; i++) {
			numberRandom.append(random.nextInt(10));
		}

		return String.valueOf(numberRandom);
	}


	public static String gerarNumeroCartao() {
		Random random = new Random();
		StringBuilder numberRandom;
		do {
			numberRandom = new StringBuilder();
			for (int i = 0; i < NUMERO_CARTAO_SEM_AGENCIA; i++) {
				numberRandom.append(random.nextInt(10));
			}

		} while (CHAVES_GERADAS_NUMERO_CARTAO.contains(String.valueOf(numberRandom)));

		CHAVES_GERADAS_NUMERO_CARTAO.add((String.valueOf(numberRandom)));
		return String.valueOf(numberRandom);
	}

	public static String gerarChaveAleatoria(int tamanhoChave) {
		if (CHAVES_GERADAS_ALEATORIA.size() < Math.pow(36, tamanhoChave)) {
			Random aleatorio = new Random();
			String caracteres = "abcdefghijklmnopqrstuvwxyz1234567890";
			String chaveAleatoria;
			do {
				chaveAleatoria = "";
				for (int i = 0; i < 1; i++) {
					chaveAleatoria += caracteres.charAt(aleatorio.nextInt(36));
				}
			} while (!CHAVES_GERADAS_ALEATORIA.add(chaveAleatoria));
			return chaveAleatoria;
		} else {
			throw new RuntimeException("Tamanho maximo de chaves atingido");
		}

	}

	public static String gerarIdConta(int quantidadeDeNumeros) {
		Random random = new Random();
		StringBuilder numberRandom;
		do {
			numberRandom = new StringBuilder();
			for (int i = 0; i < quantidadeDeNumeros; i++) {
				numberRandom.append(random.nextInt(10));
			}

		} while (CHAVES_ID_CONTA.contains(String.valueOf(numberRandom)));


		CHAVES_ID_CONTA.add(String.valueOf(numberRandom));
		return numberRandom.toString();
	}

}
