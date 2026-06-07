package entity;

public class AdminFacade {

    public boolean annullaOrdine(Amministratore amministratore, String id_ordine){
        return amministratore.annullaOrdine(id_ordine);
    }
}
