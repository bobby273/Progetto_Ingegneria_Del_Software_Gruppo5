package entity;

import database.GestorePersistenza;

import java.util.Map;

public class ClientFacade {

    //Attributi
    private final GestorePersistenza gp = new GestorePersistenza();

    public boolean annullaOrdine(Cliente cliente, Ordine ordine){
        boolean done = cliente.annullaOrdine(ordine);
        return false;
    }

    //Metodi
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

