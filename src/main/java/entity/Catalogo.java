package entity;

import database.GestorePersistenza;
import database.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Catalogo {

    static Catalogo instance; //SINGLETON

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

    }

    //Metodi

    List<Prodotto> getTuttiIProdotti() {
        if (gp == null) {
            gp = new database.GestorePersistenza();
        }

        // IL FILTRO MAGICO: Diciamo a Hibernate di prendere SOLO quelli con isEliminato a false!
        return gp.cercaPerCampi(Prodotto.class, java.util.Map.of("isEliminato", false));
    }
    Prodotto ricercaProdotto(String nomeProdotto) {
        try{
            return gp.cercaPrimoPerCampi(Prodotto.class, Map.of("nome", nomeProdotto));
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("[CATALOGO] Errore nella ricerca del prodotto");
            return null; //Prodotto non trovato
        }
    }

    // RETRIEVAL: Il catalogo sa come cercare un prodotto
    /* TODO: Da eliminare
    Prodotto ricercaProdotto(String nome) {
        gp = new GestorePersistenza();
        List<Prodotto> ris = gp.cercaPerCampo(Prodotto.class, "nome", nome);
        return ris.isEmpty() ? null : ris.get(0);
    }*/

    List<Prodotto> ricercaProdottoInCatalogo(String categoriaRicerca, String elementoDaCercare) {


        final List<String> CAMPI_RICERCA_AMMESSI = Arrays.asList("nome", "categoria", "descrizione");
        // Controllo di sicurezza per evitare che utenti da terminale possano cercare su campi non ammessi.
        if (!CAMPI_RICERCA_AMMESSI.contains(categoriaRicerca)) {
            // Se il campo non è tra quelli ammessi, lanciamo un'eccezione o restituiamo null/lista vuota
            throw new IllegalArgumentException("Campo di ricerca non valido: " + categoriaRicerca);
        }
        return gp.cercaPerCampoLike(Prodotto.class, categoriaRicerca, elementoDaCercare);
    }

    // CREAZIONE: Il catalogo decide se un prodotto può essere aggiunto
    /*TODO: Va rimosso?
    boolean aggiungiProdotto(Prodotto nuovo) {
        // Controllo di esistenza delegato allExpert stesso
        if (ricercaProdotto(nuovo.getNome()) != null) {
            System.out.println("CATALOGO: Impossibile aggiungere, prodotto già esistente.");
            return false;
        }
        gp = new GestorePersistenza();
        return gp.salva(nuovo);
    }*/

    // --- INFORMATION EXPERT: RIMOZIONE (spostata qui dal gestore/facade) ---
    boolean rimuoviProdotto(String nomeProdotto) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            // 1. Troviamo il prodotto nel database
            Prodotto p = em.find(Prodotto.class, nomeProdotto);

            if (p != null) {
                // 2. IL SOFT DELETE: Sostituiamo la cancellazione fisica con la nostra etichetta!
                // Al posto di em.remove(p), cambiamo solo lo stato.
                p.setEliminato(true);

                // 3. Salviamo. JPA capisce da solo che 'p' è cambiato e lancerà una query UPDATE
                em.getTransaction().commit();
                System.out.println("✅ [CATALOGO] Prodotto '" + nomeProdotto + "' nascosto con successo (Soft Delete).");
                return true;
            }
            em.getTransaction().commit();
            return false;
        } catch (RuntimeException e) {
            System.out.println("❌ [CATALOGO] Errore durante il Soft Delete: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }
    boolean modificaNome(String nomeOriginale, String nuovoNome) {
        Prodotto p = ricercaProdotto(nomeOriginale);
        if (p == null || ricercaProdotto(nuovoNome) != null) return false;

        rimuoviProdotto(nomeOriginale);
        return creaEAggiungiProdotto(nuovoNome, p.getCategoria(), p.getPrezzo(), p.getDescrizione(), p.getQtaDisponibile(), p.isDisponibile(), p.isScontato());
    }

    boolean modificaCategoria(String nome, String nuovaCat) {
        Prodotto p = ricercaProdotto(nome);
        if (p == null) return false;
        p.setCategoria(nuovaCat);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaPrezzo(String nome, float nuovoPrezzo) {
        Prodotto p = ricercaProdotto(nome);
        if (p == null) return false;
        p.setPrezzo(nuovoPrezzo);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaQuantita(String nome, int nuovaQta) {
        Prodotto p = ricercaProdotto(nome);
        if (p == null) return false;
        p.setQtaDisponibile(nuovaQta);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaDisponibilita(String nome, boolean nuovaDisp) {
        Prodotto p = ricercaProdotto(nome);
        if (p == null) return false;
        p.setDisponibile(nuovaDisp);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaSconto(String nome, boolean nuovoSconto) {
        Prodotto p = ricercaProdotto(nome);
        if (p == null) return false;
        p.setScontato(nuovoSconto);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean modificaDescrizione(String nome, String nuovaDesc) {
        Prodotto p = ricercaProdotto(nome);
        if (p == null) return false;
        p.setDescrizione(nuovaDesc);
        new GestorePersistenza().aggiorna(p);
        return true;
    }

    boolean creaEAggiungiProdotto(String nome, String categoria, double prezzo, String descrizione, int qta, boolean disponibile, boolean scontato) {
        if (ricercaProdotto(nome) != null) {
            System.out.println("CATALOGO: Prodotto già esistente, creazione annullata.");
            return false;
        }

        // L'istanziazione avviene qui dentro!
        Prodotto nuovoProdotto = new Prodotto(nome, qta, descrizione, (float) prezzo, categoria, disponibile, scontato);

        GestorePersistenza gp = new GestorePersistenza();
        return gp.salva(nuovoProdotto);
    }

}
