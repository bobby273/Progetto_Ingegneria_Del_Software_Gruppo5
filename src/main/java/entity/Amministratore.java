package entity;

//import StubPagamento.InterfacciaPagamento;
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

    public Amministratore(String email, String nome, String cognome, String password, String badge){
        super(email, nome, cognome, password);
        this.badge=badge;
    }

   /* public boolean annullaOrdine(String id_ordine) {
        StoricoOrdini storicoOrdini = StoricoOrdini.getInstance();
        Ordine ordine = storicoOrdini.cercaOrdinePerId(id_ordine);
        if(ordine == null) return false;
        if(ordine.getStato()==Stato.CONSEGNATO
                || ordine.getStato()==Stato.SPEDITO
                || ordine.getStato()==Stato.ANNULLATO) return false;
        ordine.setStato(Stato.ANNULLATO);
        //InterfacciaPagamento.RimborsaOrdine(ordine);
        //invio notifiche
        return true;
    }
quindi a
     TODO: da scommentare*/
}
