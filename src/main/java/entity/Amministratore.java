package entity;

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

    //metodo che vedrà poi Roberta <3
    boolean annullaOrdine(Ordine ordine){
        if(ordine == null) return false;
        if(!ordiniPersonali.contains(ordine)) return false;
        ordine.setStato(Stato.ANNULLATO);
        return true;
    }
}
