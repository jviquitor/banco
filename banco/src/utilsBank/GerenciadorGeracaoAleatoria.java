package utilsBank;

import cliente.Cliente;
import utilsBank.arquivo.Exception.LeituraArquivoException;
import utilsBank.arquivo.GerenciadorArquivo;

import java.util.HashSet;
import java.util.Set;

public class GerenciadorGeracaoAleatoria {

    public static HashSet<String> inicializarGeracaoAleatoria(String path) {
        try {
            return GerenciadorArquivo.listarSetGeracaoAleatoria(path);
        } catch (LeituraArquivoException ex) {
            return new HashSet<>();
        }
    }
     public static void salvandoNossosNumeros(String path, HashSet<String> dados) {
        GerenciadorArquivo.inserirSetGeracao(path, dados);
    }
}
