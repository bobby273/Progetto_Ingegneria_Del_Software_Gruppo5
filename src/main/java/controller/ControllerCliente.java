package controller;

//import StubPagamento.InterfacciaPagamento;
import entity.CarrelloContiene;
import entity.ClientFacade; //per comunicare con livello entity
import entity.Cliente;
import entity.Prodotto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerCliente {

    //TODO: simulo la mail di cliente attuale
    private static final String MAIL_CLIENTE = "fornataro.ma@gmail.com";

    //Attributi
    private static ClientFacade clientFacade = new ClientFacade(MAIL_CLIENTE);

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

    public void creaOrdine(Cliente cliente, String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        clientFacade.creaOrdine(indirizzo,num_carta,CCV,meseScadenza,annoScadenza);
    }

    //Per accedere all'intero catalogo --> facade
    public static List<Prodotto> getTuttiIProdotti(){
        return clientFacade.getTuttiIProdotti();
    }

    //Spacchettamento e impacchettamento (catalogo e carrello) per evitare che Boundary debba importare Prodotto
    public static List<String[]> getCatalogoBreve() {
        List<Prodotto> prodotti = clientFacade.getTuttiIProdotti();
        List<String[]> catalogoBreve = new ArrayList<>();

        if (prodotti != null) {
            for (Prodotto p : prodotti) {
                catalogoBreve.add(new String[]{p.getNome(), p.getDescrizione()});
            }
        }
        return catalogoBreve;
    }

    public static List<String[]> getCarrelloBreve() {
        List<CarrelloContiene> itemsInseriti = clientFacade.getProdottiNelCarrello(MAIL_CLIENTE);
        List<String[]> carrelloBreve = new ArrayList<>();

        if(itemsInseriti != null){
            for(CarrelloContiene item : itemsInseriti) {
                Prodotto p = item.getProdotto();
                carrelloBreve.add(new String[]{
                        p.getNome(),
                        p.getDescrizione(),
                        String.valueOf(p.getPrezzo()),
                        p.getCategoria(),
                        String.valueOf(item.getQuantita())
                });
            }
        }
        return carrelloBreve;
    }

    public static void apriDettaglioProdotto(String nomeProdotto) {
        Prodotto p = clientFacade.ricercaProdotto(nomeProdotto);

        if (p != null) {
            new boundary.FrameDettaglioProdotto(
                    p.getNome(),
                    p.getPrezzo(),
                    p.getCategoria(),
                    p.getQtaDisponibile(),
                    p.IsScontato(),
                    p.getDescrizione()
            );
        }
    }

    // Metodo per far verificare alla Boundary se un prodotto esiste (permettere stampa di mess errore apposito [testing])
    public static boolean esisteProdotto(String nomeProdotto) {
        // Verifico mediante metodo Facade
        return clientFacade.ricercaProdotto(nomeProdotto) != null;
    }

    public List<Prodotto> ricercaProdottoInCatalogo(String categoriaRicerca, String elementoDaCercare) {
        if (elementoDaCercare == null || elementoDaCercare.trim().isEmpty()) {
            return clientFacade.getTuttiIProdotti();
        }

        // Deleghiamo il lavoro alla Facade
        return clientFacade.ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
    }


    //metodo per "formattare" i dati ottenuti e darli alla GUI
    public static List<String[]> ricercaFormattata(String categoriaRicerca, String elementoDaCercare) {
        ControllerCliente controller = new ControllerCliente();
        List<Prodotto> prodotti = controller.ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
        //impacchetto i risultati come succede nell'esempio
        List<String[]> datiPronti = new java.util.ArrayList<>();
        if (prodotti != null) {
            for (Prodotto p : prodotti) {
                datiPronti.add(new String[]{
                        p.getNome(),
                        p.getCategoria(),
                        String.valueOf(p.getPrezzo()),
                        p.getDescrizione(),
                        String.valueOf(p.getQtaDisponibile()),
                        String.valueOf(p.IsScontato()),
                        String.valueOf(p.IsScontato())
                });
            }
        }
        return datiPronti;
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


