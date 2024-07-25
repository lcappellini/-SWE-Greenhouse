package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Ambiente;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Ordine;
import main.java.ORM.PosizionamentoDAO;

import java.util.ArrayList;
import java.util.List;


public class GestionePosizionamenti {
    private Ambiente ambiente;

    public GestionePosizionamenti() {

    }
    public void creaPoisizionamento(Ordine ordine, List<Integer> posizioniLibere, int idOperatore) throws Exception {
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        posizionamentoDAO.creaPosizionamento(ordine, posizioniLibere, idOperatore);
    }

    public List<Integer> liberaPosizionamenti(int idOrdine) {
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        return posizionamentoDAO.liberaPosizionamenti(idOrdine);
    }


    public void visualizzaPosizionamenti(int idOridne) {
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        posizionamentoDAO.visualizzaPosizionamenti(idOridne);
    }
}
