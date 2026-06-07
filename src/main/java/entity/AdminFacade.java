package entity;

import database.GestorePersistenza;
import database.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AdminFacade {

    public boolean annullaOrdine(Amministratore amministratore, String id_ordine){
        return true ;
        //amministratore.annullaOrdine(id_ordine); TODO:scommentare, il true è un placeholder
    }

    public static boolean creaProdotto(String nome, String categoria, double prezzo, String descrizione, int qta, boolean disponibile, boolean scontato) {
        return Catalogo.getInstance().creaEAggiungiProdotto(nome, categoria, prezzo, descrizione, qta, disponibile, scontato);
    }

    public static boolean modificaNome(String nomeOriginale, String nuovoNome) {
        return Catalogo.getInstance().modificaNome(nomeOriginale, nuovoNome);
    }

    public static boolean modificaCategoria(String nome, String nuovaCategoria) {
        return Catalogo.getInstance().modificaCategoria(nome, nuovaCategoria);
    }

    public static boolean modificaPrezzo(String nome, float nuovoPrezzo) {
        return Catalogo.getInstance().modificaPrezzo(nome, nuovoPrezzo);
    }

    public static boolean modificaDescrizione(String nome, String nuovaDescrizione) {
        return Catalogo.getInstance().modificaDescrizione(nome, nuovaDescrizione);
    }

    public static boolean modificaQuantita(String nome, int nuovaQta) {
        return Catalogo.getInstance().modificaQuantita(nome, nuovaQta);
    }

    public static boolean modificaDisponibilita(String nome, boolean nuovaDisp) {
        return Catalogo.getInstance().modificaDisponibilita(nome, nuovaDisp);
    }

    public static boolean modificaSconto(String nome, boolean nuovoSconto) {
        return Catalogo.getInstance().modificaSconto(nome, nuovoSconto);
    }

    public static boolean rimuoviProdotto(String nomeProdotto) {
        return Catalogo.getInstance().rimuoviProdotto(nomeProdotto);
    }

    public static java.util.List<String[]> getListaProdottiPerScorrimento() {
        // 1. Il catalogo estrae le entità (siamo nello stesso package, quindi funziona!)
        java.util.List<Prodotto> veriProdotti = Catalogo.getInstance().getTuttiIProdotti();
        java.util.List<String[]> listaDatiGrezzi = new java.util.ArrayList<>();

        // 2. Trasformiamo ogni entità in un array di Stringhe leggibile dall'esterno
        for (Prodotto p : veriProdotti) {
            String[] datiProdotto = new String[] {
                    p.getNome(),            // index 0
                    p.getCategoria(),       // index 1
                    String.format("%.2f", p.getPrezzo()), // index 2
                    p.getDescrizione(),     // index 3
                    String.valueOf(p.getQtaDisponibile()) // index 4
            };
            listaDatiGrezzi.add(datiProdotto);
        }

        return listaDatiGrezzi;
    }
    }







