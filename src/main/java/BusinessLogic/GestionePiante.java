package main.java.BusinessLogic;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.ObjectDAO;
import main.java.ORM.PiantaDAO;

import java.util.ArrayList;
import java.util.Map;


public class GestionePiante {
    public GestionePiante() {}

    public void visualizza(Map<String, Object> criteri){
        ObjectDAO dao = new ObjectDAO();
        dao.visualizza("Pianta", criteri);
    }

    public Pianta restituisciPianta(int id){
        PiantaDAO pDAO = new PiantaDAO();
        return pDAO.restituisciPianta(id);
    }

    public void aggiungi(ArrayList<Pianta> piante){
        ObjectDAO dao = new ObjectDAO();
        for(Pianta p : piante){
            dao.inserisci(p);
        }
    }

    public void aggiornaDescrizione(int idPianta, String descrizione) {
        PiantaDAO pDAO = new PiantaDAO();
        pDAO.aggiornaDescrizione(idPianta, descrizione);
    }
}
