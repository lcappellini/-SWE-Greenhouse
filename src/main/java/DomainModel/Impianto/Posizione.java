package main.java.DomainModel.Impianto;

public class Posizione {

    private int id;
    private Irrigatore irriqatore;
    private IgrometroTerra igrometroTerra;
    private boolean assegnata;

    //costruttore

    public Posizione(int id, Irrigatore irriqatore, IgrometroTerra igrometroTerra, boolean assegnata) {
        this.id = id;
        this.irriqatore = irriqatore;
        this.igrometroTerra = igrometroTerra;
        this.assegnata = assegnata;
    }

    public Posizione(int id) {
        this.id = id;
    }
    //setters

    //getters
    public int getId() { return id; }
    public Irrigatore getIrriqatore() { return irriqatore; }
    public IgrometroTerra getIgrometroTerra() { return igrometroTerra; }
    public boolean eAssegnata() { return assegnata; }
}
