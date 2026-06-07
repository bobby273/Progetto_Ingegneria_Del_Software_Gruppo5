package entity;

//import StubPagamento.InterfacciaPagamento;
import database.GestorePersistenza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientFacade {

    //Attributi
    private final GestorePersistenza gp = new GestorePersistenza();
    private String mailUtente;


    //Costruttori
    public ClientFacade(String mailUtente){
        this.mailUtente=mailUtente;
    }

    public ClientFacade(){}

    //Metodi
    public boolean annullaOrdine(String id_ordine) {
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
        boolean annullato = cliente.annullaOrdine(id_ordine);
        Ordine aggiorna=null;
        if(annullato){
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            aggiorna = gp.aggiorna(o);
        }
        return (aggiorna!=null);
    }

    public boolean creaOrdine(String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
        Ordine o = cliente.creaOrdine(indirizzo,num_carta,CCV,meseScadenza,annoScadenza);
        gp.salva(o);
        return o==null;
    }


    //questo metodo non deve essere implementato qui ma in carrello [vedi GitHub]
    public boolean aggiungiOAggiornaProdottoACarrello(String mailUtente, String nomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        //Delego ricerca del prodotto all'informatione expert [catalogo]
        Prodotto prodotto = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));

        if(prodotto != null && cliente != null){

            boolean esitoAggiunta = cliente.aggiungiProdottoACarrello(prodotto, qtaDesiderata);

            if(esitoAggiunta){
                gp.aggiorna(cliente);
                return true;
            }
        }
        return false;
    }
    //Manuel: per rimuovere prodotti dal carrello
    public boolean rimuoviProdottoDalCarrello(String mailUtente, String nomeProdotto){
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
        if(cliente != null){
            Prodotto prodotto = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

            boolean esitoRimozione = cliente.rimuoviProdottoDalCarrello(prodotto);

            if(esitoRimozione){
                gp.aggiorna(cliente);
                return true;
            }
        }
        return false;
    }

    //Metodi di spacchettamento per garantire isolamento di prodotto
    public List<String[]> getCatalogoBreve() {
        List<Prodotto> prodotti = Catalogo.getInstance().getTuttiIProdotti();
        List<String[]> catalogoBreve = new ArrayList<>();

        if (prodotti != null) {
            for (Prodotto p : prodotti) {
                catalogoBreve.add(new String[]{p.getNome(), p.getDescrizione()});
            }
        }
        return catalogoBreve;
    }

    public List<String[]> getCarrelloBreve(String mailUtente) {
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
        List<String[]> carrelloBreve = new ArrayList<>();

        if (cliente != null && cliente.getProdottiCarrello() != null) {
            for (CarrelloContiene item : cliente.getProdottiCarrello()) {
                Prodotto p = item.getProdotto();
                carrelloBreve.add(new String[]{
                        p.getNome(),
                        p.getDescrizione(),
                        String.valueOf(p.getPrezzo()),
                        p.getCategoria(),
                        String.valueOf(item.getQuantita())
                });
            }
        }
        return carrelloBreve;
    }

        public Object[] getDettagliProdotto (String nomeProdotto){
            Prodotto p = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

            if (p != null) {
                return new Object[]{
                        p.getNome(),
                        p.getPrezzo(),
                        p.getCategoria(),
                        p.getQtaDisponibile(),
                        p.IsScontato(),
                        p.getDescrizione()
                };
            }
            return null;
        }

    public boolean esisteProdotto(String nomeProdotto) {
        return Catalogo.getInstance().ricercaProdotto(nomeProdotto) != null;
    }


    //proviamo a filtrare direttamente nella clientFacade.
    public List<String[]> ricercaProdottoInCatalogo(String categoriaRicerca,String elementoDaCercare) {
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
                        p.getDescrizione(),
                        String.valueOf(p.getPrezzo()),
                        p.getDescrizione(),
                        String.valueOf(p.getQtaDisponibile()),
                        String.valueOf(p.IsScontato())
                });
            }
        } return risultati;
    }

    public Prodotto ricercaProdotto(String nomeProdotto) { //TODO: vedi se unirlo all'altro metodo
        return Catalogo.getInstance().ricercaProdotto(nomeProdotto);
    }

    /*TRY: probabilmente va eliminato
    public List<Prodotto> ricercaProdottoInCatalogo(String categoriaRicerca, String elementoDaCercare) {
        return Catalogo.getInstance().ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
    }*/

    //Manuel: per ottenere prodotti da visualizzare in catalogo
    public List<Prodotto> getTuttiIProdotti(){
        return Catalogo.getInstance().getTuttiIProdotti();
    }

    //Manuel: per ottenere prodotti da visualizzare nel carrello
    public List<CarrelloContiene> getProdottiNelCarrello(String mailUtente){
        Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
        if(cliente != null){
            return cliente.getProdottiCarrello();
        }
        return new ArrayList<>();
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

    // Nella ClientFacade: Metodo per la ricerca che restituisce i dati pronti per la GUI
    public List<String[]> ricercaProdottoInCatalogoBreve(String categoriaRicerca, String elementoDaCercare) {
        List<Prodotto> prodottiTrovati;

        // Se la ricerca è vuota, restituiamo tutto il catalogo
        if (elementoDaCercare == null || elementoDaCercare.trim().isEmpty()) {
            prodottiTrovati = Catalogo.getInstance().getTuttiIProdotti();
        } else {
            // Altrimenti deleghiamo al catalogo la ricerca specifica
            prodottiTrovati = Catalogo.getInstance().ricercaProdottoInCatalogo(categoriaRicerca, elementoDaCercare);
        }

        // Spacchettiamo i risultati per la Boundary
        List<String[]> risultatiBrevi = new ArrayList<>();
        if (prodottiTrovati != null) {
            for (Prodotto p : prodottiTrovati) {
                risultatiBrevi.add(new String[]{p.getNome(), p.getDescrizione()});
            }
        }
        return risultatiBrevi;
    }

}

