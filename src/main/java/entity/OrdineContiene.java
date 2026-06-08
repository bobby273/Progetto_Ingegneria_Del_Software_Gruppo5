package entity;

import jakarta.persistence.*;

@Entity
public class OrdineContiene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String ordine_id;

    private int quantità;

    @ManyToOne
    private Prodotto prodotto;

    //Costruttori
    OrdineContiene(Prodotto prodotto, int quantita, Ordine o) {
        this.prodotto = prodotto;
        this.quantità = quantita;
        this.ordine_id = o.getId();
    }

    public OrdineContiene() {
    }

    //Getter e setter
    int getQuantita() {
        return quantità;
    }

    void setQuantita(int quantita) {
        this.quantità = quantita;
    }

    Prodotto getProdotto() {
        return prodotto;
    }
}
