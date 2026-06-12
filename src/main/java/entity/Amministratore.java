package entity;

import StubPagamento.InterfacciaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Amministratore extends Utente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String badge;

    public Amministratore(){}

    Amministratore(String email, String nome, String cognome, String password, String badge){
        super(email, nome, cognome, password);
        this.badge=badge;
    }

   boolean annullaOrdine(String id_ordine) {

        StoricoOrdini storicoOrdini = StoricoOrdini.getInstance();
        Ordine ordine = storicoOrdini.cercaOrdinePerId(id_ordine);
        if(ordine == null) return false;
        //imposto lo stato dell'ordine
        if(ordine.getStato()==Stato.CONSEGNATO
                || ordine.getStato()==Stato.SPEDITO
                || ordine.getStato()==Stato.ANNULLATO) return false;
        //se l'ordine non è rimborsato non è possibile annullarlo
        if(!InterfacciaPagamento.RimborsaOrdine(ordine)) return false;
        ordine.setStato(Stato.ANNULLATO);
        StoricoOrdini.getInstance().inviaNotifiche();
       //Rimettiamo i prodotti nel catalogo
       Catalogo catalogo = Catalogo.getInstance();
       for(OrdineContiene c : ordine.getProdottiContenuti()) {
           Prodotto prodotto = c.getProdotto();
           int quantita = c.getQuantita();
           catalogo.modificaQuantita(prodotto.getNome(), prodotto.getQtaDisponibile() + quantita);
       }
        return true;
    }
}
