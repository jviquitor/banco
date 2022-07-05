package utilsBank.databank;

import java.io.Serial;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Data implements Serializable {
	@Serial
	private static final long serialVersionUID = 4L;
	private final Calendar calendar;
	private final int dia;
	private final int mes;
	private final int ano;
	private final int hora;
	private final int minuto;
	private final int segundo;

	public Data(Calendar data) {
		this.calendar = data;
		this.dia = data.get(Calendar.DAY_OF_MONTH);
		this.mes = data.get(Calendar.MONTH) + 1;
		this.ano = data.get(Calendar.YEAR);
		this.hora = data.get(Calendar.HOUR_OF_DAY);
		this.minuto = data.get(Calendar.MINUTE);
		this.segundo = data.get(Calendar.SECOND);
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

	public String toString(int[] flags) {
		String text = this.toString();
		for (int flag : flags) {
			switch (flag) {
				case DataBank.SEM_HORA:
					text = text.split(" ")[0];
					break;
				case DataBank.SEM_BARRA:
					text = text.replace("/", "");
			}
		}
		return text;
	}

	public boolean equals(Data outra) {
		return this.calendar.compareTo(outra.calendar) == 0;
	}

	public boolean depoisDe(Data outra) {
		return this.calendar.compareTo(outra.calendar) > 0;
	}

	public boolean antesDe(Data outra) {
		return this.calendar.compareTo(outra.calendar) < 0;
	}

	public int calcularIntervalo(Data outra) {
		return (int) ChronoUnit.DAYS.between(this.calendar.toInstant(), outra.calendar.toInstant());
	}
}
