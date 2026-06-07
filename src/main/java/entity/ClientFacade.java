package entity;

import StubPagamento.InterfacciaPagamento;
import database.GestorePersistenza;

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

        try{
            //Cerca prodotto per nome
            Prodotto prodotto = gp.cercaPrimoPerCampi(Prodotto.class, Map.of("nome", nomeProdotto));
            if(prodotto == null){ //Se volessi imporre vincolo sulla quantità disponibile lo farei qui
                return false;
            }

            //Cerca carrello per mail utente
            Carrello carrello = gp.cercaPrimoPerCampi(Carrello.class, Map.of("mailUtente", mailUtente));

            //Se carrello non esiste, errore
            if(carrello == null){ //TODO: Domanda: qui posso o no creare un nuovo carrello (essendo non information expert)?
                return false;
            }

            //chiamata a classe Carrello per aggiungere o aggiornare prodotto
            carrello.aggiungiOAggiornaProdotto(prodotto, qtaDesiderata);

            gp.aggiorna(carrello);
            return true;

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

