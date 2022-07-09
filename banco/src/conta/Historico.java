package conta;

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
    private final ArrayList<Transacao> TRANSACOES;
    private final ArrayList<Fatura> FATURAS;

    protected Historico() {
        this.FATURAS = new ArrayList<>();
        this.TRANSACOES = new ArrayList<>();
    }

    public void addTransacao(Transacao novaTransacao) throws TransacaoException {
        if (!this.TRANSACOES.contains(novaTransacao)) {
            int index = 0;
            boolean achouPos = false;
            for (int i = 0; i < TRANSACOES.size() && !achouPos; i++) {
                Data dataNovaCmp = novaTransacao.hasDataAgendada() ? novaTransacao.getDataAgendada() : novaTransacao.getDataEmissaoTransacao();
                Data dataAtualCmp = TRANSACOES.get(i).hasDataAgendada() ? TRANSACOES.get(i).getDataAgendada() : TRANSACOES.get(i).getDataEmissaoTransacao();
                index = i + 1;
                if (dataNovaCmp.depoisDe(dataAtualCmp)) {
                    index--;
                    achouPos = true;
                }
            }
            TRANSACOES.add(index, novaTransacao);
        } else {
            throw new TransacaoException("Transacao ja existe");
        }
    }

    public void addFaturas(Fatura novaFatura) {
        if (!this.FATURAS.contains(novaFatura)) {
            FATURAS.add(novaFatura);
        }
    }

    public ArrayList<Transacao> getTransacoes() {
        return this.TRANSACOES;
    }

    public ArrayList<Fatura> getFaturas() {
        return FATURAS;
    }
}
