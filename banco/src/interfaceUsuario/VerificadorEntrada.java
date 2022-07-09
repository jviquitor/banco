package interfaceUsuario;

import cliente.exceptions.TiposClientes;
import conta.ContaDiamond;
import conta.ContaPremium;
import conta.ContaStandard;
import conta.GerenciamentoCartao;
import conta.exceptions.TipoInvalido;
import interfaceUsuario.dados.DadosChavesPix;
import interfaceUsuario.exceptions.ValorInvalido;
import utilsBank.GeracaoAleatoria;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static interfaceUsuario.MenuUsuario.*;

public class VerificadorEntrada {
    public static final String STANDARD = "standard";
    public static final String PREMIUM = "premium";
    public static final String DIAMOND = "diamond";
    public static final String IDENTIFICACAO = "identificacao";
    public static final int MAX_CARACTERES_ENTRADA = 60;
    public static final int DIA_MINIMO_DEB_AUTO = 1;
    public static final int DIA_MAX_DEB_AUTO = 10;
    protected static final double RENDA_MINIMA = 200.0;
    protected static final double RENDA_MAXIMA_STANDARD = 10000.0;
    protected static final double RENDA_MAXIMA_PREMIUM = 30000.0;
    protected static final String[] ENTRADAS_CHAVE_PIX = {"chave_aleatoria", "telefone", "email"};
    protected static final int DIGITOS_MAXIMO_TELEFONE = 12;
    protected static final int QUANTIDADE_IDENTIFICACAO_VALIDA = 14;
    private static final int APELIDO_MAX_CARACTERES = 30;
    private static final Pattern NAO_APENAS_ALFABETO = Pattern.compile("[^a-zA-Z\s]");
    private static final Pattern NAO_APENAS_ALFABETO_DIGITO = Pattern.compile("[^a-zA-Z0-9\s]");
    private static final Pattern HAS_WHITESPACE = Pattern.compile("\s");
    private static final Pattern QUALQUER_NAO_DIGITO = Pattern.compile("/\\D+/");
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_VERIFICADOR = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final int TAMANHO_MINIMO_SENHA = 3;
    private static final int TAMANHO_CPF = 11;
    private static final int TAMANHO_CNPJ = 14;
    private static final int TAMANHO_CEP = 8;
    private static final Scanner TECLADO = new Scanner(System.in);

    protected static boolean verificarEntradasZeroUm(String entrada) {
        return !entrada.equals("0") && !entrada.equals("1");
    }

    protected static boolean verificarDadosTransacao(String entrada, String tipoOperacao, String tipoConta) throws ValorInvalido {
        int value;
        try {
            value = Integer.parseInt(entrada);
        } catch (Exception exception) {
            System.out.println("Por favor, coloque um valor valido.");
            value = Integer.parseInt(entrada);
        }
        if (tipoOperacao.equals(TRANSFERENCIA)) {
            return verificarEntradaValor(entrada, tipoOperacao);
        } else if (tipoOperacao.equals(DEPOSITO)) {
            if (value > 0.0) {
                double valorDepositosOcorridos = InterfaceUsuario.getClienteAtual().getConta().getSaldoTotalDepositado();
                switch (tipoConta) {
                    case STANDARD:
                        if (value <= ContaStandard.DEPOSITO_MAXIMO && valorDepositosOcorridos + value <= ContaStandard.DEPOSITO_MAXIMO) {
                            return true;
                        }
                        throw new ValorInvalido("[ERRO] O seu tipo de conta nao permite transacoes maiores do que " + ContaStandard.DEPOSITO_MAXIMO + " reais\nPor favor, insira um valor valido!");
                    case PREMIUM:
                        if (value <= ContaPremium.DEPOSITO_MAXIMO && valorDepositosOcorridos + value <= ContaPremium.DEPOSITO_MAXIMO) {
                            return true;
                        }
                        throw new ValorInvalido("[ERRO] O seu tipo de conta nao permite transacoes maiores do que " + ContaPremium.DEPOSITO_MAXIMO + " reais\nPor favor, insira um valor valido!");
                    case DIAMOND:
                        if (value <= ContaDiamond.DEPOSITO_MAXIMO && valorDepositosOcorridos + value <= ContaDiamond.DEPOSITO_MAXIMO) {
                            return true;
                        }
                        throw new ValorInvalido("[ERRO] Nossa agencia nao permite depositos maiores do que " + ContaDiamond.DEPOSITO_MAXIMO + " reais\nPor favor, insira um valor valido!");
                }
            }
        }
        return false;
    }

