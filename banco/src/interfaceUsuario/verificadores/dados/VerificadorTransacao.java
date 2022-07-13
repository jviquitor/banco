package interfaceUsuario.verificadores.dados;

import conta.ContaDiamond;
import conta.ContaPremium;
import conta.ContaStandard;
import conta.GerenciamentoCartao;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.MenuUsuarioConstantes;
import interfaceUsuario.exceptions.ValorInvalido;

import static interfaceUsuario.menus.MenuUsuario.DEPOSITO;
import static interfaceUsuario.menus.MenuUsuario.TRANSFERENCIA;
import static interfaceUsuario.verificadores.dados.VerificadorEntrada.*;

public class VerificadorTransacao {

    public static boolean dadosTransacao(String entrada, String tipoOperacao, String tipoConta) throws ValorInvalido {
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

    public static boolean valorFatura(String s, MenuUsuarioConstantes tipoOperacao, GerenciamentoCartao carteira) throws ValorInvalido {
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

    public static boolean agendamentoTransacao(String[] s, String tipoConta) throws ValorInvalido {
        if (dadosTransacao(s[0], TRANSFERENCIA, tipoConta)) {
            return VerificadorData.verificarData(s[1]);
        }
        return false;
    }

    public static boolean verificarBoleto(String[] entrada) throws ValorInvalido {
        if (verificarEntradaValorPositivo(entrada[0]) || verificarEntradaValorPositivo(entrada[2])) {
            return false;
        }
        if (!VerificadorData.verificarData(entrada[1])) {
            return false;
        }
        try {
            Integer.parseInt(entrada[2]);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

}
