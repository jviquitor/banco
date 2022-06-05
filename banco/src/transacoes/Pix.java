package transacoes;

public class Pix extends Transacoes {
	private String nomeDaTransacao = "Pix";
	private int quantidadeDeChavesAtuais;
	private final int quantidadeDeChavesMax = 4;
	private ChavePix chave;
}
