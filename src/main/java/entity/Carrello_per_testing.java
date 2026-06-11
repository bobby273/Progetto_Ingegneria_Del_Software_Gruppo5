package entity;

import java.util.ArrayList;
import java.util.List;

public class Carrello_per_testing {
    //Attributi
    private final String mailUtente; //Id di utente è la sua mail (univoco)
    private List<CarrelloContiene_per_testing> prodottiContenuti; // =righe della tabella carrello

    //CLASSI DI UTILITA
    public static class CarrelloContiene_per_testing {
        int quantita;
        Prodotto_per_testing prodotto;

        CarrelloContiene_per_testing(Prodotto_per_testing prodotto, int quantita) {
            this.prodotto = prodotto;
            this.quantita = quantita;
        }
    }



    public static class Prodotto_per_testing {
        //Attributi
        private int qtaDisponibile;
        private String descrizione;
        private float prezzo;
        private String categoria;
        private boolean isDisponibile=true;
        private boolean isScontato=false;
        private boolean isEliminato = false;
        private String nome;

        //Getter e setter
        public int getQtaDisponibile_test() {
            return qtaDisponibile;
        }

        public String getNome_test() {
            return nome;
        }

        public void setQtaDisponibile_test(int qtaDisponibile) {
            this.qtaDisponibile = qtaDisponibile;
        }

        public Prodotto_per_testing() {
        }

        Prodotto_per_testing(String nome, int qtaDisponibile, String descrizione, float prezzo, String categoria, boolean isDisponibile, boolean isScontato) {
            this.nome = nome;
            this.qtaDisponibile = qtaDisponibile;
            this.descrizione = descrizione;
            this.prezzo = prezzo;
            this.categoria = categoria;
            this.isDisponibile = isDisponibile;
            this.isScontato = isScontato;
            this.isEliminato = false;
        }


        public void setCategoria_test(String categoria) {
            this.categoria = categoria;
        }

        public void setDescrizione_test(String descrizione) {
            this.descrizione = descrizione;
        }

        public void setDisponibile_test(boolean disponibile) {
            isDisponibile = disponibile;
        }

        public void setNome_test(String nome) {
            this.nome = nome;
        }

        public void setPrezzo_test(float prezzo) {
            this.prezzo = prezzo;

        }

        public void setScontato_test(boolean scontato) {
            isScontato = scontato;
        }

        public boolean isEliminato_test() {
            return isEliminato;
        }

        public void setEliminato_test(boolean eliminato) {
            this.isEliminato = eliminato;
        }


        public boolean isDisponibile_test() {
            return isDisponibile;
        }

        public boolean isScontato_test() {
            return isScontato;
        }

        public String getCategoria_test() {
            return categoria;
        }

        public String getDescrizione_test() {
            return descrizione;
        }

        public float getPrezzo_test() {
            return prezzo;
        }
    }


    //Costruttori
    public Carrello_per_testing(String mailUtente) {
        this.mailUtente = mailUtente;
        this.prodottiContenuti = new ArrayList<>();
    }


    public Carrello_per_testing() {
        this.mailUtente = null;
        this.prodottiContenuti = new ArrayList<>();
    }

    //Metodi
    public boolean aggiungiOAggiornaProdotto_test(Prodotto_per_testing prodotto, int qtaDesiderata) {

        //<-- Qui se volessi inserire su check sulle quantità lo metterei, ma abbiamo deciso che non serve

        for(CarrelloContiene_per_testing riga : prodottiContenuti) {
            if(riga.prodotto.getNome_test().equals(prodotto.getNome_test())){
                //Se trovo il prodotto nel carrello, aggiorno quantità
                riga.quantita +=qtaDesiderata;
                return true;
            }
        }
        //Se non esiste già il prodotto
        CarrelloContiene_per_testing nuovo = new CarrelloContiene_per_testing(prodotto, qtaDesiderata);
        prodottiContenuti.add(nuovo);
        return true;
    }

    //Duale di aggiungi e aggiorna | true->ha rimosso, false->non ha rimosso
    public boolean rimuoviProdotto_test(Prodotto_per_testing prodotto) {
        boolean esito = false;
        for (CarrelloContiene_per_testing riga : prodottiContenuti) {
            if (riga.prodotto.getNome_test().equals(prodotto.getNome_test())) {
                prodottiContenuti.remove(riga);
                esito = true;
                break;
            }
        }
        return esito;
    }

    //Getter + setter
    public String getMailUtente_test() {
        return mailUtente;
    }

    public List<CarrelloContiene_per_testing> getProdottiContenuti_test() {
        return prodottiContenuti;
    }
}


