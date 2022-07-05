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
    /*
    protected Double getFatura() {
        return limiteUsado;
    }
    */

	/**
	 * Metodo apenas utilizada para quando o cliente pagar a fatura e ter o seu limite atual atualizado conforme pagamento
	 */
	protected void aumentarLimiteAtual(Double valorPagoFatura) {
		this.limiteUsado -= valorPagoFatura;
	}

	protected void diminuirLimiteAtual(Double valorGasto) {
		this.limiteUsado += valorGasto;
	}

	protected boolean adicionarNovoCartao(Cartao cartao) {
		return listaDeCartao.add(cartao);
	}

	protected List<Cartao> getListaDeCartao() {
		return listaDeCartao;
	}

	public Double getLimiteMaximo() {
		return limiteMaximo;
	}

	protected void setLimiteMaximo(Double limiteMaximo) {
		this.limiteMaximo = limiteMaximo;
	}

	/*
	protected Double getLimiteUsado() {
		return limiteUsado;
	}

	public void setLimiteUsado(Double limiteUsado) {
		this.limiteUsado = limiteUsado;
	}
	*/
	protected boolean isDebitoAutomatico() {
		return this.debitoAutomatico;
	}

	/*
	protected void setDebitoAutomatico(boolean debitoAutomatico) {
		this.debitoAutomatico = debitoAutomatico;
	}
	*/
	//TODO A INterface precisa ter um local que mostra a gerencia do cartao pra ativar ou nao o debito automatico
	protected void setDebitoAutomatico(boolean debitoAutomatico, Data dataDebitoAutomatico) {
		this.debitoAutomatico = debitoAutomatico;
		this.dataDebitoAutomatico = dataDebitoAutomatico;
	}


}
