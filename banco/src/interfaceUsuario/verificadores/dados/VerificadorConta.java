package interfaceUsuario.verificadores.dados;

import conta.exceptions.TipoInvalido;
import interfaceUsuario.exceptions.ValorInvalido;

import static interfaceUsuario.verificadores.dados.VerificadorEntrada.*;

public class VerificadorConta {

    public static void verificarRenda(Double renda) throws ValorInvalido {
        if (renda < 0.0) {
            throw new ValorInvalido("[ERRO] Valor negativo para sua renda");
        } else if (renda < RENDA_MINIMA) {
            throw new ValorInvalido("[RENDA MINIMA NAO PERMITIDA] As regras da Agencia nao permite essa renda, por favor, consulte nossos termos de uso!");
        }
    }

    public static String tipoDeContaPelaRenda(Double renda) {
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
