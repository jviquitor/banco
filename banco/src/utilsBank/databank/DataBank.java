package utilsBank.databank;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DataBank {
	private static final Locale IDIOMA = new Locale("pt", "BR");
	private static final int FORMATO = DateFormat.SHORT;
	public static final int SEM_HORA = 0;
	public static final int COM_HORA = 1;

	/**
	 * @return Objeto Data a partir da hora atual.
	 */
	public static Data criarData(int flag) {
		// Retorna a data atual.
		Calendar data = Calendar.getInstance(IDIOMA);
		if (flag == SEM_HORA) {
			int ano = data.get(Calendar.YEAR);
			int mes = data.get(Calendar.MONTH);
			int dia = data.get(Calendar.DAY_OF_MONTH);
			data.set(ano, mes, dia, 0, 0, 0);
		}
		return new Data(data);
	}

	/**
	 * Gera a data a partir da string dataTexto.
	 *
	 * @param dataTexto Data no formato "DD/MM/YYYY" caso a flag seja SEM_HORA ou "DD/MM/YYYY HH:MM:SS" caso a flag seja COM_HORA.
	 * @return Conversão do dataTexto em objeto Data.
	 */
	public static Data criarData(String dataTexto, int flag) {
		String[] partes = dataTexto.split(" ");
		String[] parteDma = partes[0].split("/");
		int[] dma = new int[3];
		for (int i = 0; i < 3; i++) {
			dma[i] = Integer.parseInt(parteDma[i]);
		}
		Calendar data = Calendar.getInstance();
		if (flag == SEM_HORA) {
			data.set(dma[2], dma[1] - 1, dma[0], 0, 0, 0);
		} else {
			String[] parteHorario = partes[1].split(":");
			int[] horario = new int[3];
			for (int i = 0; i < 3; i++) {
				horario[i] = Integer.parseInt(parteHorario[i]);
			}
			data.set(dma[2], dma[1] - 1, dma[0], horario[0], horario[1], horario[2]);
		}
		return new Data(data);
	}
}
