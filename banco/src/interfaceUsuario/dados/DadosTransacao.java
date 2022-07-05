package interfaceUsuario.dados;

import agencia.Agencia;
import agencia.exceptions.BuscaException;
import cliente.Cliente;

import java.io.Serial;
import java.io.Serializable;

public class DadosTransacao implements Serializable {
	@Serial
	private static final long serialVersionUID = 9L;
	private final Double valor;
	private Cliente destino;  //destino o dinheiro vai para o destino
	private Cliente origem; //origem o dinheiro sai da origem

	public DadosTransacao(Double valor, String chaveDestino, String chaveorigem, String tipoChaveDestino, String tipoChaveOrigem) throws BuscaException {
		this.valor = valor;
		setDestinoPix(chaveDestino, tipoChaveDestino);
		setOrigemPix(chaveorigem, tipoChaveOrigem);
	}

	public DadosTransacao(Double valor, String chaveDestino, String tipoChaveDestino, Cliente cliente) throws BuscaException {
		this.valor = valor;
		setDestinoPix(chaveDestino, tipoChaveDestino);
		setOrigemPix(cliente);
	}

	public DadosTransacao(Double valor, Cliente destino) {
		this.valor = valor;
		this.destino = destino;
	}

	public DadosTransacao(Double valor, Cliente destino, Cliente origem) {
		this.valor = valor;
		this.destino = destino;
		this.origem = origem;
	}


	private void setDestinoBoleto(String chave) throws BuscaException {
		this.destino = Agencia.buscarCliente(chave);
	}

	private void setDestinoPix(String chave, String tipoDaChave) throws BuscaException {
		this.destino = Agencia.buscarClientePorChavePix(tipoDaChave, chave);
	}

	private void setOrigemPix(String chave, String tipoDaChave) throws BuscaException {
		this.origem = Agencia.buscarClientePorChavePix(tipoDaChave, chave);
	}

	private void setOrigemPix(Cliente cliente) {
		this.origem = cliente;
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
