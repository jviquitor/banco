import agencia.Agencia;
import agencia.exceptions.InsercaoException;
import cliente.exceptions.LoginException;
import interfaceUsuario.MenuUsuario;

public class Main {
	public static void main(String[] args) throws LoginException, InsercaoException {
		Agencia agencia = Agencia.getInstance();
		MenuUsuario.iniciar();
	}
}
