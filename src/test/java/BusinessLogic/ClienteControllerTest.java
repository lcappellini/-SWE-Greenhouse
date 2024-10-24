package test.java.BusinessLogic;
import main.java.BusinessLogic.AdminExtraController;
import main.java.BusinessLogic.ClienteController;
import main.java.BusinessLogic.LoginClienteController;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta;
import main.java.ORM.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClienteControllerTest {
    private ClienteController clienteController;
    Cliente cliente;

    @BeforeEach
    public void setUp() {


        LoginClienteController loginClienteController = new LoginClienteController();
        cliente = loginClienteController.accedi("mario@email.it", "123");

        clienteController = new ClienteController(cliente);

        AdminExtraController adminExtraController = new AdminExtraController();
        try{
            adminExtraController.resetDatabase();
            adminExtraController.createDatabase();
            adminExtraController.defaultDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Test
    public void testAggiornaProfilo() {
        System.out.println("--Test Aggiornamento Dati Profilo Utente--");
        System.out.println("Modifica un dato del profilo di un Utente");
        System.out.println("nome -> Sergio");
        boolean result = clienteController.aggiornaProfilo("nome", "Sergio");
        assertTrue(result);
        System.out.println("Test superato!");
    }

    @Test
    public void testRichiediNuovoOrdine_Success() {
        System.out.println("--Test Richiesta nuovo Ordine (Success)--");
        System.out.println("Crea un nuovo Ordine e lo richiede");
        System.out.println("Ordine: 10 Rose");

        ArrayList<Pianta> piante = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            piante.add(new Pianta("Rosa","da piantare"));
        Ordine ordine = new Ordine(cliente.getId(),piante);

        boolean result = clienteController.richiediNuovoOrdine(ordine);
        assertTrue(result);
        System.out.println("Test superato!");
    }

    @Test
    public void testRichiediNuovoOrdine_Fail() {
        System.out.println("--Test Richiesta nuovo Ordine (Fail)--");
        System.out.println("Crea un nuovo Ordine e lo richiede");
        System.out.println("Ordine: Numero piante maggiore delle posizioni libere");

        PosizioneDAO posizioneDAO = new PosizioneDAO();
        int posizioniDisponibili = posizioneDAO.getNNonAssegnate();

        ArrayList<Pianta> piante = new ArrayList<>();
        for (int i = 0; i < posizioniDisponibili+1; i++)  // non ci sono sicuramente 100 posizioni libere
            piante.add(new Pianta("Rosa","da piantare"));
        Ordine ordine = new Ordine(cliente.getId(),piante);

        boolean result = clienteController.richiediNuovoOrdine(ordine);
        assertFalse(result);
        System.out.println("Test superato!");
    }

    @Test
    public void testPagaEritiraOrdine_Success() {
        //boolean result = clienteController.pagaEritiraOrdine(ordine);
        //assertTrue(result);
    }

    @Test
    public void testPagaEritiraOrdine_Fail() {
       // boolean result = clienteController.pagaEritiraOrdine(ordine);
        //assertTrue(result);
    }
}
