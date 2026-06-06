package controller;

import entity.AdminFacade;
import entity.Ordine;
import entity.Stato;

public class ControllerAmministratore {
    //costruttore
    //TODO: verificarne la correttezza
    private AdminFacade amministratore;
    public ControllerAmministratore(AdminFacade amministratore) {
        this.amministratore = amministratore;
    }

    public boolean richiediAnnullamentoOrdine(Ordine ordineDaAnnullare) {
        // REGOLA: posso annullare qualsiasi ordine non consegnatp
        if (ordineDaAnnullare.getStato() != Stato.CONSEGNATO) {
            return false;
        }
        boolean successo = this.amministratore.annullaOrdine(ordineDaAnnullare);

        if (successo) {
            // TODO: per quando avremo il database
            System.out.println("Attendi");
        }

        return successo;
    }

}
