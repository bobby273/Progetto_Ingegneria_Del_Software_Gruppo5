package entity;

//import StubPagamento.InterfacciaPagamento;
import database.GestorePersistenza;

import java.util.List;
import java.util.Map;

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


    //questo metodo non deve essere implementato qui ma in carrello [vedi GitHub]
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

    //metodo per accedere al catalogo (visualizza tutti i prodotti)
    public List<Prodotto> getTuttiIProdotti(){
        return Catalogo.getInstance().getTuttiIProdotti();
    }
}

