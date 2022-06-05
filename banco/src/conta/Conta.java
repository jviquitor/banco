package conta;

import Historico.Historico;
import cartao.Cartao;
import transacao.Transacao;

import java.util.List;

public abstract class Conta {
	private int tipoDaConta;
	private int idConta;

	private int saquesOcorridos;
	private double saldo;
	private double dinheiroGuardado;
	private double dinheiroDisponivelEmprestimo;

	private List<Transacao> transacoesRealizadas;
	private List<Transacao> transacoesAgendadas;
	private List<Transacao> transacaosRecebidas;
	private List<Transacao> transacaoNotificacao;
	private Historico historico;

	private boolean hasCardCredit;
	private boolean hasCardDebit;
	private List<Cartao> listaDeCartao;
	private int limiteMaximo;
	private int limiteAtual;

	public abstract boolean transferir(Transacao formaDeTransacao); //Nao é abstrata
	public abstract boolean pagar(Transacao formaDeTransacao);//Nao é abstrata
	public abstract boolean depositar(Transacao formaDeTransacao);//Nao é abstrata
	public abstract boolean agendarTransacao();//Nao é abstrata
	public abstract boolean resetNotificacoes();//Nao é abstrata
	public abstract boolean renderSaldo();


}
