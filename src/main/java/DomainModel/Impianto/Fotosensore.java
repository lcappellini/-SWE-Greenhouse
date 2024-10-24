package main.java.DomainModel.Impianto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class Fotosensore extends Sensore {
    public Fotosensore(int id, String dataString, float valore) {
        super(id);
        this.valore = valore;
        if (dataString == null)
            this.data = null;
        else
            this.data = LocalDateTime.parse(dataString);
    }

    @Override
    public float misura(LocalDateTime lt, boolean attuatore_acceso) {
        int ora = lt.getHour();
        float valoreLuce;

        // Logica per la misurazione della luce in base all'ora del giorno
        if (ora >= 6 && ora <= 18) {
            // Giorno: valori di luce tra 100 e 3000
            valoreLuce = (float)(100 + (Math.random() * (3000 - 100 + 1))); // Valore tra 100 e 3000
        } else {
            // Notte: valori di luce più bassi, tra 0 e 50 (o anche un valore minimo)
            valoreLuce = (float)(0 + (Math.random() * 51)); // Valore tra 0 e 50
        }

        // Se l'attuatore è acceso (es. lampada accesa), aggiunge luce artificiale
        if (attuatore_acceso) {
            // Aggiunge un valore casuale tra 50 e 500 per simulare la luce artificiale
            valoreLuce += (float)(50 + (Math.random() * 451)); // Aggiunge un valore tra 50 e 500
        }

        // Limita il valore massimo della luce a 3000
        valoreLuce = Math.min(valoreLuce, 3000);

        this.data = lt;
        this.valore = valoreLuce;
        return this.valore;
    }


    public String getTipoSensore(){
        return "Fotosensore";
    }
}
