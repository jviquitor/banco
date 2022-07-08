import agencia.Agencia;
import interfaceUsuario.MenuUsuario;

public class Main {
	public static void main(String[] args) {
		try {
			Agencia agencia = Agencia.getInstance();
			agencia.abrindoAgencia();
			VerificadorDiario verificadorDiario = VerificadorDiario.getInstance();
			MenuUsuario.iniciar();
		} catch (Exception ex) {
			System.out.println("Nao foi possivel iniciar o banco");
		}
	}
}
