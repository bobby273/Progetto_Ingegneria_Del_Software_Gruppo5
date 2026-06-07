package controller;

import entity.AdminFacade;
import entity.Amministratore;
import database.GestorePersistenza;
import entity.Prodotto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerAmministratore {

    //TODO: SIMULO BADGE
    private static final String badge = "123456";
    private GestorePersistenza gp;
    //Attributi
    private static AdminFacade adminFacade = new AdminFacade(badge);

    public boolean annullaOrdine(String id_ordine) {
        return adminFacade.annullaOrdine(id_ordine);
    }

    public static java.util.List<String[]> ottieniListaProdotti() {
        return entity.AdminFacade.getListaProdottiPerScorrimento();
    }

    public static boolean creaNuovoProdotto(String nome, String categoria, double prezzo, String descrizione, int qta, boolean disponibile, boolean scontato) {
        System.out.println("CONTROLLER: Richiesta creazione prodotto. Delego alla Facade...");
        return AdminFacade.creaProdotto(nome, categoria, prezzo, descrizione, qta, disponibile, scontato);
    }

    public static boolean modificaNomeProdotto(String nomeOriginale, String nuovoNome) {
        return AdminFacade.modificaNome(nomeOriginale, nuovoNome);
    }

    public static boolean modificaCategoriaProdotto(String nome, String nuovaCat) {
        return AdminFacade.modificaCategoria(nome, nuovaCat);
    }

    public static boolean modificaPrezzoProdotto(String nome, float nuovoPrezzo) {
        return AdminFacade.modificaPrezzo(nome, nuovoPrezzo);
    }

    public static boolean modificaDescrizioneProdotto(String nome, String nuovaDesc) {
        return AdminFacade.modificaDescrizione(nome, nuovaDesc);
    }

    public static boolean modificaQuantitaProdotto(String nome, int nuovaQta) {
        return AdminFacade.modificaQuantita(nome, nuovaQta);
    }

    public static boolean modificaDisponibilitaProdotto(String nome, boolean nuovaDisp) {
        return AdminFacade.modificaDisponibilita(nome, nuovaDisp);
    }

    public static boolean modificaScontoProdotto(String nome, boolean nuovoSconto) {
        return AdminFacade.modificaSconto(nome, nuovoSconto);
    }

    public static boolean rimuoviProdotto(String nomeProdotto) {
        return AdminFacade.rimuoviProdotto(nomeProdotto);
    }

    public List<Prodotto> ricercaProdottoInCatalogo(String categoriaRicerca, String elementoDaCercare) {
        if (elementoDaCercare == null || elementoDaCercare.trim().isEmpty()) {
            return adminFacade.getTuttiIProdotti();
        }

        // Deleghiamo il lavoro alla Facade
        return adminFacade.ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
    }

    public String getStato(String id_ordine){
        return adminFacade.getStato(id_ordine);
    }

    public float getTotale(String id_ordine){
        return adminFacade.getTotale(id_ordine);
    }

    public LocalDateTime getDataConferma(String id_ordine){
        return adminFacade.getDataConferma(id_ordine);
    }

    public String getIndirizzoSpedizione(String id_ordine){
        return adminFacade.getIndirizzoSpedizione(id_ordine);
    }

    public ArrayList<String> getProdottiEQuantita(String id_ordine){
        return adminFacade.getProdottoEQuantita(id_ordine);
    }

    public Long getIdCliente(String id_ordine){
        return adminFacade.getIdClienteDaIdOrdine(id_ordine);
    }

}

