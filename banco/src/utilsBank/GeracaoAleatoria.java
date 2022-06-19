package utilsBank;

// TODO: 6/5/2022 Fazer metódos aleatorios para ajudar na geração das coisas

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeracaoAleatoria {
	private static final int NUMERO_CARTAO_SEM_AGENCIA = 12;
	private static final Set<String> chavesNossosNumerosGeradas = new HashSet<>();
	private static final Set<String> chavesGeradasAleatorias = new HashSet<>();
	private static final Set<Integer> chavesGeradasNumeroCartao = new HashSet<>();
	private static final Set<String> chavesIdsConta = new HashSet<>();

	public static String gerarNossosNumeros(int quantidadeNumeros) {
		Random random = new Random();
		StringBuilder numberRandom;
		do {
			numberRandom = new StringBuilder();
			for (int i = 0; i < quantidadeNumeros; i++) {
				numberRandom.append(random.nextInt(10));
			}

		} while (chavesNossosNumerosGeradas.contains(numberRandom));

		chavesNossosNumerosGeradas.add(String.valueOf(numberRandom));
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

		} while (chavesGeradasNumeroCartao.contains(Integer.parseInt(String.valueOf(numberRandom))));

		chavesGeradasNumeroCartao.add(Integer.parseInt(String.valueOf(numberRandom)));
		return String.valueOf(numberRandom);
	}

	public static String gerarChaveAleatoria(int tamanhoChave) {
		if (chavesGeradasAleatorias.size() < Math.pow(36, tamanhoChave)) {
			Random aleatorio = new Random();
			String caracteres = "abcdefghijklmnopqrstuvwxyz1234567890";
			String chaveAleatoria;
			do {
				chaveAleatoria = "";
				for (int i = 0; i < 1; i++) {
					chaveAleatoria += caracteres.charAt(aleatorio.nextInt(36));
				}
			} while (!chavesGeradasAleatorias.add(chaveAleatoria));
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

		} while (chavesIdsConta.contains(numberRandom));

		chavesIdsConta.add(String.valueOf(numberRandom));
		return numberRandom.toString();
	}

	public static void main(String[] args) {
		int valor = 1000;
		for (int i = 0; i < valor; i++) {
			String valorAleatorio = GeracaoAleatoria.gerarNossosNumeros(2);
		}
		System.out.println(chavesNossosNumerosGeradas);
	}
}
