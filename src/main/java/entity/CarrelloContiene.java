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
    CarrelloContiene(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public CarrelloContiene() {
    }

    //Getter e setter
    int getQuantita() {
        return quantita;
    }
    void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    Prodotto getProdotto() {
        return prodotto;
    }

    float getPrezzo(){
        return prodotto.getPrezzo();
    }
}