    protected static boolean verificarEntradaTipoChavePix(String entrada) {
        for (String palavra : ENTRADAS_CHAVE_PIX) {
            if (entrada.equals(palavra)) {
                return true;
            }
        }
        return false;
    }

    private static boolean verificarTelefone(String e) {
        return e.length() <= DIGITOS_MAXIMO_TELEFONE;
    }

    private static boolean verificarEmail(String e) {
        Matcher matcher = EMAIL_VERIFICADOR.matcher(e);
        return matcher.matches();
    }

    public static boolean verificarIdade(String idade, TiposClientes tiposClientes) {
        if (tiposClientes == TiposClientes.CLIENTE_EMPRESA) {
            return Integer.parseInt(idade) >= 3;
        }
        return Integer.parseInt(idade) >= 18;
    }

    public static boolean verificarTipo(String tipo) {
        return tipo.equals("0") || tipo.equals("1") || tipo.equals("2");
    }

    private static boolean verificadorIdentificacao(String e) {
        try {
            Integer.parseInt(e);
        } catch (NumberFormatException ex) {
            return true;
        }
        return false;
    }

    private static boolean verificadorChaveAleatoria(String e) {
        return e.length() == GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA;
    }

    protected static boolean verificarChavePix(String entrada, String tipoChavePix) {
        System.out.println("A CHAVE INSERIDA " + entrada + " ESTA CORRETA? [1] SIM! [0] NAO, PRECISO TROCAR");
        if (TECLADO.nextLine().equals("0")) {
            return false;
        } else {
            switch (tipoChavePix) {
                case DadosChavesPix.TELEFONE:
                    return verificarTelefone(entrada);
                case DadosChavesPix.EMAIL:
                    return verificarEmail(entrada);
                case DadosChavesPix.CHAVE_ALEATORIA:
                    return verificadorChaveAleatoria(entrada);
                case DadosChavesPix.IDENTIFICACAO:
                    return verificadorIdentificacao(entrada);
            }
        }
        return false;
    }

    protected static void verificarRenda(Double renda) throws ValorInvalido {
        if (renda < 0.0) {
            throw new ValorInvalido("[ERRO] Valor negativo para sua renda");
        } else if (renda < RENDA_MINIMA) {
            throw new ValorInvalido("[RENDA MINIMA NAO PERMITIDA] As regras da Agencia nao permite essa renda, por favor, consulte nossos termos de uso!");
        }
    }

    protected static String tipoDeContaPelaRenda(Double renda) {
        if (renda <= RENDA_MAXIMA_STANDARD) {
            return STANDARD;
        } else if (renda <= RENDA_MAXIMA_PREMIUM) {
            return PREMIUM;
        } else if (renda > RENDA_MAXIMA_PREMIUM) {
            return DIAMOND;
        }
        throw new TipoInvalido("O tipo de conta nao pode ser definido");
    }

