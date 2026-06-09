package entity;

//import StubPagamento.InterfacciaPagamento;
import StubPagamento.InterfacciaPagamento;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente extends Utente{
    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String indirizzoSpedizione;
    private byte immagineProfilo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ordine> ordiniPersonali;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Carrello carrello;


    //Costruttori
    public Cliente(){}

    Cliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        super(email, nome, cognome, password);
        this.indirizzoSpedizione=indirizzoSpedizione;
        this.ordiniPersonali=new ArrayList<Ordine>();
        this.carrello = new Carrello(email);
    }

    //Getter e setter
    Long getId(){
        return id;
    }
    Carrello getCarrello(){
        return carrello;
    }

    Ordine creaOrdine(String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza) {

        Ordine ordine = null;
        // costruisco l'ordine
        ordine = new Ordine(this, indirizzo, this.carrello);

        if(ordine == null){
            System.out.println("Crash nel costruttore Ordine");
            return null;
        }

        System.out.println("Costruttore Ordine superato con successo!");

        if (ordine.getProdottiContenuti() == null || ordine.getProdottiContenuti().isEmpty()) {
            System.out.println("Impossibile creare l'ordine!");
            Ordine.elimina(ordine);
            return null;
        }

        float totale = ordine.calcolaTotale();

        // 3. Tentativo di Pagamento
        boolean pagamentoEffettuato = ordine.PagaOrdine(num_carta, CCV, meseScadenza, annoScadenza, ordine.getId(), totale);

        if (pagamentoEffettuato) {
            System.out.println("Pagamento Approvato!");

            try {
                System.out.println("Tento di aggiungere agli ordini personali...");
                if (ordiniPersonali == null) {
                    ordiniPersonali = new ArrayList<>();
                }
                ordiniPersonali.add(ordine);

                System.out.println("Tento di aggiungere allo StoricoOrdini...");
                StoricoOrdini.getInstance().aggiungiOrdine(ordine);

                System.out.println("⏳ DEBUG: Tento di scalare le quantità dal catalogo...");
                for(OrdineContiene c : ordine.getProdottiContenuti()) {
                    Catalogo.getInstance().modificaQuantita(c.getProdotto().getNome(), (c.getProdotto().getQtaDisponibile() - c.getQuantita()));
                }

                System.out.println("Operazioni in Cliente completate! Restituisco l'ordine alla Facade.");
                return ordine;

            } catch (Exception e) {
                System.out.println("Problema: " + e.toString());
                e.printStackTrace();

                // Facciamo rollback fittizio per sicurezza
                Ordine.elimina(ordine);
                return null;
            }

        } else {
            System.out.println("ERRORE: Pagamento non approvato.");

            // Rollback: Rimettiamo i prodotti nel carrello dell'utente
            for(OrdineContiene c : ordine.getProdottiContenuti()) {
                carrello.aggiungiOAggiornaProdotto(c.getProdotto(), c.getQuantita());
            }

            Ordine.elimina(ordine);
            return null;
        }
    }


    boolean annullaOrdine(String id_ordine){
        Ordine ordine = cercaOrdine(id_ordine);
        if(ordine == null) return false;
        if(ordine.getStato()==Stato.CONSEGNATO
                || ordine.getStato()==Stato.SPEDITO
                || ordine.getStato()==Stato.ANNULLATO) return false;
        if(!InterfacciaPagamento.RimborsaOrdine(ordine))return false;
        ordine.setStato(Stato.ANNULLATO);
        StoricoOrdini.getInstance().inviaNotifiche();
        //Rimettiamo i prodotti nel catalogo
        Catalogo catalogo = Catalogo.getInstance();
        for(OrdineContiene c : ordine.getProdottiContenuti()) {
            Prodotto prodotto = c.getProdotto();
            int quantita = c.getQuantita();
            catalogo.modificaQuantita(prodotto.getNome(), prodotto.getQtaDisponibile() + quantita);
        }
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

    List<Ordine> visualizzaOrdiniPersonali() {

        //idea di base: invoco l'information expert StoricoOrdini e cerco gli ordini del cliente
        //successivamente, li passo ad ordiniPersonali, ovvero la lista visualizzabile dalla singola istanza di Cliente

        //parte di transferimento
        if (this.ordiniPersonali == null) {
            return new ArrayList<>();
        }
        return this.ordiniPersonali;
    }


    Object[] visualizzaInfoOrdine(String id_ordine) {
        // 1. Deleghiamo la ricerca allo StoricoOrdini globale
        Ordine ordineSelezionato = StoricoOrdini.getInstance().cercaOrdinePerId(id_ordine);

        // 2. Per Questioni di ulteriore sicurezza Verifichiamo che l'ordine esista e che sia mio
        if (ordineSelezionato == null || !ordineSelezionato.getCliente().getEmail().equals(this.getEmail())) {
            System.out.println("Errore: Ordine non trovato o non appartenente a questo cliente.");
            return null;
        }
        return ordineSelezionato.getInfoOrdine(); //per l'UML, posso tranquillamente accedere ad ordini dopo che mi sono assicurato che l'ordine sia personale
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
