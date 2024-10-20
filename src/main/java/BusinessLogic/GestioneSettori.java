package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.*;
import main.java.ORM.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestioneSettori {
    private ScheduledExecutorService executor;// Corrected: Added type declaration here

    public GestioneSettori() {}

    public Settore getSettoreBySpazio(int idSpazio, int i){
        SettoreDAO settoreDAO = new SettoreDAO();
        return settoreDAO.getSettoreBySpazio(idSpazio, i);
    }

    public void visualizzaSettori(int idSpazio) {
        SettoreDAO settoreDAO = new SettoreDAO();
        int i = 1;
        Settore s = settoreDAO.getSettoreBySpazio(idSpazio, i); // Supponiamo che il metodo recuperi i settori legati allo spazio

        if (s == null) {
            System.out.println("  N/A   "); // Se non ci sono settori, stampa N/A
        } else {
            System.out.println("+------------------------------------------------------------------------------------------+");
            System.out.println("|   ID   | Spazio | Termometro |  Fotosensore | Climatizzazione | Lampada | Igrometro aria |");
            System.out.println("|--------|--------|------------|--------------|-----------------|---------|----------------|");
            // Ciclo che continua fino a quando non ci sono più settori
            do {
                System.out.printf("| %-6d | %-6d | %-10s | %-12s | %-15s | %-7s | %-14s |\n",
                        s.getId(), idSpazio,
                        (s.getTermometro() != null ? s.getTermometro().getId() : "N/A"),
                        (s.getFotosensore() != null ? s.getFotosensore().getId() : "N/A"),
                        (s.getClimatizzazione() != null ? (s.getClimatizzazione().isWorking() ? "ON" : "OFF") : "N/A"),
                        (s.getLampada() != null ? (s.getLampada().isWorking() ? "ON" : "OFF") : "N/A"),
                        (s.getIgrometroAria() != null ? s.getIgrometroAria().getId() : "N/A")
                );
                i++;
                s = settoreDAO.getSettoreBySpazio(idSpazio, i); // Ottiene il settore successivo dello spazio specificato
            } while (s != null);
        }

        System.out.println("+------------------------------------------------------------------------------------------+");
    }

    public void monitoraSettore(Settore settore, LocalDateTime lt) {
        SensoreDAO sensoreDAO = new SensoreDAO();
        AttuatoreDAO attuatoreDAO = new AttuatoreDAO();
        settore.monitora(lt);
        for (Sensore<?> s : settore.getSensori()) {
            sensoreDAO.registraMisura(s);
        }
        for (Attuatore a : settore.getAttuatori()) {
            attuatoreDAO.registraAzione(a, lt.toString());
        }
        // Definisci un formatter per il formato "3 Jun 2008 11:05:30"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss");

        // Converte LocalDateTime in stringa formattata
        String formattedLt = lt.format(formatter);
        // Stampa formattata dei valori
        System.out.println(".");
        // Stampa formattata dei valori con parametri in riga
        System.out.println("TIME: " + formattedLt);  // Stampa il tempo formattato

        System.out.println("| Termometro        | Igrometro (Aria)  | Fotosensore       | A/C     | Lampada |");
        System.out.println("+-------------------+-------------------+-------------------+---------+---------+");

        // Valori corrispondenti
        System.out.printf("| %-14f °C | %-12f /100 | %-11s lumen | %-8s| %-8s|\n",
                settore.getTermometro().getValore(),
                settore.getIgrometroAria().getValore(),
                settore.getFotosensore().getValore(),
                settore.getClimatizzazione().isWorking() ? "ON" : "OFF",
                settore.getLampada().isWorking() ? "ON" : "OFF");

        System.out.println("+-------------------+-------------------+-------------------+---------+---------+");

    }

    public void avviaMonitoraggio(Settore settore) {
        executor = Executors.newScheduledThreadPool(1);

        // Usa un array come wrapper per LocalDateTime
        final LocalDateTime[] lt = {LocalDateTime.now()};

        // Esegue il task di monitoraggio ogni 2 secondi, simulando l'avanzamento di 1 ora
        executor.scheduleAtFixedRate(() -> {
            try {
                // Chiama la funzione monitoraSettore con l'ora simulata
                monitoraSettore(settore, lt[0]);

                // Aggiunge 1 ora a ogni iterazione
                lt[0] = lt[0].plusMinutes(15);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2500, TimeUnit.MILLISECONDS);

        // Crea un thread separato per ascoltare la pressione di un tasto
        new Thread(() -> {
            try {
                System.out.println("Premi un tasto per interrompere il monitoraggio...");

                // Attende la pressione di un tasto
                System.in.read();

                // Ferma il monitoraggio quando viene premuto un tasto
                System.out.println("Tasto premuto! Arresto monitoraggio...");
                stopMonitoraggio();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Funzione per fermare il monitoraggio
    public void stopMonitoraggio() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown(); // Arresta il monitoraggio
            System.out.println("Monitoraggio terminato.");
        }
    }
    public Settore getById(int id){
        SettoreDAO settoreDAO = new SettoreDAO();
        return settoreDAO.getById(id);
    }

    //TODO DA SPOSTARE NEL MAIN
    public Settore richiediSettore(Spazio spazio){
        Scanner scanner1 = new Scanner(System.in);
        //sensoreDAO.visualizza(spazio.getId());
        visualizzaSettori(spazio.getId());
        System.out.print("Inserire ID Settore da selezionare: ");
        int idSettore = Integer.parseInt(scanner1.nextLine());
        Settore settore = null;
        settore = getById(idSettore);
        if(settore == null){
            System.out.println("Settore non trovato.");
            return null;
        }
        return settore;
    }

}
