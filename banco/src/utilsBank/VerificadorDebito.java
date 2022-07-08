package utilsBank;

import agencia.Agencia;
import conta.Conta;
import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.util.ArrayList;

public class VerificadorDebito extends Thread {
	private VerificadorDebito instance;
	private boolean loop;

	private VerificadorDebito() {
		this.loop = true;
	}

	public VerificadorDebito getInstance() {
		if (instance == null) {
			instance = new VerificadorDebito();
			instance.start();
		}
		return instance;
	}

	@Override
	public void run() {
		while (loop) {
			Data dataAtual = DataBank.criarData(DataBank.SEM_HORA);
			ArrayList<Transacao> transacoesAgendadas = Agencia.getTransacoes();
			boolean emDia = false;
			for (Transacao transacao : transacoesAgendadas) {
				if (!transacao.getDataAgendada().depoisDe(dataAtual)) {
					Conta origem = transacao.getContaOrigem();
					//TODO como tratar o erro?
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
	}

	public void end() {
		this.loop = false;
	}
}
