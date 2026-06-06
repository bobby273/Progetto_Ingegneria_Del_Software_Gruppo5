package entity;

import java.util.List;

public class Cliente extends Utente{
    private String indirizzoSpedizione;
    private byte immagineProfilo;
    private List<Ordine> ordiniPersonali;

    boolean  annullaOrdine(Ordine ordine){
        if(ordine == null) return false;
        if(!ordiniPersonali.contains(ordine)) return false;
        ordine.setStato(Stato.ANNULLATO);
        return true;
    }

    public List<Ordine> visualizzaElencoOrdini() {

        //idea di base: invoco l'information expert StoricoOrdini e cerco gli ordini del cliente
        //successivamente, li passo ad ordiniPersonali, ovvero la lista visualizzabile dalla singola istanza di Cliente

        //parte di transferimento
        StoricoOrdini storico = StoricoOrdini.getInstance();
        List<Ordine> tuttiGliOrdini= storico.getOrdini();
        for (Ordine ordine : tuttiGliOrdini) {
            if(ordine.getCliente().getEmail().equals(this.email)){
                ordiniPersonali.add(ordine);
            };
        }
        //parte di stampa degli ordini
        System.out.println("Elenco ordini del cliente: "+this.nome+"\t"+this.cognome+"\n");
        System.out.println("----------------------------------------\n");
        if (ordiniPersonali.isEmpty()) {
            System.out.println("Non hai ancora effettuato alcun ordine");
        } else {
            for (Ordine o: ordiniPersonali){
                System.out.println("ID:"+o.id+"\t Costo totale:"+o.totale+ "\t Stato Ordine" +o.stato+"\n");
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

        // Stampiamo la "tabella" completa con tutti gli attributi della classe ordine
        System.out.println("==================================================");
        System.out.println("             DETTAGLIO COMPLETO ORDINE            ");
        System.out.println("==================================================");
        System.out.println("ID ORDINE:            " + ordineSelezionato.id);
        System.out.println("TOTALE PAGATO:        €" + ordineSelezionato.totale);
        System.out.println("OGGETTI ACQUISTATI" + ordineSelezionato.prodottiContenuti);
        System.out.println("STATO ATTUALE:        " + ordineSelezionato.stato);
        System.out.println("DATA CONFERMA:        " + ordineSelezionato.dataConferma);
        System.out.println("INDIRIZZO SPEDIZIONE: " + ordineSelezionato.indirizzoSpedizione);
        System.out.println("==================================================");

    }
}
