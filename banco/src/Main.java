import agencia.Agencia;
import interfaceUsuario.menus.MenuUsuario;
import utilsBank.VerificadorDiario;

public class Main {
	public static void main(String[] args) {
		try {
			Agencia agencia = Agencia.getInstance();
			agencia.abrindoAgencia();
			VerificadorDiario verificadorDiario = VerificadorDiario.getInstance();
			MenuUsuario.iniciar();
			verificadorDiario.end();
			System.out.println("Obrigada por acessar ao nosso Internet Banking!");
		} catch (Exception ex) {
			System.out.println("Nao foi possivel iniciar o banco");
		}
	}
}
