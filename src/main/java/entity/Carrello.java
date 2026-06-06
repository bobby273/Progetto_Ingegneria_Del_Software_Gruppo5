package entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

 @Entity
public class Carrello {

     //Attributi
     @Id
     private final String mailUtente;

     @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
     private ArrayList<CarrelloContiene> prodottiContenuti; // =righe della tabella carrello
     //CarrelloContiene = RigaCarrello {quantità + prodotto}

     //Costruttori
     public Carrello(String mailUtente) {
        this.mailUtente = mailUtente;
        this.prodottiContenuti = new ArrayList<>();
     }

     public Carrello() {
         this.mailUtente = null;
         this.prodottiContenuti = new ArrayList<>();
     }

     //Metodi
     public void aggiungiOAggiornaProdotto(Prodotto prodotto, int qtaDesiderata) {
         for(CarrelloContiene riga : prodottiContenuti) {
             if(riga.getProdotto().getNome().equals(prodotto.getNome())){ //Se trovo il prodotto nel carrello
                 riga.setQuantita(riga.getQuantita() + qtaDesiderata);
                 return;
             }
         }
         //Se non esiste già il prodotto
         CarrelloContiene nuovo = new CarrelloContiene(prodotto, qtaDesiderata);
         prodottiContenuti.add(nuovo);
     }

     //Getter + setter
     public String getMailUtente() {
        return mailUtente;
     }

     public ArrayList<CarrelloContiene> getProdottiContenuti() {
        return prodottiContenuti;
     }
     public void setProdottiContenuti(ArrayList<CarrelloContiene> prodottiContenuti) {
        this.prodottiContenuti = prodottiContenuti;
    }
}
