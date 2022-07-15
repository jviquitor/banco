package utilsBank;

import utilsBank.arquivo.GerenciadorArquivo;
import utilsBank.arquivo.exception.LeituraArquivoException;

import java.util.HashSet;

public class GerenciadorGeracaoAleatoria {

    public static HashSet<String> inicializarGeracaoAleatoria(String path) {
        try {
            return GerenciadorArquivo.listarSetGeracaoAleatoria(path);
        } catch (LeituraArquivoException ex) {
            throw new RuntimeException("Erro de carregamento. O banco nao foi iniciado.");
        }
    }

    public static void salvandoGeracaoAleatoria(String path, HashSet<String> dados) {
        GerenciadorArquivo.inserirSetGeracao(path, dados);
    }
}
