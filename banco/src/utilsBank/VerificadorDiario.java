package utilsBank;

import agencia.Agencia;
import conta.Conta;
import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.arquivo.GerenciadorArquivo;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.util.ArrayList;

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

	public Data getUltimaAtualizacao() {
		return this.ultimaAtualizacao;
	}

	public void verificarTransacoesAgendadas(Data dataAtual) {
		ArrayList<Transacao> transacoesAgendadas = Agencia.getTransacoes();
		for (Transacao transacao : transacoesAgendadas) {
			if (!transacao.getDataAgendada().depoisDe(dataAtual)) {
				Conta origem = transacao.getContaOrigem();
				try {
					if (origem.getSaldo() < transacao.getValor()) {
						transacoesAgendadas.remove(transacao);
						origem.apagarTransacaoAgendada(transacao);
					} else {
						origem.realizarTransacaoAgendada(transacao);
					}
				} catch (TransacaoException ignore) {
				}
			}
		}
	}

	@Override
	public void run() {
		while (loop) {
			Data dataAtual = DataBank.criarData(DataBank.SEM_HORA);
			if (dataAtual.depoisDe(this.ultimaAtualizacao)) {
				System.out.println("ATUALIZANDO...");
				verificarTransacoesAgendadas(dataAtual);
				Agencia.getInstance().renderContas();
				this.ultimaAtualizacao = dataAtual;
			}
		}
	}

	public void end() {
		this.loop = false;
	}
}
