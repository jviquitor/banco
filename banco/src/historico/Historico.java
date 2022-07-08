package historico;

import cartao.Fatura;
import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.databank.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Historico implements Serializable {
	@Serial
	private static final long serialVersionUID = 8L;
	private final ArrayList<Transacao> transacoes;


	private final ArrayList<Fatura> faturas;

	public Historico() {
		this.faturas = new ArrayList<>();
		this.transacoes = new ArrayList<>();
	}

	public ArrayList<Transacao> getTransacoes() {
		return this.transacoes;
	}

	public ArrayList<Fatura> getFaturas() {
		return faturas;
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

	public void addFaturas(Fatura novaFatura) {
		if (!this.faturas.contains(novaFatura)) {
			faturas.add(novaFatura);
		}
	}
}
