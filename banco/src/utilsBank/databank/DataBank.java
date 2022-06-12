package utilsBank.databank;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DataBank {
	private static final Locale IDIOMA = new Locale("pt", "BR");
	private static final int FORMATO = DateFormat.SHORT;

	/**
	 * @return Objeto Data a partir da hora atual.
	 */
	public static Data criaData() {
		// Retorna a data atual.
		return new Data(Calendar.getInstance(IDIOMA));
	}

	/**
	 * Gera a data a partir da string dataTexto.
	 *
	 * @param dataTexto Data no formato "DD/MM/YYYY HH:MM:SS".
	 * @return Conversão do dataTexto em objeto Data.
	 */
	public static Data criaData(String dataTexto) {
		String[] partes = dataTexto.split(" ");
		String[] parteDma = partes[0].split("/");
		String[] parteHorario = partes[1].split(":");
		int[] dma = new int[3];
		int[] horario = new int[3];
		for (int i = 0; i < 3; i++) {
			dma[i] = Integer.parseInt(parteDma[i]);
		}
		for (int i = 0; i < 3; i++) {
			horario[i] = Integer.parseInt(parteHorario[i]);
		}
		Calendar data = Calendar.getInstance();
		data.set(dma[2], dma[1] - 1, dma[0], horario[0], horario[1], horario[2]);
		return new Data(data);
	}
}
