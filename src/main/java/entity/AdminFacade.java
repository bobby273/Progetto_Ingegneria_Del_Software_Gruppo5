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

    // Helper interno per evitare duplicazione di codice nel recupero del prodotto
    private static Prodotto recuperaProdotto(String nome) {
        GestorePersistenza gp = new GestorePersistenza();
        List<Prodotto> ris = gp.cercaPerCampo(Prodotto.class, "nome", nome);
        return ris.isEmpty() ? null : ris.get(0);
    }

    // --- 1. MODIFICA NOME (Caso speciale: cambia l'ID) ---
    public static boolean modificaNome(String nomeOriginale, String nuovoNome) {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto p = recuperaProdotto(nomeOriginale);
        if (p == null) return false;

        // Controlliamo che il nuovo nome non sia già occupato
        List<Prodotto> duplicato = gp.cercaPerCampo(Prodotto.class, "nome", nuovoNome);
        if (!duplicato.isEmpty()) return false;

        // Trattandosi della chiave primaria, cancelliamo il vecchio e creiamo il nuovo
        rimuoviProdotto(nomeOriginale);
        Prodotto pNuovo = new Prodotto(nuovoNome, p.getQtaDisponibile(), p.getDescrizione(), p.getPrezzo(), p.getCategoria(), p.isDisponibile(), p.isScontato());
        return gp.salva(pNuovo);
    }

    // --- 2. MODIFICA CATEGORIA ---
    public static boolean modificaCategoria(String nome, String nuovaCategoria) {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto p = recuperaProdotto(nome);
        if (p == null) return false;

        p.setCategoria(nuovaCategoria); // Cambia SOLO la categoria
        gp.aggiorna(p);
        return true;
    }

    // --- 3. MODIFICA PREZZO ---
    public static boolean modificaPrezzo(String nome, float nuovoPrezzo) {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto p = recuperaProdotto(nome);
        if (p == null) return false;

        p.setPrezzo(nuovoPrezzo); // Cambia SOLO il prezzo
        gp.aggiorna(p);
        return true;
    }

    // --- 4. MODIFICA DESCRIZIONE ---
    public static boolean modificaDescrizione(String nome, String nuovaDescrizione) {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto p = recuperaProdotto(nome);
        if (p == null) return false;

        p.setDescrizione(nuovaDescrizione); // Cambia SOLO la descrizione
        gp.aggiorna(p);
        return true;
    }

    // --- 5. MODIFICA QUANTITÀ ---
    public static boolean modificaQuantita(String nome, int nuovaQta) {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto p = recuperaProdotto(nome);
        if (p == null) return false;

        p.setQtaDisponibile(nuovaQta); // Cambia SOLO la quantità
        gp.aggiorna(p);
        return true;
    }

    // --- 6. MODIFICA DISPONIBILITÀ ---
    public static boolean modificaDisponibilita(String nome, boolean nuovaDisp) {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto p = recuperaProdotto(nome);
        if (p == null) return false;

        p.setDisponibile(nuovaDisp);
        gp.aggiorna(p);
        return true;
    }

    // --- 7. MODIFICA SCONTO ---
    public static boolean modificaSconto(String nome, boolean nuovoSconto) {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto p = recuperaProdotto(nome);
        if (p == null) return false;

        p.setScontato(nuovoSconto);
        gp.aggiorna(p);
        return true;
    }

    // --- CREA E RIMUOVI (Rimangono invariati e necessari) ---
    public static boolean creaProdotto(String nome, String categoria, double prezzo, String descrizione, int qta, boolean disponibile, boolean scontato) {
        GestorePersistenza gp = new GestorePersistenza();

        // 1. Verifica preventiva duplicati sul nome nel database
        List<Prodotto> esiste = gp.cercaPerCampo(Prodotto.class, "nome", nome);
        if (!esiste.isEmpty()) {
            System.out.println("FACADE ERRORE: Prodotto già esistente con questo nome.");
            return false;
        }

        // 2. La Facade si occupa di istanziare l'Entity nel cuore del modello!
        Prodotto nuovoProdotto = new Prodotto(nome, qta, descrizione, (float) prezzo, categoria, disponibile, scontato);

        // 3. Salva l'oggetto sul DB tramite il GestorePersistenza
        return gp.salva(nuovoProdotto);
    }
    public static boolean rimuoviProdotto(String nomeProdotto) {
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






}
