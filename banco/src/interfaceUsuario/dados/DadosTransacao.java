package interfaceUsuario.dados;

import conta.Conta;
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
	private String dataVencimento;
	private int multaPorDias;
	private Conta cobrador;  //Cobrador cobrara o dinheiro de alguem recebe o dinheiro
	private Conta pagador; //Pagador pagara o dinheiro cobrado pelo cobrador LEMBRANDO QUE SE O PAGADOR FOR NULO DEVE SER UM BOLETO O TIPO DE TRANSACAO
	private String tipoDaTransacao;

	public int getMultaPorDias() {
		return multaPorDias;
	}

	public Data getDataVencimento() {
		return DataBank.criaData(dataVencimento);
	}

	public String getDataVencimentoInt() {
		String dataA[] = dataVencimento.split("/");
		return String.format("%02d/%02d/%04d", Integer.parseInt(dataA[0]), Integer.parseInt(dataA[1]), Integer.parseInt(dataA[2]));
	}

	public Conta getCobrador() {
		return cobrador;
	}

	public Conta getPagador() {
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
