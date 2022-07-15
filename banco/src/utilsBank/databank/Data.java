package utilsBank.databank;

import java.io.Serial;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Data implements Serializable {
    public static final int DIA = 0;
    public static final int MES = 1;
    public static final int ANO = 2;
    @Serial
    private static final long serialVersionUID = 4L;
    private final Calendar calendar;
    private final int hora;
    private final int minuto;
    private final int segundo;
    private int dia;
    private int mes;
    private int ano;

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
                    text = this.toString().split(" ")[0];
                    break;
                case DataBank.SEM_ANO:
                    text = this.toString().split("/")[0] + "/" + this.toString().split("/")[1];
                    break;
                case DataBank.SEM_MES:
                    text = this.toString().split("/")[0];
                    break;
                case DataBank.SEM_BARRA:
                    text = text.replace("/", "");
                    break;
            }
        }
        return text;
    }

    public boolean equals(Data outra) {
        return this.dia == outra.dia && this.mes == outra.mes && this.ano == outra.ano;
    }

    @SuppressWarnings("unused")
    public boolean equals(Data outra, int[] flags) {
        for (int flag : flags) {
            switch (flag) {
                case Data.DIA:
                    if (this.dia != outra.dia) {
                        return false;
                    }
                    break;
                case Data.MES:
                    if (this.mes != outra.mes) {
                        return false;
                    }
                    break;
                case Data.ANO:
                    if (this.ano != outra.ano) {
                        return false;
                    }
                    break;
            }
        }
        return true;
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

    public void somar(int valor, int flag) {
        switch (flag) {
            case Data.DIA:
                this.dia += valor;
                break;
            case Data.MES:
                this.mes += valor;
                break;
            case Data.ANO:
                this.ano += valor;
                break;
        }
    }

    public int getDia() {
        return this.dia;
    }
}
