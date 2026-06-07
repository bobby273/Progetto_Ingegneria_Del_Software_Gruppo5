package entity;

//import StubPagamento.InterfacciaPagamento;
import database.GestorePersistenza;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientFacade {

    //Attributi
    private final GestorePersistenza gp = new GestorePersistenza();

    //Metodi

    public boolean annullaOrdine(Cliente cliente, String id_ordine){
        return cliente.annullaOrdine(id_ordine);
    }

    public void creaOrdine(Cliente cliente, String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        cliente.creaOrdine(indirizzo,num_carta,CCV,meseScadenza,annoScadenza);
    }


    public boolean aggiungiOAggiornaProdottoACarrello(String mailUtente, String nomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        //Delego ricerca del prodotto all'informatione expert [catalogo]
        Prodotto prodotto = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

        //Non essendovi un I.F. di clienti, accedo direttamente mediante gp
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));

        if(prodotto != null && cliente != null){

            boolean esitoAggiunta = cliente.aggiungiProdottoACarrello(prodotto, qtaDesiderata);

            if(esitoAggiunta){
                gp.aggiorna(cliente);
                return true;
            }
        }
        return false;

    }

    public Prodotto ricercaProdotto(String nomeProdotto) { //TODO: vedi se unirlo all'altro metodo
        return Catalogo.getInstance().ricercaProdotto(nomeProdotto);
    }


    public List<Prodotto> ricercaProdottoInCatalogo(String categoriaRicerca, String elementoDaCercare) {
        return Catalogo.getInstance().ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
    }

    //Manuel: per ottenere prodotti da visualizzare in catalogo
    public List<Prodotto> getTuttiIProdotti(){
        return Catalogo.getInstance().getTuttiIProdotti();
    }

    //Manuel: per ottenere prodotti da visualizzare nel carrello
    public List<CarrelloContiene> getProdottiNelCarrello(String mailUtente){
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
        if(cliente != null){
            return cliente.getProdottiCarrello();
        }
        return new ArrayList<>();
    }

    //Manuel: per rimuovere prodotti dal carrello
    public boolean rimuoviProdottoDalCarrello(String mailUtente, String nomeProdotto){
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
        if(cliente != null){
            Prodotto prodotto = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

            boolean esitoRimozione = cliente.rimuoviProdottoDalCarrello(prodotto);

            if(esitoRimozione){
                gp.aggiorna(cliente);
                return true;
            }
        }
        return false;
    }
}

