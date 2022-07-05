package historico;

import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.databank.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Historico implements Serializable {
	@Serial
	private static final long serialVersionUID = 8L;
	//TODO HISTORICO DE FATURA
	private final ArrayList<Transacao> transacoes;

	public Historico() {
		this.transacoes = new ArrayList<>();
	}

	public ArrayList<Transacao> getTransacoes() {
		return this.transacoes;
	}

	public void addTransacao(Transacao novaTransacao) throws TransacaoException {
		if (!this.transacoes.contains(novaTransacao)) {
			int index = 0;
			boolean achouPos = false;
			for (int i = 0; i < transacoes.size() && !achouPos; i++) {
				Data dataNovaCmp = novaTransacao.hasDataAgendada() ? novaTransacao.getDataAgendada() : novaTransacao.getDataEmissaoTransacao();
				Data dataAtualCmp = transacoes.get(i).hasDataAgendada() ? transacoes.get(i).getDataAgendada() : transacoes.get(i).getDataEmissaoTransacao();
				if (dataNovaCmp.antesDe(dataAtualCmp)) {
					index = i;
					achouPos = true;
				}
			}
			transacoes.add(index, novaTransacao);
		} else {
			throw new TransacaoException("Transacao ja existe");
		}
	}
}
