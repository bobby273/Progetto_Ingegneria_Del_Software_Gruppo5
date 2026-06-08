package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Prodotto {
    //Attributi
    private int qtaDisponibile;
    private String descrizione;
    private float prezzo;
    private String categoria;
    private boolean isDisponibile=true;
    private boolean isScontato=false;

    @Id
    private String nome;

    //Getter e setter
    int getQtaDisponibile() {
        return qtaDisponibile;
    }

    String getNome() {
        return nome;
    }

    void setQtaDisponibile(int qtaDisponibile) {
        this.qtaDisponibile = qtaDisponibile;
    }

    //Costruttore vuoto per Hibernate
    public Prodotto() {
    }

    Prodotto(String nome, int qtaDisponibile, String descrizione, float prezzo, String categoria, boolean isDisponibile, boolean isScontato) {
        this.nome = nome;
        this.qtaDisponibile = qtaDisponibile;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.isDisponibile = isDisponibile;
        this.isScontato = isScontato;
    }


    void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    void setDisponibile(boolean disponibile) {
        isDisponibile = disponibile;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    void setPrezzo(float prezzo) {
        this.prezzo = prezzo;

    }

    void setScontato(boolean scontato) {
        isScontato = scontato;
    }

    boolean isDisponibile() {
        return isDisponibile;
    }

    boolean isScontato() {
        return isScontato;
    }
    String getCategoria() {
        return categoria;
    }
    String getDescrizione() {
        return descrizione;
    }

    float getPrezzo() {
        return prezzo;
    }

    boolean IsScontato() {
        return isScontato;
    }
}
