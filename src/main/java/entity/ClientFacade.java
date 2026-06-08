package entity;

//import StubPagamento.InterfacciaPagamento;
import database.GestorePersistenza;

import javax.swing.*;

import static controller.ControllerAccesso.CLIENTE;
import static controller.ControllerAccesso.checkLogin;
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
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE){
            Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
            boolean annullato = cliente.annullaOrdine(id_ordine);
            boolean aggiorna = false;
            if (annullato) {
                Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
                aggiorna = !((gp.aggiorna(o)==null) ||(gp.aggiorna(cliente)==null));
            }
            return aggiorna;
        } else{
            //il null è sus
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean creaOrdine(String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        System.out.println("🔍 [FACADE] Inizio procedura creaOrdine per l'utente: " + mailUtente);

        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));

            if (cliente == null) {
                System.out.println("❌ ERRORE: Cliente non trovato nel Database!");
                return false;
            }

            // 1. Creiamo l'oggetto ordine (il metodo interno ha già fatto tutti i controlli)
            Ordine o = cliente.creaOrdine(indirizzo, num_carta, CCV, meseScadenza, annoScadenza);

            // 2. Controlliamo se è andato tutto a buon fine
            if (o != null) {
                System.out.println("✅ [FACADE] Ordine creato internamente. Procedo al salvataggio diretto (gp.salva)...");

                // ====================================================================
                // IL SALVATAGGIO DEFINITIVO: Ora che abbiamo il mappedBy e niente Cascade strani,
                // questo comando farà una INSERT perfetta dell'Ordine senza far esplodere il Cliente!
                // ====================================================================
                gp.salva(o);
                cliente.getCarrello().getProdottiContenuti().clear();
                gp.aggiorna(cliente);


                System.out.println("✅ [FACADE] Ordine salvato nel Database con successo! Missione compiuta.");
                return true; // 🟢 Segnale di SUCCESSO per far comparire il popup sulla GUI
            }

            System.out.println("❌ [FACADE] La creazione dell'ordine è fallita (es. pagamento rifiutato o carrello vuoto).");
            return false;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public boolean aggiungiOAggiornaProdottoACarrello(String mailUtente, String nomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        //Delego ricerca del prodotto all'informatione expert [catalogo]
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Prodotto prodotto = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

            Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));

            if (prodotto != null && cliente != null) {

                boolean esitoAggiunta = cliente.aggiungiProdottoACarrello(prodotto, qtaDesiderata);

                if (esitoAggiunta) {
                    gp.aggiorna(cliente);
                    return true;
                }
            }
            return false;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    //Manuel: per rimuovere prodotti dal carrello
    public boolean rimuoviProdottoDalCarrello(String mailUtente, String nomeProdotto){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
            if (cliente != null) {
                Prodotto prodotto = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

                boolean esitoRimozione = cliente.rimuoviProdottoDalCarrello(prodotto);

                if (esitoRimozione) {
                    gp.aggiorna(cliente);
                    return true;
                }
            }
            return false;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Metodi di spacchettamento per garantire isolamento di prodotto
    public List<String[]> getCatalogoBreve() {
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            List<Prodotto> prodotti = Catalogo.getInstance().getTuttiIProdotti();
            List<String[]> catalogoBreve = new ArrayList<>();

            if (prodotti != null) {
                for (Prodotto p : prodotti) {
                    catalogoBreve.add(new String[]{p.getNome(), p.getDescrizione()});
                }
            }
            return catalogoBreve;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<String[]> getCarrelloBreve(String mailUtente) {
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
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
                            String.valueOf(item.getQuantita()),
                            String.valueOf(p.isDisponibile())
                    });
                }
            }
            return carrelloBreve;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Object[] getDettagliProdotto (String nomeProdotto){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Prodotto p = Catalogo.getInstance().ricercaProdotto(nomeProdotto);

            if (p != null) {
                return new Object[]{
                        p.getNome(),
                        p.getPrezzo(),
                        p.getCategoria(),
                        p.getQtaDisponibile(),
                        p.IsScontato(),
                        p.getDescrizione(),
                        p.isDisponibile()
                };
            }
            return null;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public boolean esisteProdotto(String nomeProdotto) {
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            return Catalogo.getInstance().ricercaProdotto(nomeProdotto) != null;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    //proviamo a filtrare direttamente nella clientFacade.
    public List<String[]> ricercaProdottoInCatalogo(String categoriaRicerca,String elementoDaCercare) {
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
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
            }
            return risultati;
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Object[] getInfoOrdine(String id_ordine) {
        if(checkLogin(mailUtente, CLIENTE) == CLIENTE) {
            Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));

            if (cliente != null) {
                return cliente.visualizzaInfoOrdine(id_ordine);
            }
            return null;

        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato.", "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    public List<String[]> getStoricoOrdiniPersonale() {
        if(checkLogin(mailUtente, CLIENTE) == CLIENTE) {

            Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
            List<String[]> storicoBreve = new ArrayList<>();

            if (cliente != null) {
                // ECCO LA MAGIA: Deleghiamo al Cliente il compito di trovarsi i suoi ordini!
                List<Ordine> ordiniPersonali = cliente.visualizzaOrdiniPersonali();

                for (Ordine o : ordiniPersonali) {
                    storicoBreve.add(new String[]{
                            o.getId(), // (o getId_ordine())
                            o.getDataConferma() != null ? o.getDataConferma().toString() : "In elaborazione",
                            o.getStato() != null ? o.getStato().toString() : "Sconosciuto",
                            String.format("%.2f", o.getTotale())
                    });
                }
            }
            return storicoBreve;

        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide.", "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        }
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
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            return Catalogo.getInstance().getTuttiIProdotti();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //Manuel: per ottenere prodotti da visualizzare nel carrello
    public List<CarrelloContiene> getProdottiNelCarrello(String mailUtente){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Cliente cliente = gp.cercaPrimoPerCampi(Cliente.class, Map.of("email", mailUtente));
            if (cliente != null) {
                return cliente.getProdottiCarrello();
            }
            return new ArrayList<>();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    public Long getIdClienteDaIdOrdine(String id_ordine){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getIdCliente();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getStato(String id_ordine){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getStato().toString();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public float getTotale(String id_ordine){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getTotale();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public LocalDateTime getDataConferma(String id_ordine){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getDataConferma();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getIndirizzoSpedizione(String id_ordine){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
            Ordine o = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id_ordine", id_ordine));
            return o.getIndirizzoSpedizione();
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public ArrayList<String> getProdottoEQuantita(String id_ordine){
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
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

    // Nella ClientFacade: Metodo per la ricerca che restituisce i dati pronti per la GUI
    public List<String[]> ricercaProdottoInCatalogoBreve(String categoriaRicerca, String elementoDaCercare) {
        if(checkLogin(mailUtente,CLIENTE)==CLIENTE) {
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
        } else {
            JOptionPane.showMessageDialog(null , "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}

