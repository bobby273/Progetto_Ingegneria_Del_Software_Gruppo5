package entity;

import database.GestorePersistenza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminFacade {

    private final GestorePersistenza gp = new GestorePersistenza();
    private String mailAmministratore;

    public AdminFacade(String mailUser){
        this.mailAmministratore = mailUser;
    }

    public boolean annullaOrdine(String id_ordine){
        Amministratore amministratore = gp.cercaPrimoPerCampi(Amministratore.class, Map.of("email", mailAmministratore));
        boolean annullato = amministratore.annullaOrdine(id_ordine);
        Ordine aggiorna=null;
        if(annullato){
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            aggiorna = gp.aggiorna(o);
        }
        return (aggiorna !=null);
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
                    String.valueOf(p.getQtaDisponibile()), // index 4
                    String.valueOf(p.isDisponibile()),            // Index 5 <-- AGGIUNTO REALE DAL DB
                    String.valueOf(p.isScontato())
            };
            listaDatiGrezzi.add(datiProdotto);
        }

        return listaDatiGrezzi;
    }

    public List<Prodotto> getTuttiIProdotti(){
        return Catalogo.getInstance().getTuttiIProdotti();
    }

    //proviamo a filtrare direttamente nella AdminFacade.
    public static List<String[]> ricercaProdottoInCatalogo(String categoriaRicerca,String elementoDaCercare) {
        List<Prodotto> prodottiTrovati;

        if (elementoDaCercare == null || elementoDaCercare.trim().isEmpty()) {
            prodottiTrovati = Catalogo.getInstance().getTuttiIProdotti(); //se non compilo la ricerca, ottengo indietro il catalogo intero
        } else {
            prodottiTrovati = Catalogo.getInstance().ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
        }

        List<String[]> risultati = new ArrayList<>();
        if (prodottiTrovati != null) {
            for (Prodotto p : prodottiTrovati) {
                risultati.add(new String[]{
                        p.getNome(),
                        p.getCategoria(),
                        String.valueOf(p.getPrezzo()),
                        p.getDescrizione(),
                        String.valueOf(p.getQtaDisponibile()),
                        String.valueOf(p.isDisponibile()),
                        String.valueOf(p.IsScontato())
                });
            }
        } return risultati;
    }

    public Long getIdClienteDaIdOrdine(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getIdCliente();
    }

    public String getStato(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getStato().toString();
    }

    public float getTotale(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getTotale();
    }

    public LocalDateTime getDataConferma(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getDataConferma();
    }

    public String getIndirizzoSpedizione(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        return o.getIndirizzoSpedizione();
    }

    public ArrayList<String> getProdottoEQuantita(String id_ordine){
        Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
        ArrayList<OrdineContiene> pc=o.getProdottiContenuti();
        ArrayList<String> prodotti = new  ArrayList<>();
        for(OrdineContiene c:pc){
            prodotti.add(c.getProdotto().getNome());
            prodotti.add(String.valueOf(c.getQuantita()));
        }

        return prodotti;
    }

}







