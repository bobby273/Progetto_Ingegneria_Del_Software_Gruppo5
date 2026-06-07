package controller;

import entity.AdminFacade;
import entity.Amministratore;

public class ControllerAmministratore {
    //Attributi
    private static AdminFacade adminFacade = new AdminFacade();

    public boolean annullaOrdine(Amministratore amministratore, String id_ordine){
        return adminFacade.annullaOrdine(amministratore, id_ordine);
    }
}
