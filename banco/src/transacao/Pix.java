package transacao;

public class Pix extends Transacao implements Pagavel {
	private String nomeDaTransacao = "Pix";
	private int quantidadeDeChavesAtuais;
	private final int quantidadeDeChavesMax = 4;
	private ChavePix chave;

}
