package entity;

//import StubPagamento.InterfacciaPagamento;
import database.GestorePersistenza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientFacade {

    //Attributi
    private final GestorePersistenza gp = new GestorePersistenza();
    private Cliente cliente; //TODO: Va rimosso

    //Metodi

    public ClientFacade(String mailUtente){ //TODO: Va rimosso
        cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
    }

    public boolean annullaOrdine(String id_ordine) {
        boolean annullato = cliente.annullaOrdine(id_ordine);
        Ordine aggiorna=null;
        if(annullato){
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            aggiorna = gp.aggiorna(o);
        }
        return (aggiorna!=null);
    }

    public boolean creaOrdine(String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        Ordine o = cliente.creaOrdine(indirizzo,num_carta,CCV,meseScadenza,annoScadenza);
        gp.salva(o);
        return o==null;
    }


    //questo metodo non deve essere implementato qui ma in carrello [vedi GitHub]
    public boolean aggiungiOAggiornaProdottoACarrello(String mailUtente, String nomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        //Delego ricerca del prodotto all'informatione expert [catalogo]
        Prodotto prodotto = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

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

    public Long getIdClienteDaIdOrdine(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getIdCliente();
    }

    public String getStato(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getStato().toString();
    }

    public float getTotale(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getTotale();
    }

    public LocalDateTime getDataConferma(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getDataConferma();
    }

    public String getIndirizzoSpedizione(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getIndirizzoSpedizione();
    }

    public ArrayList<String> getProdottoEQuantita(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        ArrayList<OrdineContiene> pc=o.getProdottiContenuti();
        ArrayList<String> prodotti = new  ArrayList<>();
        for(OrdineContiene c:pc){
            prodotti.add(c.getProdotto().getNome());
            prodotti.add(String.valueOf(c.getQuantita()));
        }

        return prodotti;
    }

}

