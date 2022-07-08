import agencia.Agencia;
import interfaceUsuario.MenuUsuario;

public class Main {
	public static void main(String[] args) {
		try {
			Agencia agencia = Agencia.getInstance();
			MenuUsuario.iniciar();
		} catch (Exception ex) {
			System.out.println("Nao foi possivel iniciar o banco");
		}
	}
}
