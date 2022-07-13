package interfaceUsuario.verificadores.dados;

import interfaceUsuario.exceptions.ValorInvalido;

import static interfaceUsuario.verificadores.dados.VerificadorEntrada.DIA_MAX_DEB_AUTO;
import static interfaceUsuario.verificadores.dados.VerificadorEntrada.DIA_MINIMO_DEB_AUTO;

public class VerificadorData {
    protected static boolean verificarData(String d) throws ValorInvalido {
        if (d.contains("/")) {
            String[] d_data = d.split("/");
            for (String d_datum : d_data) {
                try {
                    if (Integer.parseInt(d_datum) <= 0) {
                        throw new ValorInvalido("[DATA INVALIDA]");
                    }
                } catch (NumberFormatException ex) {
                    throw new ValorInvalido("[DATA INVALIDA]");
                }
            }
            return Integer.parseInt(d_data[1]) <= 12 && Integer.parseInt(d_data[2]) >= 2000 && Integer.parseInt(d_data[0]) <= 31;
        }
        throw new ValorInvalido("[DATA INVALIDA]");
    }

    public static boolean verificarDataDebitoAuto(String entrada) {
        try {
            int datavalue = Integer.parseInt(entrada);
            return datavalue >= DIA_MINIMO_DEB_AUTO && datavalue <= DIA_MAX_DEB_AUTO;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
