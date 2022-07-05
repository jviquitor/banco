package utilsBank;

import utilsBank.arquivo.GerenciadorArquivo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeracaoAleatoria {
	//TODO SALVAR TODAS AS GERACOES ALEATORIA NO SALVAMENTO DO ARQUIVO DA AGENCIA ATUALIZACAO
	public static final int TAMANHO_ID_CONTA = 4;
	public static final int TAMANHO_CHAVE_ALEATORIA = 48;
	private static final int NUMERO_CARTAO_SEM_AGENCIA = 12;
	private static final Set<String> CHAVES_NOSSO_NUMEROS = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_NOSSO_NUMEROS);
	private static final Set<String> CHAVES_GERADAS_ALEATORIA = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_GERADAS_ALEATORIA);
	private static final Set<String> CHAVES_GERADAS_NUMERO_CARTAO = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_GERADAS_NUMERO_CARTAO);
	private static final Set<String> CHAVES_ID_CONTA = GerenciadorGeracaoAleatoria.inicializarGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_ID_CONTA);

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
		GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_NOSSO_NUMEROS, (HashSet<String>) CHAVES_NOSSO_NUMEROS);
		return numberRandom.toString();
	}

	public static String gerarNumeros(int quantidade) {
		Random random = new Random();
		StringBuilder numberRandom;

		numberRandom = new StringBuilder();
		for (int i = 0; i < quantidade; i++) {
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
		GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_GERADAS_NUMERO_CARTAO, (HashSet<String>) CHAVES_GERADAS_NUMERO_CARTAO);
		return String.valueOf(numberRandom);
	}

	public static String gerarChaveAleatoria(int tamanhoChave) {
		if (CHAVES_GERADAS_ALEATORIA.size() < Math.pow(36, tamanhoChave)) {
			Random aleatorio = new Random();
			String caracteres = "abcdefghijklmnopqrstuvwxyz1234567890";
			String chaveAleatoria;
			do {
				chaveAleatoria = "";
				for (int i = 0; i < tamanhoChave; i++) {
					chaveAleatoria += caracteres.charAt(aleatorio.nextInt(36));
				}
			} while (!CHAVES_GERADAS_ALEATORIA.add(chaveAleatoria));
			GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_GERADAS_ALEATORIA, (HashSet<String>) CHAVES_GERADAS_ALEATORIA);
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
		GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_ID_CONTA, (HashSet<String>) CHAVES_ID_CONTA);
		return numberRandom.toString();
	}

}
