package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.*;
import main.java.ORM.*;

import java.util.*;

public class GestioneAttuatori {
    public GestioneAttuatori() {}
    private String[] tipiAttuatori = {"Climatizzazione", "Lampada", "Operatore", "Irrigatore"};
    OperatoreDAO operatoreDAO = new OperatoreDAO();
    AttuatoreDAO attuatoreDAO = new AttuatoreDAO();
    ClimatizzazioneDAO climatizzazioneDAO = new ClimatizzazioneDAO();
    LampadaDAO lampadaDAO = new LampadaDAO();
    IrrigatoreDAO irrigatoreDAO = new IrrigatoreDAO();

    public void registraOperazione(Attuatore a, String descrizione, String data){
        OperazioneDAO dao = new OperazioneDAO();
        dao.registraAzione(a, descrizione, data);
    }

    public void visualizzaLiberi(String tipoAttuatore) {
        ObjectDAO odao = new ObjectDAO();
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("occupato", false);
        odao.visualizza(tipoAttuatore, criteria);
    }

    public void visualizza(String tipoAttuatore, Map<String, Object> criteri) {
        ObjectDAO odao = new ObjectDAO();
        if(Arrays.asList(tipiAttuatori).contains(tipoAttuatore)){
            odao.visualizza(tipoAttuatore, criteri);
        }else{
            System.out.println("Attuatore non trovato");
        }
    }


    public List<Integer> restituisciChiavi(String nomeTabella, Map<String, Object> criteri) {
        ObjectDAO odao = new ObjectDAO();
        return odao.restituisciChiavi(nomeTabella, criteri);
    }


    public Operatore richiediAttuatoreLibero(String tipoAttuatore) {
        Scanner sc = new Scanner(System.in);
        attuatoreDAO.visualizzaLiberi(tipoAttuatore);
        StringBuilder prompt = new StringBuilder("Inserire ID ").append(tipoAttuatore).append(": ");
        System.out.print(prompt.toString());
        int id = Integer.parseInt(sc.nextLine());  // Legge l'ID inserito dall'uten// te
        String query = "SELECT * FROM \"" + tipoAttuatore + "\" WHERE id = ?";
        Operatore attuatore = switch (tipoAttuatore.toLowerCase()) {
            case "operatore" -> operatoreDAO.getById(id);
            default -> throw new IllegalArgumentException("Tipo di attuatore non riconosciuto. ");
        };

        if (attuatore == null || attuatore.attivo()) {
            System.out.println("L'attuatore selezionato non è disponibile.");
            return null;  // Restituisce null se l'attuatore non è valido o è attivo
        }
        return attuatore;  // Restituisce l'attuatore libero selezionato
    }

}
