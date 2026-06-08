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
        System.out.println("🔍 [CLIENTE] Entrato nel metodo creaOrdine interno!");
        System.out.println("🔍 [CLIENTE] Stato carrello attuale: " + (this.carrello == null ? "ASSENTE (NULL) ❌" : "PRESENTE ✅"));

        Ordine ordine = null;
        try {
            // 1. IL PUNTO CRITICO: Proviamo a costruire l'ordine
            System.out.println("🔍 [CLIENTE] Tento di lanciare new Ordine()...");
            ordine = new Ordine(this, indirizzo, this.carrello);
            System.out.println("✅ [CLIENTE] Costruttore Ordine superato con successo!");

        } catch (Exception e) {
            System.out.println("❌ CRASH FATALE DENTRO IL COSTRUTTORE DI ORDINE! ❌");
            System.out.println("Motivo dell'errore: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        // 2. Controllo sicurezza carrello vuoto
        if (ordine.getProdottiContenuti() == null || ordine.getProdottiContenuti().isEmpty()) {
            System.out.println("❌ ERRORE: Impossibile creare l'ordine, il carrello è vuoto!");
            Ordine.elimina(ordine);
            return null;
        }

        float totale = ordine.calcolaTotale();
        String idTemporaneoPerStub = "TEMP_ID_" + System.currentTimeMillis();

        // 3. Tentativo di Pagamento
        boolean pagamentoEffettuato = ordine.PagaOrdine(num_carta, CCV, meseScadenza, annoScadenza, idTemporaneoPerStub, totale);

        // ==========================================
        // GESTIONE ESITO PAGAMENTO E TRAPPOLA CRASH
        // ==========================================
        if (pagamentoEffettuato) {
            System.out.println("✅ Pagamento Approvato!");

            try {
                System.out.println("⏳ DEBUG: Tento di aggiungere agli ordini personali...");
                // FIX PREVENTIVO: Se Hibernate non ha inizializzato la lista perché vuota, la creiamo noi!
                if (ordiniPersonali == null) {
                    ordiniPersonali = new ArrayList<>();
                }
                ordiniPersonali.add(ordine);

                System.out.println("⏳ DEBUG: Tento di aggiungere allo StoricoOrdini...");
                StoricoOrdini.getInstance().aggiungiOrdine(ordine);

                System.out.println("⏳ DEBUG: Tento di scalare le quantità dal catalogo...");
                for(OrdineContiene c : ordine.getProdottiContenuti()) {
                    Catalogo.getInstance().modificaQuantita(c.getProdotto().getNome(), (c.getProdotto().getQtaDisponibile() - c.getQuantita()));
                }

                System.out.println("✅ DEBUG: Operazioni in Cliente completate! Restituisco l'ordine alla Facade.");
                return ordine;

            } catch (Exception e) {
                System.out.println("❌ CRASH FATALE DURANTE LE OPERAZIONI POST-PAGAMENTO!");
                System.out.println("Motivo: " + e.toString());
                e.printStackTrace();

                // Facciamo rollback fittizio per sicurezza
                Ordine.elimina(ordine);
                return null;
            }

        } else {
            System.out.println("❌ ERRORE: Pagamento non approvato.");

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
        List<Ordine> ordiniFiltrati = new ArrayList<>();
        StoricoOrdini storico = StoricoOrdini.getInstance();
        List<Ordine> tuttiGliOrdini= storico.getOrdini();

        if(tuttiGliOrdini != null){
            for(Ordine ordine : tuttiGliOrdini){
                if (ordine.getCliente() != null && ordine.getCliente().getEmail().equals(this.getEmail())){
                    ordiniFiltrati.add(ordine);
                }
            }
        }
        return ordiniFiltrati;
    }


    void visualizzaInfoOrdine(Ordine ordineSelezionato) {
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
