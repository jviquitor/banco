package interfaceUsuario.verificadores.dados;

import conta.exceptions.TipoInvalido;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.MenuUsuarioConstantes;
import interfaceUsuario.exceptions.ValorInvalido;

import java.util.regex.Pattern;

import static interfaceUsuario.menus.MenuUsuario.GUARDAR;
import static interfaceUsuario.menus.MenuUsuario.RESGATAR;
import static interfaceUsuario.verificadores.dados.VerificadorClientes.isAlphanumeric;

public class VerificadorEntrada {
    public static final String STANDARD = "standard";
    public static final String PREMIUM = "premium";
    public static final String DIAMOND = "diamond";
    public static final String IDENTIFICACAO = "identificacao";
    public static final int MAX_CARACTERES_ENTRADA = 60;
    public static final int DIA_MINIMO_DEB_AUTO = 1;
    public static final int DIA_MAX_DEB_AUTO = 10;
    public static final double RENDA_MINIMA = 200.0;
    public static final double RENDA_MAXIMA_STANDARD = 10000.0;
    public static final double RENDA_MAXIMA_PREMIUM = 30000.0;
    protected static final String[] ENTRADAS_CHAVE_PIX = {"chave_aleatoria", "telefone", "email"};
    protected static final int DIGITOS_MAXIMO_TELEFONE = 12;
    protected static final int QUANTIDADE_IDENTIFICACAO_VALIDA = 14;
    private static final int APELIDO_MAX_CARACTERES = 30;
    static final Pattern NAO_APENAS_ALFABETO = Pattern.compile("[^a-zA-Z\s]");
    static final Pattern NAO_APENAS_ALFABETO_DIGITO = Pattern.compile("[^a-zA-Z\\d\s]");
    static final Pattern HAS_WHITESPACE = Pattern.compile("\s");
    static final Pattern QUALQUER_NAO_DIGITO = Pattern.compile("/\\D+/");
    static final int TAMANHO_MINIMO_SENHA = 3;
    static final int TAMANHO_CPF = 11;
    static final int TAMANHO_CNPJ = 14;
    static final int TAMANHO_CEP = 8;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z\\d-+]+(\\.[_A-Za-z\\d-]+)*@"
                    + "[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$";
    static final Pattern EMAIL_VERIFICADOR = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean verificarEntradasZeroUm(String entrada) {
        return !entrada.equals("0") && !entrada.equals("1");
    }

    public static boolean verificarEntradaTipo(String tipo) {
        return tipo.equals("0") || tipo.equals("1") || tipo.equals("2");
    }

    public static boolean verificarEntradaValorParaFatura(String s, MenuUsuarioConstantes verificarValorSaldo) throws ValorInvalido {
        double valor;
        try {
            valor = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            throw new TipoInvalido("[ERRO: VALOR INSERIDO INCORRETAMENTE] O valor de entrada deve ser maior que 0");
        }
        if (valor < 0.0) {
            throw new ValorInvalido("[ERRO] Valor negativo para operacao");
        }
        if (verificarValorSaldo == MenuUsuarioConstantes.VERIFICAR_VALOR_SALDO) {
            if (valor >= InterfaceUsuario.getClienteAtual().getConta().getSaldo()) {
                throw new ValorInvalido("[ERRO] Nao ha saldo suficiente para realizar essa operacao!");
            }
        }
        return true;
    }

    public static boolean verificarEntradaValor(String s, String tipoOperacao) throws ValorInvalido {
        double valor;
        try {
            valor = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            throw new TipoInvalido("[ERRO: VALOR INSERIDO INCORRETAMENTE] O valor de entrada deve ser maior que 0");
        }
        if (valor < 0.0) {
            throw new ValorInvalido("[ERRO] Valor negativo para operacao");
        }
        if (tipoOperacao.equals(GUARDAR)) {
            if (valor >= InterfaceUsuario.getClienteAtual().getConta().getSaldo()) {
                throw new ValorInvalido("[ERRO] Nao ha saldo suficiente para realizar essa operacao!");
            }
        } else if (tipoOperacao.equals(RESGATAR)) {
            if (valor >= InterfaceUsuario.getClienteAtual().getConta().getDinheiroGuardado()) {
                throw new ValorInvalido("[ERRO] Nao ha saldo guardado suficiente para realizar essa operacao!");
            }
        }
        return true;
    }

    protected static boolean verificarEntradaValorPositivo(String s) throws ValorInvalido {
        double valor;
        try {
            valor = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            throw new TipoInvalido("[ERRO: VALOR INSERIDO INCORRETAMENTE] O valor de entrada deve ser maior que 0");
        }
        if (valor < 0.0) {
            throw new ValorInvalido("[ERRO] Valor negativo para operacao");
        }
        return false;
    }

    public static boolean verificarApelidoCartao(String[] entradas) throws ValorInvalido {
        if (entradas[0].length() > APELIDO_MAX_CARACTERES) {
            throw new ValorInvalido("O apelido excede a quantidade permitida de caracteres");
        }

        if (!isAlphanumeric(entradas[0])) {
            throw new ValorInvalido("Por favor, o apelido deve conter apenas numeros e letras");
        }
        return true;
    }

}
