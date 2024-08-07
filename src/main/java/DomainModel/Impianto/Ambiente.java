package main.java.DomainModel.Impianto;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ambiente {

    private int id;
    private int nPosizioniMax;
    private ArrayList<Posizione> posizioni;
    private ArrayList<Sensore> sensori;
    private ArrayList<Attuatore> attuatori;
    private Map<String, Float> misure;
    private Map<String, Boolean> accesi;
    private Climatizzatore climatizzatore;
    private Lampada lampada;
    private Fotosensore fotosensore;
    private Termometro termometro;
    private IgrometroAria igrometroAria;

    public Ambiente(int id, int nPosizioniMax) {
        this.id = id;
        this.nPosizioniMax = nPosizioniMax;
    }



    public Ambiente(int id, int nPosMax, int idTermometro, int idIgrometroA,
                  int idFotosensore, int idClimatizzazione, int idLampada) {
        this(id, nPosMax);
        this.sensori = new ArrayList<>();
        this.sensori.add(new Termometro(idTermometro));
        this.sensori.add(new IgrometroAria(idIgrometroA));
        this.sensori.add(new Fotosensore(idFotosensore));
        this.attuatori = new ArrayList<>();
        misure = new HashMap<>();
        this.attuatori.add(new Lampada(idLampada));
        this.attuatori.add(new Climatizzatore(idClimatizzazione));

    }



    public int getnPosizioniMax() {
        return nPosizioniMax;
    }

    public ArrayList<Posizione> getPosizioni() { return posizioni; }

    public int getTemperatura(){
        //FIXME int i = termometro.chiedi();
        return 0;
    }

    public boolean eDisponibile(int nPosti){
        int i = 0;
        for(Posizione p : posizioni){
            if(!p.eAssegnata()){
                i = i + 1;
            }
        }
        return i == nPosti;
    }

    public void setPosizioni(ArrayList<Posizione> posizioni) {
        this.posizioni = posizioni;
    }

    public int getId() {
        return id;
    }

    public void setClimatizzazione(Climatizzatore climatizzazione) {
        this.climatizzatore = climatizzazione;
    }

    public void setLampada(Lampada lampada) {
        this.lampada = lampada;
    }

    public void setFotosensore(Fotosensore fotosensore) {
        this.fotosensore = fotosensore;
    }

    public void setTermometro(Termometro termometro) {
        this.termometro = termometro;
    }

    public void setIgrometroAria(IgrometroAria igrometroAria) {
        this.igrometroAria = igrometroAria;
    }

    public void monitoraggio(int ore){

    }

    public void misura(LocalDateTime lt, Map<String, Boolean> attuatore_acceso){
        misure.clear();
        for( Sensore s : sensori){
            boolean acceso = false;
            switch (s.tipoSensore()){
                case ("Termometro"), ("IgrometroAria") -> {
                    acceso = attuatore_acceso.get("Climatizzazione");
                }
                case ("Fotosensore") -> {
                    acceso = attuatore_acceso.get("Lampada");
                }
            }
            misure.put(s.tipoSensore() ,s.misura(lt, acceso));
        }
    }

    public Map<String, String> aziona(){
        Map<String, String> descrizioni = new HashMap<>();
        accesi.clear();
        for(Attuatore a : attuatori){
            if(a.tipoAttuatore().equals("Climatizzazione")){
                if(((misure.get("Termometro")<15.0 || misure.get("Termometro")>30.0)
                        || misure.get("IgrometroAria")<30)) {
                    if (!a.attivo()) {
                        descrizioni.put(a.tipoAttuatore(), a.esegui(1));
                        accesi.put("Climatizzazione",true);

                    }
                }else if(a.attivo()){
                    descrizioni.put(a.tipoAttuatore(),a.esegui(-1));
                    accesi.put("Climatizzazione",false);
                }

            }else if(a.tipoAttuatore().equals("Lampada")){
                if(misure.get("Fotosensore")<200 ){
                    if(!a.attivo()){
                        descrizioni.put(a.tipoAttuatore(),a.esegui(1));
                        accesi.put("Lampada",true);
                    }
                }else if(a.attivo()){
                    descrizioni.put(a.tipoAttuatore(),a.esegui(-1));
                    accesi.put("Lampada",false);
                }
            }

        }
        return descrizioni;
    }

    public Map<String, Boolean> getAttuatoriAccesi() {
        return accesi;
    }

    public ArrayList<Sensore> getSensori() {
        return sensori;
    }

    public Sensore getSensore(String tipoSensore) {
        Sensore sensore = null;
        for(Sensore s : sensori){
            if(s.tipoSensore().equals(tipoSensore)){
                sensore = s;
            }
        }
        return sensore;
    }

    public ArrayList<Attuatore> getAttuatori() {
        return attuatori;
    }
}
