package conta;

public interface Rentavel {
	Double FATOR_RENDER_STANDARD = 0.2;
	Double FATOR_RENDER_PREMIUM = 0.5;
	Double FATOR_RENDER_DIAMOND = 0.8;

	void renderSaldo();
}
