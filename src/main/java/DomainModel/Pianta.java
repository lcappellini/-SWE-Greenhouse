package main.java.DomainModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pianta {
    private int id;
    private String tipoPianta;

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    private LocalDate dataInizio;
    private String descrizione = "";
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
        System.out.println(descrizione);
        this.descrizione = descrizione == null ? "" : descrizione;
        this.dataInizio = LocalDate.parse(dataInizio);
        this.stato = stato;
        setCosto(tipoPianta);
    }

    public Pianta(String tipoPianta, String stato) {
        this.tipoPianta = tipoPianta;
        this.stato = stato;
        this.setGiorni_crescitaDaTipo(tipoPianta);
        this.setCosto(tipoPianta);
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

    public void generaStato(){
        float probabilita = 0.80f;
        if (Math.random() < probabilita) {
            this.stato = "sta crescendo";
        } else {
            this.stato = "ha bisogno di cure";
        }
    }

    public void setStatoHaBisogno() {
        this.stato = "ha bisogno di cure";
    }

    public void setStatoNonHaBisogno() {
        this.stato = "sta crescendo";
    }

    public String getTipoPianta() {
        return tipoPianta;
    }

    public int getId() {return this.id;}

    public String getStato() {return this.stato;}

    public void setId(int id) {this.id = id;}

    public boolean haBisogno() {
        return this.stato.equals("ha bisogno di cure");
    }

    public String cura(int id_operatore, LocalDateTime lt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String c = "[" + lt.format(formatter) + "] Curata da Operatore(" + id_operatore + ") .";
        if(descrizione.isEmpty()){
            this.descrizione = c;
        }else{
            this.descrizione += c;
        }
        this.stato = "Curata, sta crescendo";
        return c;
    }
}
