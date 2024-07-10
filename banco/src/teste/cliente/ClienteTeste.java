package teste.cliente;

//import das classes
import cliente.ClientePessoa;
import cliente.exceptions.LoginException;
import conta.Conta;
import interfaceUsuario.exceptions.ValorInvalido;
import cliente.Endereco;
import cartao.Fatura;

//import dos testes
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import agencia.exceptions.BuscaException;


public class ClienteTeste {
	
	ClientePessoa cliente;
    ClientePessoa cliente2;
    Conta conta;
    Endereco endereco;
	
    @BeforeEach
	public void inicializa() throws ValorInvalido{
        String cep = "24715-051";
        Integer numero = 357;
        String complemento = "Em frente ao 358";
        
        endereco = new Endereco(cep, numero, complemento);
        
		String nome = "Vitor";
        String email = "vitor@email.com";
        String telefone = "(21) 9999-9999";
        Integer idade = 30;
        String cpf = "000.111.333.22";
        String senha = "Senha123";
        
        
        cliente = new ClientePessoa(nome, email, telefone, idade, endereco, cpf, senha);
        cliente2 = new ClientePessoa(nome, email, telefone, idade, endereco, cpf, senha);
        
	}
    
    @Test
    public void getNomeTest() {
    	String nomeTeste = cliente.getNome();
    	
    	Assertions.assertEquals(nomeTeste, cliente.getNome());
    }
    
    @Test
    public void getIdentificacaoTest() {
    	String cpfTeste = cliente.getIdentificacao();
    	
    	Assertions.assertEquals(cpfTeste, cliente.getIdentificacao());
    }
    
    @Test
    public void getContaTest() throws ValorInvalido, LoginException{
        conta = cliente.getConta();
        
        Assertions.assertEquals(conta, cliente.getConta());
    }
    
    @Test
    public void allInfosTest() {
    	String allInfosTeste = cliente.allInfos();
    	
    	Assertions.assertNotNull(allInfosTeste);
    }
    
    @Test void pagarFaturaTest() {
    	Fatura faturaTeste = new Fatura(100.00, cliente);
    	
    	cliente.pagarFatura(100.00);
    	Assertions.assertNotNull(faturaTeste);
    }

    
    @Test
    public void verificarSenhaTest() throws ValorInvalido {
    	boolean senhaTeste1;
    	boolean senhaTeste2;

		try {
			senhaTeste1 = cliente.verificarSenha("Senha123");
			Assertions.assertTrue(senhaTeste1);
		} catch (Exception LoginException) {
			senhaTeste1 = false;
			Assertions.assertFalse(senhaTeste1);
		}
		
		try {
			senhaTeste2 = cliente.verificarSenha("senha123");
			Assertions.assertTrue(senhaTeste2);
		} catch (Exception LoginException) {
			senhaTeste2 = false;
			Assertions.assertFalse(senhaTeste2);
		}
    	
    }
	
}