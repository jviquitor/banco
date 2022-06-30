package interfaceUsuario.dados;

import cartao.FuncaoCartao;
import conta.exceptions.TipoInvalido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DadosCartao {
	private final String apelidoCartao;
	private final String funcaoCartao;

	public DadosCartao(String apelidoCartao, String nomeTitular, String funcaoCartao) {
		this.apelidoCartao = apelidoCartao;
		this.funcaoCartao = funcaoCartao;
	}

	public String getApelidoCartao() {
		return apelidoCartao;
	}


	public FuncaoCartao getFuncaoCartao() {
		List<String> debito = new ArrayList<>(Arrays.asList("debito", "débito"));
		List<String> credito = new ArrayList<>(Arrays.asList("crédito", "credito"));

		if (debito.contains(this.funcaoCartao.toLowerCase())) {
			return FuncaoCartao.CARTAO_DEBITO;
		} else if (credito.contains(this.funcaoCartao.toLowerCase())) {
			return FuncaoCartao.CARTAO_CREDITO;
		}
		throw new TipoInvalido("Por favor, escolha um tipo de cartao valido");
	}
}
