package interfaceUsuario.verificadores.dados;

import interfaceUsuario.dados.DadosChavesPix;
import utilsBank.GeracaoAleatoria;

import static interfaceUsuario.menus.MenuUsuario.TECLADO;
import static interfaceUsuario.verificadores.dados.VerificadorEntrada.ENTRADAS_CHAVE_PIX;

public class VerificadorPix {
    public static boolean chavePix(String entrada, String tipoChavePix) {
        System.out.println("A CHAVE INSERIDA " + entrada + " ESTA CORRETA? [1] SIM! [0] NAO, PRECISO TROCAR");
        if (TECLADO.nextLine().equals("0")) {
            return true;
        } else {
            switch (tipoChavePix) {
                case DadosChavesPix.TELEFONE:
                    return !VerificadorClientes.verificarTelefone(entrada);
                case DadosChavesPix.EMAIL:
                    return !VerificadorClientes.verificarEmail(entrada);
                case DadosChavesPix.CHAVE_ALEATORIA:
                    return !chaveAleatoria(entrada);
                case DadosChavesPix.IDENTIFICACAO:
                    return !VerificadorClientes.verificadorIdentificacao(entrada);
            }
        }
        return true;
    }

    private static boolean chaveAleatoria(String e) {
        return e.length() == GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA;
    }

    public static boolean tipoChavePix(String entrada) {
        for (String palavra : ENTRADAS_CHAVE_PIX) {
            if (entrada.equals(palavra)) {
                return true;
            }
        }
        return false;
    }

}
