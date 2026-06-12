package entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import StubPagamento.InterfacciaPagamento;
import jakarta.persistence.*;



@Entity
public class Ordine {
    //Attributi
    @Id
    private String id_ordine;

    private Stato stato;
    private float totale;
    private LocalDateTime dataConferma;
    private String indirizzoSpedizione;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrdineContiene> prodottiContenuti;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Cliente cliente;

    //Costruttori
    Ordine(Cliente cliente, String indirizzoSpedizione, Carrello carrello){
        this.cliente = cliente;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.id_ordine = generaID();
        this.stato = Stato.INSERITO;
        this.dataConferma = LocalDateTime.now();
        this.totale = 0;
        this.prodottiContenuti = new ArrayList<>();
        this.prodottiContenuti = aggiungiDaCarrello(carrello);
    }

    public Ordine() {

    }

    //Metodi
    //metodo per aggiungere i prodotti dal carrello al nuovo ordine
    private List<OrdineContiene>  aggiungiDaCarrello(Carrello carrello){
        List<CarrelloContiene> prodottiInCarrello = carrello.getProdottiContenuti();
        List<OrdineContiene> prodottiInOrdine = new ArrayList<>();

        if(prodottiInCarrello == null || prodottiInCarrello.isEmpty()) {
            return prodottiInOrdine;
        }
        //copio dal carrello del cliente al nuovo ordine
        List<CarrelloContiene> copiaCarrello = new ArrayList<>(prodottiInCarrello);

        for (CarrelloContiene carrelloContiene : copiaCarrello) {
            if (!(carrelloContiene.getQuantita() > carrelloContiene.getProdotto().getQtaDisponibile())
                    && carrelloContiene.getProdotto().isDisponibile()
                    && !carrelloContiene.getProdotto().isEliminato()) {

                // Aggiungiamo all'ordine
                prodottiInOrdine.add(new OrdineContiene(carrelloContiene.getProdotto(), carrelloContiene.getQuantita(), this));

                //rimuovo dal carrello
                carrello.rimuoviProdotto(carrelloContiene.getProdotto());

            } else {
                System.out.println("Quantità desiderata del prodotto " + carrelloContiene.getProdotto().getNome() + " non disponibile");
            }
        }

        return prodottiInOrdine;
    }


    //metodo per generare un id univoco per Ordine
    private String generaID(){
        boolean esiste=true;
        int[] id= new int[10];
        java.util.Random random = new java.util.Random();
        String idOrdine="0000000001";
        while(esiste){
            for(int i=0; i<10; i++) {
                id[i] = random.nextInt(10);
            }
            idOrdine = Arrays.stream(id).mapToObj(String::valueOf).collect(Collectors.joining());
            if(StoricoOrdini.getInstance().cercaOrdinePerId(idOrdine)==null){
                esiste=false;
            }
        }
        return idOrdine;
    }

    // Metodo Information Expert: impacchetta tutte le info dell'ordine
    public Object[] getInfoOrdine() {
        // 1. Prepariamo la lista dei prodotti già formattata per la grafica
        ArrayList<String> prodottiStr = new ArrayList<>();
        if (this.prodottiContenuti != null) {
            for (OrdineContiene c : this.prodottiContenuti) {
                prodottiStr.add(c.getProdotto().getNome() + " (Q.tà: " + c.getQuantita() + ")"); //in questo modo, combiniamo i prodotti e la quantità in un'unica stringa
            }
        }

        // 2. Restituiamo l'array con tutti i dati nell'ordine esatto
        return new Object[]{
                this.id_ordine,
                this.totale,
                this.stato != null ? this.stato.toString() : "N/D",
                this.dataConferma,
                this.indirizzoSpedizione,
                prodottiStr,
                this.cliente != null ? this.cliente.getId() : null
        };
    }

    //getter e setter
    Cliente getCliente() {
        return cliente;
    }

    Long getIdCliente(){
        return cliente.getId();
    }

    void setStato(Stato stato) {
        this.stato=stato;
    }
    Stato getStato() {
        return this.stato;
    }

    String getId() {
        return id_ordine;
    }

    float calcolaTotale() {
        for(OrdineContiene ordine : prodottiContenuti){
            this.totale += (ordine.getQuantita()*ordine.getProdotto().getPrezzo());
        }
        return this.totale;
    }

    static Ordine elimina(Ordine ordine){
        for(OrdineContiene o : ordine.prodottiContenuti){
            o = null;
            //poi attendo che passi il garbage collector
        }
        return null;
    }

    public List<OrdineContiene> getProdottiContenuti() { // <-- LIST al posto di ArrayList
        return prodottiContenuti;
    }

    float getTotale() {
        return totale;
    }
    //localdatetime ritorna il timestamp
    LocalDateTime getDataConferma() {
        return dataConferma;
    }

    String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    //stub
    boolean PagaOrdine(long num_carta, int CCV, int meseScadenza, int annoScadenza, String id_ordine, float totale){
        return InterfacciaPagamento.PagaOrdine(num_carta, CCV, meseScadenza, annoScadenza, id_ordine, totale);
    }
}
