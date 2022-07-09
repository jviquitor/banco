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
    private final List<Cartao> LISTA_DE_CARTOES;
    private Double limiteUsado;
    private boolean debitoAutomatico = false;
    private int dataDebitoAutomatico;

    protected GerenciamentoCartao() {
        this.LISTA_DE_CARTOES = new ArrayList<>();
        this.limiteUsado = 0.0;
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
        if (!LISTA_DE_CARTOES.contains(cartao)) {
            LISTA_DE_CARTOES.add(cartao);
        }
    }

    public void setDebitoAutomatico(boolean debitoAutomatico, int dataDebitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
        this.dataDebitoAutomatico = dataDebitoAutomatico;
    }

    /**
     * Retorna o dia que o usuário colocou de debito automático
     *
     * @return dataDebitoAutomaticoç
     */
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
        if (!LISTA_DE_CARTOES.isEmpty()) {
            return LISTA_DE_CARTOES.get(0).getLimiteMaximo();
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

    protected List<Cartao> getListaDeCartoes() {
        return LISTA_DE_CARTOES;
    }

    public boolean isDebitoAutomatico() {
        return this.debitoAutomatico && this.dataDebitoAutomatico > 0;
    }

}
