package entity;

import database.GestorePersistenza;

import javax.swing.*;

import static controller.ControllerAccesso.AMMINISTRATORE;
import static controller.ControllerAccesso.checkLogin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminFacade {

    private final GestorePersistenza gp = new GestorePersistenza();
    private static String mailAmministratore;

    public AdminFacade(String mailUser){
        this.mailAmministratore = mailUser;
    }

    public boolean annullaOrdine(String id_ordine){
        //come prima cosa faccio un check sul login così che anche se si provasse a accedere direttamente a queste funzioni da linea di comando si verrebbe bloccati se non autenticati
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            Amministratore amministratore = gp.cercaPrimoPerCampi(Amministratore.class, Map.of("email", mailAmministratore));
            boolean annullato = amministratore.annullaOrdine(id_ordine);  //delego all'entity amministratore
            boolean aggiorna = false;  //metto come condizione iniziale false
            if (annullato) {
                Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
                aggiorna = (gp.aggiorna(o)==null || gp.aggiorna(o.getCliente())==null);
            }
            return aggiorna;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean creaProdotto(String nome, String categoria, double prezzo, String descrizione, int qta, boolean disponibile, boolean scontato) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            return Catalogo.getInstance().creaEAggiungiProdotto(nome, categoria, prezzo, descrizione, qta, disponibile, scontato);  //l'information expert di prodotto è catalogo, per la gestione dei prodotti mi devo quindi rifare a lui
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean modificaNome(String nomeOriginale, String nuovoNome) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
        return Catalogo.getInstance().modificaNome(nomeOriginale, nuovoNome);
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean modificaCategoria(String nome, String nuovaCategoria) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            return Catalogo.getInstance().modificaCategoria(nome, nuovaCategoria);
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean modificaPrezzo(String nome, float nuovoPrezzo) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            return Catalogo.getInstance().modificaPrezzo(nome, nuovoPrezzo);
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean modificaDescrizione(String nome, String nuovaDescrizione) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            return Catalogo.getInstance().modificaDescrizione(nome, nuovaDescrizione);
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean modificaQuantita(String nome, int nuovaQta) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            return Catalogo.getInstance().modificaQuantita(nome, nuovaQta);
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean modificaDisponibilita(String nome, boolean nuovaDisp) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            return Catalogo.getInstance().modificaDisponibilita(nome, nuovaDisp);
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean modificaSconto(String nome, boolean nuovoSconto) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            return Catalogo.getInstance().modificaSconto(nome, nuovoSconto);
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

public static boolean rimuoviProdotto(String nomeProdotto) {
       if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
          return Catalogo.getInstance().rimuoviProdotto(nomeProdotto);
       } else {
          JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
          return false;
       }
   }

    public static java.util.List<String[]> getListaProdottiPerScorrimento() {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            // 1. Il catalogo estrae le entità (siamo nello stesso package, quindi funziona)
            java.util.List<Prodotto> veriProdotti = Catalogo.getInstance().getTuttiIProdotti();
            java.util.List<String[]> listaDatiGrezzi = new java.util.ArrayList<>();

            // Trasformiamo ogni entità in un array di Stringhe leggibile dall'esterno
            for (Prodotto p : veriProdotti) {
                String[] datiProdotto = new String[]{
                        p.getNome(),
                        p.getCategoria(),
                        String.format("%.2f", p.getPrezzo()),
                        p.getDescrizione(),
                        String.valueOf(p.getQtaDisponibile()),
                        String.valueOf(p.isDisponibile()),
                        String.valueOf(p.isScontato())
                };
                listaDatiGrezzi.add(datiProdotto);
            }

            return listaDatiGrezzi;  //ritorno i prodotti come lista di stringa ocsì da facilitare il collegamento con la gui
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    public static List<String[]> ricercaProdottoInCatalogo(String categoriaRicerca,String elementoDaCercare) {
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            List<Prodotto> prodottiTrovati;

            if (elementoDaCercare == null || elementoDaCercare.trim().isEmpty()) {
                prodottiTrovati = Catalogo.getInstance().getTuttiIProdotti(); //LOGICA DI ANNULLAMENTO: se non compilo la ricerca, ottengo indietro il catalogo intero
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
                            String.valueOf(p.isScontato())
                    }); //aggiungo i prodotti trovati al risultato
                }
            }
            return risultati;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Long getIdClienteDaIdOrdine(String id_ordine){
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getIdCliente();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getStato(String id_ordine){
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getStato().toString();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public float getTotale(String id_ordine){
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getTotale();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public LocalDateTime getDataConferma(String id_ordine){
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getDataConferma();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getIndirizzoSpedizione(String id_ordine){
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            Ordine o= gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getIndirizzoSpedizione();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //funzione per prendere i prodotti contenuti nell'ordine e la loro quantità
    public ArrayList<String> getProdottoEQuantita(String id_ordine){
        if(checkLogin(mailAmministratore,AMMINISTRATORE)==AMMINISTRATORE) {
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            List<OrdineContiene> pc = o.getProdottiContenuti();
            ArrayList<String> prodotti = new ArrayList<>();
            for (OrdineContiene c : pc) {
                prodotti.add(c.getProdotto().getNome());
                prodotti.add(String.valueOf(c.getQuantita()));
            }

            return prodotti;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}







