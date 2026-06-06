package entity;

import jakarta.persistence.*;

@Entity
public class CarrelloContiene {
    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    int quantita;

    @ManyToOne
    Prodotto prodotto;

    //Costruttori
    public CarrelloContiene(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public CarrelloContiene() {
    }

    //Getter e setter
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }
}
