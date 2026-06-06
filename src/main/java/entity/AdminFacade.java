package entity;

public class AdminFacade {

    public boolean annullaOrdine(Amministratore amministratore, Ordine ordine){
        boolean done = amministratore.annullaOrdine(ordine);
        return done;
    }
}
