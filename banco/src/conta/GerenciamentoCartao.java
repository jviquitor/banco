package conta;

import cartao.Cartao;
import interfaceUsuario.exceptions.ValorInvalido;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GerenciamentoCartao implements Serializable {
    @Serial
    private static final long serialVersionUID = 7L;
    private final List<Cartao> listaDeCartao;
    private Double limiteUsado;
    private boolean debitoAutomatico = false;
    private int dataDebitoAutomatico;

    protected GerenciamentoCartao() {
        this.listaDeCartao = new ArrayList<>();
        this.limiteUsado = 0.0;
    }

    public int getDataDebitoAutomatico() {
        return dataDebitoAutomatico;
    }

    /**
     * Retorna o valor do Limite utilizado [FATURA]
     *
     * @return Double
     */

    public Double getFatura() {
        return limiteUsado;
    }

    /**
     * Retorna o limite maximo do Cartao
     *
     * @return Double
     */
    public Double getLimiteMaximo() throws ValorInvalido {
        if (!listaDeCartao.isEmpty()) {
            return listaDeCartao.get(0).getLimiteMaximo();
        }
        throw new ValorInvalido("Nenhum Cartao adicionado, por favor, tente novamente criando um cartao virtual.");
    }

    /**
     * Retorna o valor do limite restante para ser gasto
     *
     * @return Double
     */
    public Double getLimiteRestante() throws ValorInvalido {
        return getLimiteMaximo() - limiteUsado;
    }

    /**
     * Diminui o Limite usado com base no parametro informado
     */
    protected void aumentarLimiteAtual(Double valorPagoFatura) {
        this.limiteUsado -= valorPagoFatura;
    }

    /**
     * Aumenta o limite usado com base no parametor informado
     */

    public void diminuirLimiteAtual(Double valorGasto) {
        this.limiteUsado += valorGasto;
    }

    /**
     * Recebe um Cartao e verifica se o Cartao ja foi inserido na lista do cartão, caso nao, adiciona
     */

    protected void adicionarNovoCartao(Cartao cartao) {
        if (!listaDeCartao.contains(cartao)) {
            listaDeCartao.add(cartao);
        }
    }

    protected List<Cartao> getListaDeCartao() {
        return listaDeCartao;
    }


    public boolean isDebitoAutomatico() {
        return this.debitoAutomatico && this.dataDebitoAutomatico > 0;
    }

    public void setDebitoAutomatico(boolean debitoAutomatico, int dataDebitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
        this.dataDebitoAutomatico = dataDebitoAutomatico;
    }


}
