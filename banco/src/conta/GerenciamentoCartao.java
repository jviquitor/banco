package conta;

import cartao.Cartao;
import utilsBank.databank.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GerenciamentoCartao implements Serializable {
	//TODO Parcelamento com juros dependendo do tipo do cartoa
	@Serial
	private static final long serialVersionUID = 7L;
	private final List<Cartao> listaDeCartao;
	private Double limiteMaximo;
	private Double limiteUsado;
	private boolean debitoAutomatico = false;
	private Data dataDebitoAutomatico;

	//TODO construtor singleton
	protected GerenciamentoCartao() {
		this.listaDeCartao = new ArrayList<>();
	}

	protected Data getDataDebitoAutomatico() {
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
	 * Retorna o valor do limite restante para ser gasto
	 *
	 * @return Double
	 */
	public Double getLimiteRestante() {
		return limiteMaximo - limiteUsado;
	}

	/**
	 * Diminui o Limite usado com base no parametro informado
	 *
	 * @param valorPagoFatura
	 */
	protected void aumentarLimiteAtual(Double valorPagoFatura) {
		this.limiteUsado -= valorPagoFatura;
	}

	/**
	 * Aumenta o limite usado com base no parametor informado
	 *
	 * @param valorGasto
	 */

	protected void diminuirLimiteAtual(Double valorGasto) {
		this.limiteUsado += valorGasto;
	}

	/**
	 * Recebe um Cartao e verifica se o Cartao ja foi inserido na lista do Cartao, caso nao, adiciona
	 *
	 * @param cartao
	 * @return Boolean
	 */

	protected boolean adicionarNovoCartao(Cartao cartao) {
		if (!listaDeCartao.contains(cartao)) {
			return listaDeCartao.add(cartao);
		}
		return false;
	}

	protected List<Cartao> getListaDeCartao() {
		return listaDeCartao;
	}


	/**
	 * Retorna o limite maximo do Cartao
	 *
	 * @return Double
	 */

	public Double getLimiteMaximo() {
		return limiteMaximo;
	}

	/**
	 * Coloca o LimiteMaximo
	 *
	 * @param limiteMaximo
	 */

	protected void setLimiteMaximo(Double limiteMaximo) {
		this.limiteMaximo = limiteMaximo;
	}

	public boolean isDebitoAutomatico() {
		return this.debitoAutomatico;
	}

	public void setDebitoAutomatico(boolean debitoAutomatico, Data dataDebitoAutomatico) {
		this.debitoAutomatico = debitoAutomatico;
		this.dataDebitoAutomatico = dataDebitoAutomatico;
	}


}
