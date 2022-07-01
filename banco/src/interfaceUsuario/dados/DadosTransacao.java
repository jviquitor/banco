package interfaceUsuario.dados;

import agencia.Agencia;
import cliente.Cliente;
import conta.exceptions.TipoInvalido;
import transacao.Boleto;
import transacao.Pagavel;
import transacao.Pix;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DadosTransacao implements Serializable {
	private final Double valor;
	private final String dataVencimento; //@Lembrando sera setada no construtor e sera utilizada com o getters
	private final Integer multaPorDias;
	private Cliente cobrador;  //Cobrador cobrara o dinheiro de alguem recebe o dinheiro
	private Cliente pagador; //Pagador pagara o dinheiro cobrado pelo cobrador LEMBRANDO QUE SE O PAGADOR FOR NULO DEVE SER UM BOLETO O TIPO DE TRANSACAO
	private final String tipoDaTransacao;

	public DadosTransacao(Double valor, String dataVencimento, Integer multaPorDias, String tipoDaTransacao, String chaveCobrador) {
		this.valor = valor;
		this.dataVencimento = dataVencimento;
		this.multaPorDias = multaPorDias;
		this.tipoDaTransacao = tipoDaTransacao;
		setCobradorBoleto(chaveCobrador);
	}

	public DadosTransacao(Double valor, String tipoDaTransacao, String chaveCobrador, String chavePagador, String tipoChaveCobrador, String tipoChavePagador) {
		this.valor = valor;
		this.dataVencimento = null;
		this.multaPorDias = null;
		this.tipoDaTransacao = tipoDaTransacao;
		setCobradorPix(chaveCobrador, tipoChaveCobrador);
		verificarPagador(chavePagador, tipoChavePagador);
	}

	private void setCobradorBoleto(String chave) {
		this.cobrador = Agencia.buscarCliente(chave);
	}

	private void setCobradorPix(String chave, String tipoDaChave) {
		this.cobrador = Agencia.buscarClientePorChavePix(tipoDaChave, chave);
	}

	private void verificarPagador(String chave, String tipoDaChave) {
		this.pagador = Agencia.buscarClientePorChavePix(tipoDaChave, chave);
	}

	public Integer getMultaPorDias() {
		return multaPorDias;
	}

	public Cliente getCobrador() {
		return this.cobrador;
	}

	public Data getDataVencimento() {
		return DataBank.criarData(dataVencimento, DataBank.SEM_HORA);
	}

	public String getDataVencimentoString() {
		return getDataVencimento().toString(DataBank.SEM_HORA);
	}

	public Cliente getPagador() {
		return this.pagador;
	}

	public Double getValor() {
		return valor;
	}

	public Pagavel getTipoDaTransacao() {
		List<String> pix = new ArrayList<>(Arrays.asList("pix", "p1x", "piss", "pixxx"));
		List<String> boleto = new ArrayList<>(Arrays.asList("boleto", "bol3t0", "boleta"));

		if (pix.contains(this.tipoDaTransacao)) {
			return new Pix(this);
		} else if (boleto.contains(this.tipoDaTransacao)) {
			return new Boleto(this);
		}
		throw new TipoInvalido("Nenhum tipo de Transacao escolhida");
	}
}
