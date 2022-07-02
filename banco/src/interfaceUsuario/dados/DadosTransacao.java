package interfaceUsuario.dados;

import agencia.Agencia;
import cliente.Cliente;

import java.io.Serializable;

public class DadosTransacao implements Serializable {
	private final Double valor;
	private Cliente destino;  //destino o dinheiro vai para o destino
	private Cliente origem; //origem o dinheiro sai da origem

	public DadosTransacao(Double valor, String chaveDestino, String chaveorigem, String tipoChaveDestino, String tipoChaveOrigem) {
		this.valor = valor;
		setDestinoPix(chaveDestino, tipoChaveDestino);
		setOrigemPix(chaveorigem, tipoChaveOrigem);
	}

	private void setDestinoBoleto(String chave) {
		this.destino = Agencia.buscarCliente(chave);
	}

	private void setDestinoPix(String chave, String tipoDaChave) {
		this.destino = Agencia.buscarClientePorChavePix(tipoDaChave, chave);
	}

	private void setOrigemPix(String chave, String tipoDaChave) {
		this.origem = Agencia.buscarClientePorChavePix(tipoDaChave, chave);
	}

	public Cliente getdestino() {
		return this.destino;
	}


	public Cliente getorigem() {
		return this.origem;
	}

	public Double getValor() {
		return valor;
	}

}
