package conta;

import cartao.Cartao;
import utilsBank.databank.Data;

import java.util.ArrayList;
import java.util.List;

public class GerenciamentoCartao {
    //TODO Parcelamento com juros dependendo do tipo do cartoa
    //TODO Verifica Limite
    private List<Cartao> listaDeCartao;
    private Double limiteMaximo;
    private Double limiteUsado;
    private boolean debitoAutomatico = false;
    private Data dataDebitoAutomatico;

    //TODO construtor singleton
    protected GerenciamentoCartao() {
        this.listaDeCartao = new ArrayList<>();
    }

    public Data getDataDebitoAutomatico() {
        return dataDebitoAutomatico;
    }

    /**
     * Metodo apenas utilizada para quando o cliente pagar a fatura e ter o seu limite atual atualizado conforme pagamento
     */
    protected void aumentarLimiteAtual(Double valorPagoFatura) {
        this.limiteUsado -= valorPagoFatura;
    }

    protected void diminuirLimiteAtual(Double valorGasto) {
        this.limiteUsado += valorGasto;
    }


    public boolean adicionarNovoCartao(Cartao cartao) {
        return listaDeCartao.add(cartao);
    }

    public List<Cartao> getListaDeCartao() {
        return listaDeCartao;
    }


    public void setLimiteMaximo(Double limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
    }

    public Double getLimiteUsado() {
        return limiteUsado;
    }

    public void setLimiteUsado(Double limiteUsado) {
        this.limiteUsado = limiteUsado;
    }

    protected boolean isDebitoAutomatico() {
        return this.debitoAutomatico;
    }

    public void setDebitoAutomatico(boolean debitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
    }

    //TODO A INterface precisa ter um local que mostra a gerencia do cartao pra ativar ou nao o debito automatico
    public void setDebitoAutomatico(boolean debitoAutomatico, Data dataDebitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
        this.dataDebitoAutomatico = dataDebitoAutomatico;
    }


}
