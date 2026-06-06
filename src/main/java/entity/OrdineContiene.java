package entity;

public class OrdineContiene {
    int quantità;
    Prodotto prodotto;

    public OrdineContiene(int quantita, Prodotto prodotto) {
        this.prodotto = prodotto;
        this.quantità = quantita;
    }
}
