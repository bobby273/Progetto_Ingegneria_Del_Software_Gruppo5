package entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
import Exceptions.ErroreDisponibilitaException;

public class Ordine {
    Stato stato;
    Cliente cliente;
    String id;
    float totale;
    LocalDateTime dataConferma;
    String indirizzoSpedizione;
    ArrayList<OrdineContiene> prodottiContenuti;

    Ordine(Cliente cliente, String indirizzoSpedizione, Carrello carrello){
        this.cliente = cliente;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.prodottiContenuti = new ArrayList<>();
        this.totale = 0;

        this.prodottiContenuti = aggiungiDaCarrello(carrello);

        StoricoOrdini.getInstance().aggiungiOrdine(this);
        //in questo modo, l'ordine appena instanziato verrà aggiunto automaticamente allo Storico
    }

    private ArrayList<OrdineContiene>  aggiungiDaCarrello(Carrello carrello){
        ArrayList<CarrelloContiene> prodottiInCarrello = carrello.getProdottiContenuti();
        if(prodottiInCarrello.isEmpty()) return null;
        ArrayList<OrdineContiene> prodottiInOrdine = new ArrayList<>();
        for (CarrelloContiene carrelloContiene : prodottiInCarrello) {
            if (!(carrelloContiene.getQuantita() > carrelloContiene.getProdotto().getQtaDisponibile())) {
                prodottiInOrdine.add(new OrdineContiene(carrelloContiene.getQuantita(), carrelloContiene.getProdotto()));
            } else {
                throw new ErroreDisponibilitaException("quantità desiderata non disponibile");
            }
        }
        return prodottiInOrdine;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
