package interfaceUsuario.dados;

import cliente.Cliente;
import conta.exceptions.TipoInvalido;
import transacao.Boleto;
import transacao.Pagavel;
import transacao.Pix;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DadosTransacao {
	private Double valor;
	private String dataVencimento; //@Lembrando sera setada no construtor e sera utilizada com o getters
	private int multaPorDias;
	private Cliente cobrador;  //Cobrador cobrara o dinheiro de alguem recebe o dinheiro
	private Cliente pagador; //Pagador pagara o dinheiro cobrado pelo cobrador LEMBRANDO QUE SE O PAGADOR FOR NULO DEVE SER UM BOLETO O TIPO DE TRANSACAO
	private String tipoDaTransacao;

	public int getMultaPorDias() {
		return multaPorDias;
	}

	public Data getDataVencimento() {
		return DataBank.criarData(dataVencimento, DataBank.SEM_HORA);
	}

	public String getDataVencimentoString() {
		return getDataVencimento().toString(DataBank.SEM_HORA);
	}

	public Cliente getCobrador() {
		return cobrador;
	}

	public Cliente getPagador() {
		return pagador;
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
