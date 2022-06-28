package utilsBank.databank;

import java.io.Serializable;
import java.util.Calendar;

public class Data implements Serializable {
	private final int dia;
	private final int mes;
	private final int ano;
	private final int hora;
	private final int minuto;
	private final int segundo;

	public Data(Calendar data) {
		this.dia = data.get(Calendar.DAY_OF_MONTH);
		this.mes = data.get(Calendar.MONTH) + 1;
		this.ano = data.get(Calendar.YEAR);
		this.hora = data.get(Calendar.HOUR_OF_DAY);
		this.minuto = data.get(Calendar.MINUTE);
		this.segundo = data.get(Calendar.SECOND);
	}

	public int getDia() {
		return dia;
	}

	public int getMes() {
		return mes;
	}

	public int getAno() {
		return ano;
	}

	public int getHora() {
		return hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public int getSegundo() {
		return segundo;
	}

	@Override
	public String toString() {
		return String.format(
				"%02d/%02d/%04d %02d:%02d:%02d",
				this.dia,
				this.mes,
				this.ano,
				this.hora,
				this.minuto,
				this.segundo
		);
	}

	public String toString(int flag) {
		if (flag == DataBank.SEM_HORA) {
			return this.toString().split(" ")[0];
		}
		return this.toString();
	}
}
