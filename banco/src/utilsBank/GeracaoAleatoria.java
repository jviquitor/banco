package utilsBank;

import utilsBank.arquivo.GerenciadorArquivo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeracaoAleatoria {
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
        String caracteres = "abcdefghijklmnopqrstuvwxyz1234567890";
        if (CHAVES_GERADAS_ALEATORIA.size() < Math.pow(caracteres.length(), tamanhoChave)) {
            Random aleatorio = new Random();
            String chaveAleatoria;
            do {
                chaveAleatoria = "";
                for (int i = 0; i < tamanhoChave; i++) {
                    chaveAleatoria += caracteres.charAt(aleatorio.nextInt(caracteres.length()));
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

    public static void salvarNossosNumeros() {
        GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_NOSSO_NUMEROS, (HashSet<String>) CHAVES_NOSSO_NUMEROS);
    }

    public static void salvarNumerosCartoes() {
        GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_GERADAS_NUMERO_CARTAO, (HashSet<String>) CHAVES_GERADAS_NUMERO_CARTAO);
    }

    public static void salvarChavesAleatorias() {
        GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_GERADAS_ALEATORIA, (HashSet<String>) CHAVES_GERADAS_ALEATORIA);
    }

    public static void salvarIdsContas() {
        GerenciadorGeracaoAleatoria.salvandoGeracaoAleatoria(GerenciadorArquivo.PATH_CHAVES_ID_CONTA, (HashSet<String>) CHAVES_ID_CONTA);
    }
}
