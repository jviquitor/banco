import agencia.Agencia;
import interfaceUsuario.MenuUsuario;

public class Main {
	public static void main(String[] args) {
		Agencia agencia = Agencia.getInstance();
		MenuUsuario.iniciar();
	}
}
