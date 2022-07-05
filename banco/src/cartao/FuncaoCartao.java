package cartao;

public enum FuncaoCartao {
	CARTAO_DEBITO,
	CARTAO_CREDITO,
	;

	@Override
	public String toString() {
		if (this.equals(CARTAO_CREDITO)) {
			return "CREDITO";
		} else {
			return "DEBITO";
		}
	}
}
