package entity;

import java.util.ArrayList;
import java.util.List;

public class StoricoOrdini {
    private static StoricoOrdini instance; //StoricoOrdini si comporta come singleton per tutto il BCED,
    //ma verrà usato in maniera diversa da utente e cliente

    private List<Ordine> ordini;

    private StoricoOrdini(){
        this.ordini = new ArrayList<>();
    }

    public static StoricoOrdini getInstance() {
        if (instance == null) {
            instance = new StoricoOrdini();
        }
        return instance;
    }


    // scriviamo ora i modi per aggiungere e rimuovere ordini nella nostra lista
    //aggiungiOrdini servirà per ogni instanza di Ordine, mentre rimuovere verrà chiamata da annullaOrdine()

    public void aggiungiOrdine(Ordine ordineDaAggiungere){
        if (ordineDaAggiungere != null && this.ordini.contains(ordineDaAggiungere)) {
            this.ordini.add(ordineDaAggiungere);
        } else {
            System.out.println("Errore: hai provato ad aggiungere un ordine vuoto");
        }
    }

    public void rimuoviOrdine(Ordine ordineDaRimuovere){
        if (ordineDaRimuovere != null && this.ordini.contains(ordineDaRimuovere)) {
            this.ordini.remove(ordineDaRimuovere);
        } else {
            System.out.println("Errore: hai provato ad rimuovere un ordine non presente");
        }
    }

    public void stampaOrdiniRicevuti(){
        if (this.ordini.isEmpty()) {
            System.out.println("------ ELENCO ORDINI RICEVUTI ------\n");
            System.out.println("La lista è vuota: il sistema non ha ricevuto alcun ordine");
        } else {
            for (Ordine o: ordini){
                System.out.println("ID:"+o.id+"\t Costo totale:"+o.totale+ "\t Data Conferma Ordine:" +o.dataConferma+ "\t Indirizzo Spedizione:"+o.indirizzoSpedizione+"\n");
                //unico dubbio:quando il cliente userà questo metodo per visualizzare i propri ordini,
                //cosa mostrerà data conferma ordine? conviene inserire lì il tasto di conferma?
            }
        }

    }
    public void generaReport(){
        System.out.println("Report generato"); //stub generico che funziona da placeholder della vera funzione
    }

    public void inviaNotifiche(){
        System.out.println("Notifiche inviate"); //stub generico che funziona da placeholder della vera funzione
    }
}
