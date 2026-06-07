package entity;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class OrdineContiene {
    @Id
    private String ordine_id;

    private int quantità;

    @ManyToOne
    private Prodotto prodotto;

    //Costruttori
    public OrdineContiene(Prodotto prodotto, int quantita, Ordine o) {
        this.prodotto = prodotto;
        this.quantità = quantita;
        this.ordine_id = o.getId();
    }

    public OrdineContiene() {
    }

    //Getter e setter
    public int getQuantita() {
        return quantità;
    }
    public void setQuantita(int quantita) {
        this.quantità = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }
}
