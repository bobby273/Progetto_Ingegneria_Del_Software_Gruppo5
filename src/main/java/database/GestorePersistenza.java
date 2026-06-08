package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GestorePersistenza {

    public boolean salva(Object oggetto) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(oggetto);
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

    public boolean salvaTutti(Object... oggetti) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            for (Object oggetto : oggetti) {
                em.persist(oggetto);
            }
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public <T> T trovaPerId(Class<T> classe, Long id) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            return em.find(classe, id);
        } finally {
            em.close();
        }
    }

    public <T> List<T> cercaPerCampo(Class<T> classe, String nomeCampo, Object valore) {
        return cercaPerCampi(
                classe,
                Map.of(nomeCampo, valore)
        );
    }

    public <T> List<T> cercaPerCampi(Class<T> classe, Map<String, Object> campi) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT e FROM ").append(classe.getSimpleName()).append(" e");
            if (!campi.isEmpty()) {
                jpql.append(" WHERE ");
                int contatore = 0;
                for (String nomeCampo : campi.keySet()) {
                    if (contatore > 0) {
                        jpql.append(" AND ");
                    }
                    String nomeParametro = nomeCampo.replace(".", "_");
                    jpql.append("e.").append(nomeCampo).append(" = :").append(nomeParametro);
                    contatore++;
                }
            }
            TypedQuery<T> query = em.createQuery(
                    jpql.toString(),
                    classe
            );
            for (String nomeCampo : campi.keySet()) {
                String nomeParametro = nomeCampo.replace(".", "_");
                query.setParameter(nomeParametro, campi.get(nomeCampo));
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    //modifica del metodo presente nel file professore e adattato al caso della ricerca di un prodotto
    //(ovvero una ricerca che potrebbe non necessitare di case sensitive e potrebbe voler cercare per sottostringhe
    public <T> List<T> cercaPerCampoLike(Class<T> classe, String nomeCampo, Object valore) {
        return cercaPerCampiLike(
                classe,
                Map.of(nomeCampo, valore)
        );
    }

    public <T> List<T> cercaPerCampiLike(Class<T> classe, Map<String, Object> campi) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT e FROM ").append(classe.getSimpleName()).append(" e");
            if (!campi.isEmpty()) {
                jpql.append(" WHERE ");
                int contatore = 0;
                for (String nomeCampo : campi.keySet()) {
                    if (contatore > 0) {
                        jpql.append(" AND ");
                    }
                    String nomeParametro = nomeCampo.replace(".", "_");
                    Object valore = campi.get(nomeCampo);

                    if (valore instanceof String) { //assicuriamoci se è una stringa e se ciò è verificato ignoriamo il case sensitive
                        jpql.append("UPPER(e.").append(nomeCampo).append(") LIKE :").append(nomeParametro);
                    } else {
                        jpql.append("e.").append(nomeCampo).append(" = :").append(nomeParametro);
                    } contatore++;
                }
            }
            TypedQuery<T> query = em.createQuery(
                    jpql.toString(),
                    classe
            );
            for (String nomeCampo : campi.keySet()) {
                String nomeParametro = nomeCampo.replace(".", "_");
                Object valore = campi.get(nomeCampo);

                if (valore instanceof String) {
                    String valoreNeutro = ((String) valore).toUpperCase();
                    query.setParameter(nomeParametro, "%" + valoreNeutro+ "%");
                } else {
                    query.setParameter(nomeParametro, campi.get(nomeCampo));
                }
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    public <T> T cercaPrimoPerCampi(Class<T> classe, Map<String, Object> campi) {
        List<T> risultati = cercaPerCampi(classe, campi);
        if (risultati.isEmpty()) {
            return null;
        }
        return risultati.get(0);
    }


    public <T> T aggiorna(T oggetto) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            T oggettoAggiornato = em.merge(oggetto);
            em.getTransaction().commit();
            return oggettoAggiornato;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public <T> boolean elimina(Class<T> classe, Long id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            T oggetto = em.find(classe, id);
            if (oggetto != null) {
                em.remove(oggetto);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}