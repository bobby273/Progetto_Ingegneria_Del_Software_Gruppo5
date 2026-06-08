package entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import StubPagamento.InterfacciaPagamento;
import jakarta.persistence.*;

//import StubPagamento.InterfacciaPagamento;

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
        if(prodottiInCarrello.isEmpty()) return null;
        List<OrdineContiene> prodottiInOrdine = new ArrayList<>();
        List<CarrelloContiene> daRimuovereDalCarrello = new ArrayList<>();
        for (CarrelloContiene carrelloContiene : prodottiInCarrello) {
            if (!(carrelloContiene.getQuantita() > carrelloContiene.getProdotto().getQtaDisponibile())) {
                prodottiInOrdine.add(new OrdineContiene(carrelloContiene.getProdotto(),carrelloContiene.getQuantita(), this));
                daRimuovereDalCarrello.add(carrelloContiene);
            } else {
                System.out.println("quantità desiderata del prodotto "+carrelloContiene.getProdotto().getNome()+" non disponibile");
            }
        }
        //rimuovo i prodotti dal carrello
        for (CarrelloContiene prodotto : daRimuovereDalCarrello) {
            carrello.rimuoviProdotto(prodotto.getProdotto());
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
                //System.out.print(id[i]);
            }
            idOrdine = Arrays.stream(id).mapToObj(String::valueOf).collect(Collectors.joining());
            if(StoricoOrdini.getInstance().cercaOrdinePerId(idOrdine)==null){
                esiste=false;
            }
        }
        return idOrdine;
    }

    /*
    public static void main(String[] args){
        Cliente io = new Cliente("aaaaaaaaaa@gmail.com","Robertina","Giovengo","aaaaaaa12345670","Via Antonio Segni");
        Ordine ordine = new Ordine(io,"Via Antonio Segni",new Carrello("aaaaaaaaaa@gmail.com"));
        System.out.println(ordine.getId());
    }

     */

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

    List<OrdineContiene> getProdottiContenuti() {
        return prodottiContenuti;
    }

    float getTotale() {
        return totale;
    }

    LocalDateTime getDataConferma() {
        return dataConferma;
    }

    String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    //stub
    boolean PagaOrdine(String num_carta, int CCV, int meseScadenza, int annoScadenza, String id_ordine, float totale){
        return InterfacciaPagamento.PagaOrdine(num_carta, CCV, meseScadenza, annoScadenza, id_ordine, totale);
    }
}
