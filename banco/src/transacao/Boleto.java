package transacao;

import agencia.Agencia;
import cliente.Cliente;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.DadosBoleto;
import interfaceUsuario.dados.DadosTransacao;
import transacao.exceptions.TransacaoException;
import utilsBank.databank.Data;

import java.io.Serial;
import java.io.Serializable;

public class Boleto extends Transacao implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private static final String NOME_TRANSACAO = "Boleto";
    private final Data DATA_VENCIMENTO;
    private final Double MULTA_DIAS;
    private Boolean foiPago;

    public Boleto(DadosTransacao dadosTransacao, DadosBoleto dadosBoleto) {
        super(dadosTransacao);
        this.foiPago = dadosBoleto.getFoiPago();
        this.DATA_VENCIMENTO = dadosBoleto.getDataVencimento();
        this.MULTA_DIAS = Double.valueOf(dadosBoleto.getMultaPorDias());
    }

    public void pagar(Cliente origem) throws TransacaoException {
        if (Boolean.TRUE.equals(foiPago)) {
            throw new TransacaoException("Esse boleto ja foi pago");
        }
        this.origem = origem;
        this.foiPago = true;
    }

    public static Boleto criarBoleto() {
        DadosBoleto dadosBoleto = InterfaceUsuario.getDadosBoleto();
        DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();

        Boleto boleto = new Boleto(dadosTransacao, dadosBoleto);
        Agencia.getInstance().addBoleto(boleto);

        return boleto;
    }

    public Data getDataVencimento() {
        return DATA_VENCIMENTO;
    }

    public double getMultaPorDias() {
        return MULTA_DIAS;
    }

    @Override
    public String toString() {
        String toString = "[" + NOME_TRANSACAO + "]\n";
        if (VALOR != null) {
            toString = toString + "VALOR: " + VALOR + "\n";
        }
        if (NOSSO_NUMERO != null) {
            toString = toString + "NUMERO DO BOLETO: " + NOSSO_NUMERO + "\n";
        }
        if (origem != null) {
            toString = toString + "ORIGEM DO BOLETO:  " + origem + "\n";
        }
        if (getDataAgendada() == null && dataEmissaoTransacao != null) {
            toString = toString + "DATA EMISSAO DO BOLETO: " + dataEmissaoTransacao + "\n";
        }
        if (DATA_VENCIMENTO != null) {
            toString = toString + "VENCIMENTO: " + DATA_VENCIMENTO + "\n";
        }
        if (MULTA_DIAS != 0) {
            toString = toString + "MULTA POR DIAS: " + MULTA_DIAS + "\n";
        }
        if (foiPago != null) {
            if (foiPago) {
                toString = toString + "BOLETO PAGO!\n";
                if (ID_PAGAMENTO != null) {
                    toString = toString + "IDENTIFICACAO DE PAGAMENTO: " + ID_PAGAMENTO + "\n";
                }
            } else {
                toString = toString + "BOLETO NAO PAGO!\n";
            }

        }
        return toString;
    }


}
