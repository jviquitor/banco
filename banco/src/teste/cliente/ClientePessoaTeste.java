/**package teste.cliente;

//import das classes
import cliente.ClientePessoa;
import cliente.exceptions.LoginException;
import cliente.Cliente;
import conta.Conta;
import interfaceUsuario.exceptions.ValorInvalido;

//import dos testes
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import agencia.exceptions.BuscaException;


public class ClientePessoaTeste {
	
	ClientePessoa cliente;
    ClientePessoa cliente2;
    Conta conta;
	
    @BeforeEach
	public void inicializa() throws ValorInvalido{
		String nome = "Vitor";
        String email = "vitor@email.com";
        String telefone = "(21) 9999-9999";
        Integer idade = 30;
        String cpf = "000.111.333.22";
        String senha = "Senha123";

        // Criação do objeto ClientePessoa
        cliente = new ClientePessoa(nome, email, telefone, idade, null, cpf, senha);
        cliente2 = new ClientePessoa(nome, email, telefone, idade, null, cpf, senha);
		
	}
	
	@Test
    public void getParametrosTest() throws ValorInvalido, LoginException{
        cliente.allInfos();

        // Verificações
        String cpfTeste = cliente.getIdentificacao();
        String nomeTeste = cliente.getNome();
        
        conta = cliente.getConta();
        Assertions.assertEquals(cpfTeste, cliente.getIdentificacao());
        Assertions.assertEquals(nomeTeste, cliente.getNome());
        Assertions.assertEquals(conta, cliente.getConta());
    }
	
	@Test
	public void verificarSenhaTest() throws ValorInvalido, LoginException {
		
		boolean senhaTeste = cliente.verificarSenha("Senha123");
		boolean senhaTeste2 = cliente.verificarSenha("senha123");
		Assertions.assertEquals(senhaTeste, true);
		Assertions.assertEquals(senhaTeste2, true);
	}
	
	@Test
	public void equalsTest() {
		boolean igual = cliente.equals(cliente2);
		Assertions.assertEquals(igual, cliente.equals(cliente2));
	}
	
	/*@Test
	public void allinfos() {
		String infos = cliente.allInfos();
		Assertions.assertFalse();
	}*/
	
}