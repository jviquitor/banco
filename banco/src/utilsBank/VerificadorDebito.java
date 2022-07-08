package utilsBank;

public class VerificadorDebito extends Thread {
	private VerificadorDebito instance;
	private boolean loop;

	private VerificadorDebito() {
		this.loop = true;
	}

	public VerificadorDebito getInstance() {
		if (instance == null) {
			instance = new VerificadorDebito();
			instance.start();
		}
		return instance;
	}

	@Override
	public void run() {
		while (loop) {

		}
	}

	public void end() {
		this.loop = false;
	}
}
