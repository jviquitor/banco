package utilsBank;

import agencia.Agencia;
import cartao.Fatura;
import cliente.Cliente;
import conta.Conta;
import conta.GerenciamentoCartao;
import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.arquivo.GerenciadorArquivo;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.util.ArrayList;
import java.util.Set;

public class VerificadorDiario extends Thread {
	private static VerificadorDiario instance;
	private boolean loop;
	private Data ultimaAtualizacao;

	private VerificadorDiario() {
		this.loop = true;
		this.ultimaAtualizacao = GerenciadorArquivo.lerData(GerenciadorArquivo.PATH_DATA);
	}

	public static VerificadorDiario getInstance() {
		if (instance == null) {
			instance = new VerificadorDiario();
			instance.start();
		}
		return instance;
	}

	@Override
	public void run() {
		while (loop) {
			Data dataAtual = DataBank.criarData(DataBank.SEM_HORA);
			if (dataAtual.depoisDe(this.ultimaAtualizacao)) {
				System.out.println("ATUALIZANDO...");
				verificarTransacoesAgendadas(dataAtual);
				verificarFaturas(dataAtual);
				Agencia.getInstance().renderContas();
				this.ultimaAtualizacao = dataAtual;
				System.out.println("ATUALIZACAO FINALIZADA!");
			}
		}
	}

	public void end() {
		this.loop = false;
	}

	public void verificarTransacoesAgendadas(Data dataAtual) {
		ArrayList<Transacao> transacoesAgendadas = Agencia.getInstance().getTransacoes();
		ArrayList<Transacao> transacoesRealizadas = new ArrayList<>();
		for (Transacao transacao : transacoesAgendadas) {
			if (transacao.getDataAgendada().equals(dataAtual) || transacao.getDataAgendada().antesDe(dataAtual)) {
				Conta origem = transacao.getContaOrigem();
				try {
					if (origem.getSaldo() < transacao.getValor()) {
						transacoesAgendadas.remove(transacao);
						origem.apagarTransacaoAgendada(transacao);
					} else {
						transacoesRealizadas.add(origem.realizarTransacaoAgendada(transacao));
					}
				} catch (TransacaoException ignore) {
				}
			}
		}
		try {
			transacoesAgendadas.removeAll(transacoesRealizadas);
		} catch (Exception ignore) {
		}
	}

	public void verificarFaturas(Data dataAtual) {
		Set<Cliente> clientes = Agencia.getInstance().getClientes();
		for (Cliente cliente : clientes) {
			GerenciamentoCartao carteira = cliente.getConta().getCARTEIRA();
			if (carteira.isDebitoAutomatico() && dataAtual.getDia() == carteira.getDataDebitoAutomatico()) {
				if (cliente.getConta().getSaldo() < carteira.getFatura()) {
					carteira.diminuirLimiteAtual(carteira.getFatura() * GerenciadorBanco.JUROS_FATURA);
				} else {
					Fatura fatura = cliente.pagarFatura(carteira.getFatura());
					cliente.getConta().addNotificacao(fatura);
				}
			}
		}
	}

	public Data getUltimaAtualizacao() {
		return this.ultimaAtualizacao;
	}


}
