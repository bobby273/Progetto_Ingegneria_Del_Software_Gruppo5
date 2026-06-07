package controller;

//import StubPagamento.InterfacciaPagamento;
import entity.ClientFacade; //per comunicare con livello entity
import entity.Cliente;
import entity.Prodotto;

import java.util.ArrayList;
import java.util.List;

public class ControllerCliente {

    //Attributi
    private static ClientFacade clientFacade = new ClientFacade();

    //TODO: simulo la mail di cliente attuale
    private static final String MAIL_CLIENTE = "fornataro.ma@gmail.com";


    //Metodi esposti
    public static boolean AggiungiAlCarrello(String NomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        System.out.println("Eseguendo UC: AggiungiProdottoAlCarrello per "+ MAIL_CLIENTE);
        //Metodo controller richiamato dal boundary FrameDettaglioProdotto
        return clientFacade.aggiungiOAggiornaProdottoACarrello(MAIL_CLIENTE, NomeProdotto, qtaDesiderata);

    }

    public boolean richiediannullamentoOrdine(String id_ordine){
        return clientFacade.annullaOrdine (id_ordine);
    }

    public void creaOrdine(Cliente cliente, String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        clientFacade.creaOrdine(cliente, indirizzo,num_carta,CCV,meseScadenza,annoScadenza);
    }

    //Per accedere all'intero catalogo --> facade
    public static List<Prodotto> getTuttiIProdotti(){
        return clientFacade.getTuttiIProdotti();
    }

    //Spacchettamento e impacchettamento per evitare che Boundary debba importare Prodotto
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
}
