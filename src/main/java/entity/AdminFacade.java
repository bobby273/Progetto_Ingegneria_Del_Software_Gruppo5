package entity;
import java.util.List;

public class AdminFacade {

    private Amministratore amministratore;
    public AdminFacade(Amministratore amministratore) {
        this.amministratore = amministratore;
    }
    public boolean annullaOrdine(Ordine ordine){
        boolean done = amministratore.annullaOrdine(ordine);
        return done;
    }
}
