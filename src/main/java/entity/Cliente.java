package entity;

import Exceptions.ErroreDisponibilitaException;
//import StubPagamento.InterfacciaPagamento;
import StubPagamento.InterfacciaPagamento;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente extends Utente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String indirizzoSpedizione;
    private byte immagineProfilo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ordine> ordiniPersonali;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Carrello carrello;


    //Costruttori
    public Cliente(){}

    public Cliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        super(email, nome, cognome, password);
        this.indirizzoSpedizione=indirizzoSpedizione;
        this.ordiniPersonali=new ArrayList<Ordine>();
        this.carrello = new Carrello(email);
    }


    //Metodi
    void creaOrdine(String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        try{
            Ordine ordine = new Ordine(this, "aspe",this.carrello);
            float totale = ordine.calcolaTotale();
            boolean pagamentoEffettuato = ordine.PagaOrdine(num_carta,CCV,meseScadenza,annoScadenza, ordine.getId(), totale);
            if(pagamentoEffettuato) {
                ordiniPersonali.add(ordine);
                StoricoOrdini.getInstance().aggiungiOrdine(ordine);
                //in questo modo, l'ordine appena instanziato verrà aggiunto automaticamente allo Storico
            }else{
                System.out.println("Pagamento non approvato");
                Ordine.elimina(ordine);
            }
        } catch (ErroreDisponibilitaException e) {
            throw new ErroreDisponibilitaException("Ordine non valido, non ti è stato addebitato nulla");
        }
    }

    boolean annullaOrdine(String id_ordine){
        Ordine ordine = cercaOrdine(id_ordine);
        if(ordine == null) return false;
        if(ordine.getStato()==Stato.CONSEGNATO
                || ordine.getStato()==Stato.SPEDITO
                || ordine.getStato()==Stato.ANNULLATO) return false;
        ordine.setStato(Stato.ANNULLATO);
        InterfacciaPagamento.RimborsaOrdine(ordine);
        StoricoOrdini.getInstance().inviaNotifiche();
        return true;
    }

    private Ordine cercaOrdine(String id_ordine){
        Ordine cercato = null;
        for(Ordine ordine : ordiniPersonali){
            if(ordine.getId().equals(id_ordine)) {
                cercato = ordine;
                break;
            }
        }
        return cercato;
    }

    List<Ordine> visualizzaElencoOrdini() {

        //idea di base: invoco l'information expert StoricoOrdini e cerco gli ordini del cliente
        //successivamente, li passo ad ordiniPersonali, ovvero la lista visualizzabile dalla singola istanza di Cliente

        //parte di transferimento
        StoricoOrdini storico = StoricoOrdini.getInstance();
        List<Ordine> tuttiGliOrdini= storico.getOrdini();
        for (Ordine ordine : tuttiGliOrdini) {
            if(ordine.getCliente().getEmail().equals(this.getEmail())){
                ordiniPersonali.add(ordine);
            };
        }
        //parte di stampa degli ordini
        System.out.println("Elenco ordini del cliente: "+this.getNome()+"\t"+this.getCognome()+"\n");
        System.out.println("----------------------------------------\n");
        if (ordiniPersonali.isEmpty()) {
            System.out.println("Non hai ancora effettuato alcun ordine");
        } else {
            for (Ordine o: ordiniPersonali){
                System.out.println("ID:"+o.getId()+"\t Costo totale:"+o.getTotale()+ "\t Stato Ordine" +o.getStato()+"\n");
                //questa è la visualizzazione "ridotta" degli ordini: la visualizzazione di tutti gli attributi
                //dell'ordine avviene quando l'utente clicca sullo specifico ordine e avvia quindi
                // il metodo visualizzaInfoOrdine()
            }
        }
        System.out.println("----------------------------------------\n");
    return ordiniPersonali;
    }


    public void visualizzaInfoOrdine(Ordine ordineSelezionato) {
        if (ordineSelezionato == null) {
            System.out.println("Errore: Nessun ordine selezionato."); //tale if serve per evitare errori di NullPointerException
            //in caso di avvio del metodo da terminale
            return;
        }

        // Stampiamo la "tabella" completa con tutti gli attributi dell'ordine'
        System.out.println("==================================================");
        System.out.println("             DETTAGLIO COMPLETO ORDINE            ");
        System.out.println("==================================================");
        System.out.println("ID ORDINE:            " + ordineSelezionato.getId());
        System.out.println("TOTALE PAGATO:        €" + ordineSelezionato.getTotale());
        System.out.println("OGGETTI ACQUISTATI" + ordineSelezionato.getProdottiContenuti());
        System.out.println("STATO ATTUALE:        " + ordineSelezionato.getStato());
        System.out.println("DATA CONFERMA:        " + ordineSelezionato.getDataConferma());
        System.out.println("INDIRIZZO SPEDIZIONE: " + ordineSelezionato.getIndirizzoSpedizione());
        System.out.println("==================================================");

    }

    boolean aggiungiProdottoACarrello(Prodotto prodotto, int qtaDesiderata){
        boolean esito = false;
        if(this.carrello == null){
            System.out.println("Errore [CLIENTE] : il carrello non esiste");
        }
        else{
            esito = this.carrello.aggiungiOAggiornaProdotto(prodotto, qtaDesiderata);
        }
        return esito;
    }

    //Duale del metodo aggiungiProdottoACarrello
    boolean rimuoviProdottoDalCarrello(Prodotto prodotto){
        if(this.carrello == null){
            System.out.println("Errore [CLIENTE] : il carrello non esiste");
            return false;
        }
        else{
            return this.carrello.rimuoviProdotto(prodotto);
        }
    }

    //crea una copia del contenuto del carrello
    List<CarrelloContiene> getProdottiCarrello(){
        if(this.carrello != null){
            return this.carrello.getProdottiContenuti(); //ho così ottenuto i prodotti interni al carrello, non il carrello stesso
        }
        return new ArrayList<>();
    }


}
