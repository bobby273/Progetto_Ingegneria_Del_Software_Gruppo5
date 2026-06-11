package entity;

import jakarta.persistence.*;

@Entity
public class OrdineContiene {

    // 1. CHIAVE PRIMARIA REALE: Ogni riga dello scontrino avrà il suo ID univoco (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. RIMOSSO @Id: Ora è una colonna normale, così possono esserci più prodotti con lo stesso ordine_id
    private String ordine_id;

    private int quantità;

    // Lasciamo il ManyToOne pulito senza Cascade, non vogliamo che cancellando la riga si cancelli il Prodotto dal catalogo
    @ManyToOne
    private Prodotto prodotto;

    // Costruttori (Rimangono IDENTICI ai tuoi, così non devi modificare nulla altrove!)
    OrdineContiene(Prodotto prodotto, int quantita, Ordine o) {
        this.prodotto = prodotto;
        this.quantità = quantita;
        this.ordine_id = o.getId();
    }

    public OrdineContiene() {
    }

    // Getter e setter
    Long getId() {
        return id;
    }

    int getQuantita() {
        return quantità;
    }

    void setQuantita(int quantita) {
        this.quantità = quantita;
    }

    Prodotto getProdotto() {
        return prodotto;
    }

    String getOrdine_id() {
        return ordine_id;
    }
}
