package main.java.DomainModel.Pianta;
import javax.swing.text.DateFormatter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pianta {
    private int id;
    private String tipoPianta;

    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    private LocalDateTime dataInizio;
    private String descrizione;
    private String stato;
    private double costo;
    private int giorni_crescita;

    public int getGiorni_crescita() {
        return giorni_crescita;
    }
    public void setGiorni_crescitaDaTipo(String tipoPianta) {
        switch (tipoPianta){
            case "Basilico" -> giorni_crescita = 15;
            case "Rosa" -> giorni_crescita = 30;
            case "Geranio" -> giorni_crescita = 45;
            case "Girasole" -> giorni_crescita = 50;
        }
    }

    public Pianta(int id, String tipoPianta, String descrizione, String dataInizio, String stato) {
        this.id = id;
        this.tipoPianta = tipoPianta;
        this.descrizione = descrizione;
        this.dataInizio = LocalDateTime.parse(dataInizio);
        this.stato = stato;
        setCosto(tipoPianta);
    }
    public Pianta(String tipoPianta) {
        this.tipoPianta = tipoPianta;
        this.setGiorni_crescitaDaTipo(tipoPianta);
        this.setCosto(tipoPianta);
    }

    public Pianta(String tipoPianta, String stato) {
        this.tipoPianta = tipoPianta;
        this.stato = stato;
        this.setGiorni_crescitaDaTipo(tipoPianta);
        this.setCosto(tipoPianta);
    }

    public Pianta(int id, String tipoPianta) {
        this(tipoPianta);
        this.id = id;
        this.dataInizio = LocalDateTime.now();
        this.descrizione = "["+dataInizio.toString()+"]: Piantata. ";
    }

    public Pianta(int id, String tipoPianta, String descrizione) {
        this(id, tipoPianta);
        this.descrizione += descrizione;
    }

    public double getCosto(){ return costo; }
    public void setCosto(String tipoPianta){
        switch (tipoPianta){
            case "Basilico" -> this.costo = 1.5;
            case "Rosa" -> this.costo = 3.5;
            case "Geranio" -> this.costo = 2.5;
            case "Girasole" -> this.costo = 4;
            default -> this.costo = 0;
        }
    }
    public String getDescrizione(){
        return descrizione;
    }

    public String generaStato(){
        float probabilitaOttimale = 0.93f; // Valore di base

        // Genera stato
        if (Math.random() < probabilitaOttimale) {
            this.stato = "sta crescendo";
        } else {
            this.stato = "ha bisogno di cure";
        }
        return "Stato " + tipoPianta + "[" + id + "] -> " + stato;
    }

    public String getTipoPianta() {
        return tipoPianta;
    }

    public int getId() {return this.id;}

    public String getStato() {return this.stato;}

    public void setId(int id) {this.id = id;}

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDataInizio(LocalDateTime now) {
        this.dataInizio = now;
    }

    public boolean haBisogno() {
        return this.stato.equals("ha bisogno di cure");
    }

    public String cura(int id_operatore, LocalDateTime lt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String c = "[" + lt.format(formatter) + "] Curata da Operatore(" + id_operatore + ") .";
        this.descrizione += c;
        return c;
    }
}
