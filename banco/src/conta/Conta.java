package conta;

import cartao.*;
import cliente.Cliente;
import conta.exceptions.TipoInvalido;
import historico.Historico;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.Dados;
import transacao.Transacao;
import utilsBank.GeracaoAleatoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Conta {
	protected static final int TAMANHO_ID_CONTA = 4;
	
	protected String idConta;

	protected int saquesOcorridos;
	protected double saldo;
	protected double dinheiroGuardado;
	protected double dinheiroDisponivelEmprestimo;

	protected List<Transacao> transacoesRealizadas;
	protected List<Transacao> transacoesAgendadas;
	protected List<Transacao> transacoesRecebidas;
	protected List<Transacao> notificacoes;
	protected Historico historico;

	protected List<Cartao> listaDeCartao;
	protected int limiteMaximo;
	protected int limiteUsado;

	protected Conta() {
		this.idConta = GeracaoAleatoria.gerarIdConta(Conta.TAMANHO_ID_CONTA);
		this.saquesOcorridos = 0;
		this.saldo = 0;
		this.dinheiroGuardado = 0;
		this.transacoesRealizadas = new ArrayList<>();
		this.transacoesAgendadas = new ArrayList<>();
		this.transacoesRecebidas = new ArrayList<>();
		this.notificacoes = new ArrayList<>();
		this.historico = new Historico();
		this.listaDeCartao = new ArrayList<>();
		this.limiteUsado = 0;
	}

	public boolean hasCartao(String tipo) {
		for (Cartao cartao: listaDeCartao) {
			if (tipo.equalsIgnoreCase(cartao.getTipoCartao())) {
				return true;
			}
		}
		return false;
	}

	public boolean transferir(Cliente cliente) {
		//Criar uma nova transação
			//Essa transação precisará dos dados informados
		//Realiza a transação
		return false;
	}

	public static Conta criarConta() {
		//Sabendo que o cliente está online (a Interface precisa tratar isso)
		Dados dados = InterfaceUsuario.getDados();
		Conta conta;
		if (dados == null) {
			return null;
		}
		else {
			List<String> standard = new ArrayList<>(Arrays.asList("standard", "normal", "conta de pobre", "qualquer conta", "basica"));
			List<String> premium = new ArrayList<>(Arrays.asList("premium", "plus", "conta mediana"));
			List<String> diamond = new ArrayList<>(Arrays.asList("diamond", "a melhor", "com mais beneficios", "conta de rico"));


			if (diamond.contains(dados.getTipoDaConta().toLowerCase(Locale.ROOT))) {
				conta = new ContaDiamond(dados);
            } else if (premium.contains(dados.getTipoDaConta().toLowerCase(Locale.ROOT))) {
				conta = new ContaPremium(dados);
			} else if (standard.contains(dados.getTipoDaConta().toLowerCase(Locale.ROOT))) {
				conta = new ContaStandard(dados);
			} else {
				throw new TipoInvalido("Por favor, escolha um tipo de conta valido");
			}
			if (dados.hasCartaoCredito()) {
				conta.criarCartao(TipoCartao.CARTAO_CREDITO);
			} else if (dados.hasCartaoDebit()) {
				conta.criarCartao(TipoCartao.CARTAO_DEBITO);
			}
		}
		return conta;
	}

	public boolean criarCartao(TipoCartao tCartao) {
		Cartao cartao;

		//aqui tem que passar a informaçao da função dele ser debito
		if (this.getClass() == ContaStandard.class) {
			cartao = new CartaoStandard();
		} else if (this.getClass() == ContaPremium.class) {
			cartao = new CartaoPremium();
		} else if (this.getClass() == ContaDiamond.class) {
			cartao = new CartaoDiamond();
		} else {
			throw new TipoInvalido("Tipo do cartao invalido.");
		}

		this.listaDeCartao.add(cartao);
		return true;
	}


//	public boolean pagar(Transacao formaDeTransacao);//Nao é abstrata
//	public boolean depositar(Transacao formaDeTransacao);//Nao é abstrata
//	public boolean agendarTransacao();//Nao é abstrata
//	public boolean resetNotificacoes();//Nao é abstrata
//	public abstract boolean renderSaldo();


}
