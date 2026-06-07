package entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

import StubPagamento.InterfacciaPagamento;
import jakarta.persistence.*;

import Exceptions.ErroreDisponibilitaException;
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
    private ArrayList<OrdineContiene> prodottiContenuti;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cliente cliente;

    Ordine(Cliente cliente, String indirizzoSpedizione, Carrello carrello){
        this.cliente = cliente;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.id_ordine = generaID();
        this.totale = 0;
        this.prodottiContenuti = new ArrayList<>();
        this.prodottiContenuti = aggiungiDaCarrello(carrello);
    }

    public Ordine() {

    }

    private ArrayList<OrdineContiene>  aggiungiDaCarrello(Carrello carrello){
        List<CarrelloContiene> prodottiInCarrello = carrello.getProdottiContenuti();
        if(prodottiInCarrello.isEmpty()) return null;
        ArrayList<OrdineContiene> prodottiInOrdine = new ArrayList<>();
        for (CarrelloContiene carrelloContiene : prodottiInCarrello) {
            if (!(carrelloContiene.getQuantita() > carrelloContiene.getProdotto().getQtaDisponibile())) {
                prodottiInOrdine.add(new OrdineContiene(carrelloContiene.getProdotto(),carrelloContiene.getQuantita(), this));
            } else {
                System.out.println("quantità desiderata del prodotto "+carrelloContiene.getProdotto().getNome()+" non disponibile");
            }
        }
        return prodottiInOrdine;
    }


    private String generaID(){
        boolean esiste=true;
        char[] id= new char[10];
        java.util.Random random = new java.util.Random();
        while(esiste){
            random.setSeed(System.currentTimeMillis());
            for(int i=0; i<10; i++) {
                id[i] = (char) random.nextInt(10);
            }
            if(StoricoOrdini.getInstance().cercaOrdinePerId(String.valueOf(id))==null){
                esiste=false;
            }
        }
        return String.valueOf(id);
    }

    public Cliente getCliente() {
        return cliente;
    }

    Long getIdCliente(){
        return cliente.getId();
    }

    public void setStato(Stato stato) {
        this.stato=stato;
    }
    public Stato getStato() {
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

    public ArrayList<OrdineContiene> getProdottiContenuti() {
        return prodottiContenuti;
    }

    public float getTotale() {
        return totale;
    }

    public LocalDateTime getDataConferma() {
        return dataConferma;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    //stub
    public boolean PagaOrdine(String num_carta, int CCV, int meseScadenza, int annoScadenza, String id_ordine, float totale){
        return InterfacciaPagamento.PagaOrdine(num_carta, CCV, meseScadenza, annoScadenza, id_ordine, totale);
    }
}
