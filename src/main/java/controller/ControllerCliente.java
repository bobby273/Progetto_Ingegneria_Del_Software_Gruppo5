package controller;

//import StubPagamento.InterfacciaPagamento;
import entity.ClientFacade; //per comunicare con livello entity

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerCliente {

    //TODO: simulo la mail di cliente attuale
    private static String MAIL_CLIENTE="";

    //Attributi
    private static ClientFacade clientFacade;

    public ControllerCliente(String mailUtente){
        this.MAIL_CLIENTE=mailUtente;
        this.clientFacade = new ClientFacade(mailUtente);
    }

    //Metodi esposti
    public static boolean AggiungiAlCarrello(String NomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        System.out.println("Eseguendo UC: AggiungiProdottoAlCarrello per "+ MAIL_CLIENTE);
        //Metodo controller richiamato dal boundary FrameDettaglioProdotto
        return clientFacade.aggiungiOAggiornaProdottoACarrello(MAIL_CLIENTE,NomeProdotto, qtaDesiderata);

    }

    public static boolean rimuoviProdottoDalCarrello(String NomeProdotto){
        return clientFacade.rimuoviProdottoDalCarrello(MAIL_CLIENTE, NomeProdotto);
    }

    public boolean annullaOrdine(String id_ordine){
        return clientFacade.annullaOrdine(id_ordine);
    }

    public boolean creaOrdine(String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        return clientFacade.creaOrdine(indirizzo,num_carta,CCV,meseScadenza,annoScadenza); //TODO: Andrebbe aggiunto MAIL_CLIENTE come primo parametro
    }

    //Per accedere all'intero catalogo --> facade (che spacchetta i dati)
    /*TODO: public static List<Prodotto> getTuttiIProdotti(){
        return clientFacade.getTuttiIProdotti();
        --> note to self: rimosso e sostituito con metodo getCatalogoBreve() [ora è in Facade]
    }*/

    //Spacchettamento e impacchettamento (catalogo e carrello) per evitare che Boundary debba importare Prodotto
    public static List<String[]> getCatalogoBreve() {
        return clientFacade.getCatalogoBreve();
    }

    public static List<String[]> getCarrelloBreve() {
        return clientFacade.getCarrelloBreve(MAIL_CLIENTE);
    }

    public static void apriDettaglioProdotto(String nomeProdotto) {
        Object[] prodotto = clientFacade.getDettagliProdotto(nomeProdotto);

        if (prodotto != null) {
            //Ricezione dalla Facade
            new boundary.FrameDettaglioProdotto(
                    (String) prodotto[0],
                    (float) prodotto[1],
                    (String) prodotto[2],
                    (int) prodotto[3],
                    (boolean) prodotto[4],
                    (String) prodotto[5]
            );
        }
    }

    // Metodo per far verificare alla Boundary se un prodotto esiste (permettere stampa di mess errore apposito [testing])
    public static boolean esisteProdotto(String nomeProdotto) {
        // Verifico mediante metodo Facade
        return clientFacade.esisteProdotto(nomeProdotto);
    }


    //metodo per ricevere i dati dalla client Facade e passarli alla GUI frameRicercaProdotto
    public static List<String[]> ricercaProdottoInCatalogo(String categoriaRicerca, String elementoDaCercare) {
        return clientFacade.ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
    }

    public String getStato(String id_ordine){
        return clientFacade.getStato(id_ordine);
    }

    public float getTotale(String id_ordine){
        return clientFacade.getTotale(id_ordine);
    }

    public LocalDateTime getDataConferma(String id_ordine){
        return clientFacade.getDataConferma(id_ordine);
    }

    public String getIndirizzoSpedizione(String id_ordine){
        return clientFacade.getIndirizzoSpedizione(id_ordine);
    }

    public ArrayList<String> getProdottiEQuantita(String id_ordine){
        return clientFacade.getProdottoEQuantita(id_ordine);
    }

    public Long getIdCliente(String id_ordine){
        return clientFacade.getIdClienteDaIdOrdine(id_ordine);
    }


}