    protected static boolean verificarEntradaValorParaFatura(String s, MenuUsuarioConstantes verificarValorSaldo) throws ValorInvalido {
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

    protected static boolean verificarEntradaValor(String s, String tipoOperacao) throws ValorInvalido {
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

    protected static boolean verificarEntradaValorFatura(String s, MenuUsuarioConstantes tipoOperacao, GerenciamentoCartao carteira) throws ValorInvalido {
        if (tipoOperacao == MenuUsuarioConstantes.PAGAR_FATURA) {
            if (verificarEntradaValorParaFatura(s, MenuUsuarioConstantes.VERIFICAR_VALOR_SALDO)) {
                double valor = Double.parseDouble(s);
                if (valor > carteira.getFatura()) {
                    throw new ValorInvalido("[ERRO] Valor de pagamento maior que o valor da fatura");
                }
            }
        }
        if (tipoOperacao == MenuUsuarioConstantes.AUMENTAR_FATURA) {
            if (verificarEntradaValorParaFatura(s, MenuUsuarioConstantes.NAO_VERIFICAR_VALOR_SALDO)) {
                double valor = Double.parseDouble(s);
                if (valor > carteira.getLimiteRestante()) {
                    throw new ValorInvalido("[ERRO] Valor inserido maior que o seu limite");
                }
            }
            return false;
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

    protected static boolean verificarIdentidadeGerente(String s) {
        if (s.length() <= QUANTIDADE_IDENTIFICACAO_VALIDA) {
            return verificadorIdentificacao(s);
        }
        return false;
    }

    public static boolean verificarData(String d) throws ValorInvalido {
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

    protected static boolean verificarDadosAgendamentoTransacao(String[] s, String tipoConta) throws ValorInvalido {
        if (verificarDadosTransacao(s[0], TRANSFERENCIA, tipoConta)) {
            return verificarData(s[1]);
        }
        return false;
    }

    protected static boolean verificarEndereco(String[] entradaEndereco) {
        try {
            Integer.parseInt(entradaEndereco[0]);
            Integer.parseInt(entradaEndereco[1]);
            if (!(entradaEndereco[0].length() == TAMANHO_CEP)) {
                throw new ValorInvalido("CEP INVALIDO");
            }
            return true;
        } catch (Exception ignored) {

        }
        return false;
    }

    private static boolean isAlphabetic(String e) {
        return !NAO_APENAS_ALFABETO.matcher(e).find();
    }

    private static boolean isAlphanumeric(String e) {
        return !NAO_APENAS_ALFABETO_DIGITO.matcher(e).find();
    }

    protected static boolean verificarInformacoesCliente(String[] entradaGeral, TiposClientes tiposClientes) throws ValorInvalido {
        for (String s : entradaGeral) {
            if (s.isBlank()) {
                throw new ValorInvalido("Nenhum dos campos podem ser vazios. Tente novamente");
            }
            if (s.length() > MAX_CARACTERES_ENTRADA) {
                throw new ValorInvalido("Numero de caracteres excedido");
            }
        }

        if (!isAlphabetic(entradaGeral[0])) {
            throw new ValorInvalido("Por favor, o nome nao deve conter numeros ou caracteres invalidos. Tente novamente");
        }

        if (!verificarEmail(entradaGeral[1])) {
            throw new ValorInvalido("Por favor, Insira um email valido");
        }

        if ((!verificarTelefone(entradaGeral[2]))) {
            throw new ValorInvalido("Telefone Invalido. Tente novamente");
        }

        if (!verificarIdade(entradaGeral[3], tiposClientes)) {
            throw new ValorInvalido("A idade foi inserida incorretamente\n[Voce deve ser maior de 18 anos caso for Pessoa Fisica e Ter pelo menos 3 anos de Empresa," +
                    " caso for Pessoa Juridica]. Tente novamente");
        }

        if (!verificarIdentificacao(entradaGeral[4], tiposClientes)) {
            String tag = (tiposClientes == TiposClientes.CLIENTE_PESSOA) ? "CPF" : "CNPJ";
            throw new ValorInvalido(tag + " invalida, tente novamente");
        }

        if (!verificarSenha(entradaGeral[5])) {
            throw new ValorInvalido("A senha deve conter pelo menos 3 digitos e nao conter espacos.");
        }
        return true;
    }

    private static boolean verificarSenha(String s) {
        if (HAS_WHITESPACE.matcher(s).find()) {
            return false;
        }
        return s.length() >= TAMANHO_MINIMO_SENHA;
    }

    private static boolean verificarIdentificacao(String s, TiposClientes tiposClientes) {

        if (tiposClientes == TiposClientes.CLIENTE_PESSOA) {
            if (s.length() != TAMANHO_CPF) {
                return false;
            }
            return !QUALQUER_NAO_DIGITO.matcher(s).find();

        } else if (tiposClientes == TiposClientes.CLIENTE_EMPRESA) {
            if (s.length() != TAMANHO_CNPJ) {
                return false;
            }
            return !QUALQUER_NAO_DIGITO.matcher(s).find();
        }
        return false;
    }

    protected static boolean verificarBoleto(String[] entrada) throws ValorInvalido {
        if (verificarEntradaValorPositivo(entrada[0]) || verificarEntradaValorPositivo(entrada[2])) {
            return false;
        }
        if (!verificarData(entrada[1])) {
            return false;
        }
        try {
            Integer.parseInt(entrada[2]);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    protected static boolean verificarDataDebitoAuto(String entrada) {
        try {
            int datavalue = Integer.parseInt(entrada);
            return datavalue >= DIA_MINIMO_DEB_AUTO && datavalue <= DIA_MAX_DEB_AUTO;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    protected static boolean verificarApelido(String[] entradas) throws ValorInvalido {
        if (entradas[0].length() > APELIDO_MAX_CARACTERES) {
            throw new ValorInvalido("O apelido excede a quantidade permitida de caracteres");
        }

        if (!isAlphanumeric(entradas[0])) {
            throw new ValorInvalido("Por favor, o apelido deve conter apenas numeros e letras");
        }
        return true;
    }

}
