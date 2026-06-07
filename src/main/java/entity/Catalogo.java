package entity;

import database.GestorePersistenza;
import database.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class Catalogo {

    public static Catalogo instance; //SINGLETON

    private GestorePersistenza gp;

    //costruttore privato
    private Catalogo() {
        gp = new GestorePersistenza();
    }

    static Catalogo getInstance() {
        if(instance == null) {
            instance = new Catalogo(); //Non sono sicuro che vada messo, controlliamo!
        }
        return instance;

    } //TODO: l'ho creato perchè mi serviva per accedere ai prodotti, va completato!

    //Metodi

    List<Prodotto> getTuttiIProdotti() {
        database.GestorePersistenza gp = new database.GestorePersistenza();
        // Passando una mappa vuota, il tuo GestorePersistenza estrae l'intera tabella da MySQL
        return gp.cercaPerCampi(Prodotto.class, java.util.Map.of());
    }

    Prodotto ricercaProdotto(String nomeProdotto) {
        try{
            return gp.cercaPrimoPerCampi(Prodotto.class, Map.of("nome", nomeProdotto));
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("[CATALOGO] Errore nella ricerca del prodotto");
            return null; //Prodtto non trovato
        }
    }

    // RETRIEVAL: Il catalogo sa come cercare un prodotto
    Prodotto cercaProdotto(String nome) {
        GestorePersistenza gp = new GestorePersistenza();
        List<Prodotto> ris = gp.cercaPerCampo(Prodotto.class, "nome", nome);
        return ris.isEmpty() ? null : ris.get(0);
    }

    // CREAZIONE: Il catalogo decide se un prodotto può essere aggiunto
    boolean aggiungiProdotto(Prodotto nuovo) {
        // Controllo di esistenza delegato all'Expert stesso
        if (cercaProdotto(nuovo.getNome()) != null) {
            System.out.println("CATALOGO: Impossibile aggiungere, prodotto già esistente.");
            return false;
        }
        GestorePersistenza gp = new GestorePersistenza();
        return gp.salva(nuovo);
    }

    // --- INFORMATION EXPERT: RIMOZIONE (Spostata qui dal gestore/facade) ---
    boolean rimuoviProdotto(String nomeProdotto) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Prodotto p = em.find(Prodotto.class, nomeProdotto);
            if (p != null) {
                em.remove(p);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    boolean modificaNome(String nomeOriginale, String nuovoNome) {
        Prodotto p = cercaProdotto(nomeOriginale);
        if (p == null || cercaProdotto(nuovoNome) != null) return false;

        rimuoviProdotto(nomeOriginale);
        return creaEAggiungiProdotto(nuovoNome, p.getCategoria(), p.getPrezzo(), p.getDescrizione(), p.getQtaDisponibile(), p.isDisponibile(), p.isScontato());
    }

    boolean modificaCategoria(String nome, String nuovaCat) {
        Prodotto p = cercaProdotto(nome);
        if (p == null) return false;
        p.setCategoria(nuovaCat);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaPrezzo(String nome, float nuovoPrezzo) {
        Prodotto p = cercaProdotto(nome);
        if (p == null) return false;
        p.setPrezzo(nuovoPrezzo);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaQuantita(String nome, int nuovaQta) {
        Prodotto p = cercaProdotto(nome);
        if (p == null) return false;
        p.setQtaDisponibile(nuovaQta);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaDisponibilita(String nome, boolean nuovaDisp) {
        Prodotto p = cercaProdotto(nome);
        if (p == null) return false;
        p.setDisponibile(nuovaDisp);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaSconto(String nome, boolean nuovoSconto) {
        Prodotto p = cercaProdotto(nome);
        if (p == null) return false;
        p.setScontato(nuovoSconto);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaDescrizione(String nome, String nuovaDesc) {
        Prodotto p = cercaProdotto(nome);
        if (p == null) return false;
        p.setDescrizione(nuovaDesc);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean creaEAggiungiProdotto(String nome, String categoria, double prezzo, String descrizione, int qta, boolean disponibile, boolean scontato) {
        if (cercaProdotto(nome) != null) {
            System.out.println("CATALOGO: Prodotto già esistente, creazione annullata.");
            return false;
        }

        // L'istanziazione avviene qui dentro!
        Prodotto nuovoProdotto = new Prodotto(nome, qta, descrizione, (float) prezzo, categoria, disponibile, scontato);

        GestorePersistenza gp = new GestorePersistenza();
        return gp.salva(nuovoProdotto);
    }

}
