package transacao;

// TODO: 6/5/2022  Colocar um nome mais adequado para essa classe
//Lembrando que essa classe é para gerar quem será a pessoa destino/origem das transações
//O gerar comprovante, precisará saber quem é a pessoa, com o id da agência e o idDaConta
//para INFORMAR no comprovante, quem foi o destino de tal transação ou a origem da mesma

public class PessoaTransacao {
	private final String nome;
	private final String idAgencia;
	private final String idConta;

	public PessoaTransacao(String nome, String idAgencia, String idConta) {
		this.nome = nome;
		this.idAgencia = idAgencia;
		this.idConta = idConta;
	}


}
