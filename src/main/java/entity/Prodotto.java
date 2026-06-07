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
    public int getQtaDisponibile() {
        return qtaDisponibile;
    }

    public String getNome() {
        return nome;
    }

    public void setQtaDisponibile(int qtaDisponibile) {
        this.qtaDisponibile = qtaDisponibile;
    }

    //Costruttore vuoto per Hibernate
    public Prodotto() {
    }

    public Prodotto(String nome, int qtaDisponibile, String descrizione, float prezzo, String categoria, boolean isDisponibile, boolean isScontato) {
        this.nome = nome;
        this.qtaDisponibile = qtaDisponibile;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.isDisponibile = isDisponibile;
        this.isScontato = isScontato;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDisponibile(boolean disponibile) {
        isDisponibile = disponibile;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;

    }

    public void setScontato(boolean scontato) {
        isScontato = scontato;
    }

    public boolean isDisponibile() {
        return isDisponibile;
    }

    public boolean isScontato() {
        return isScontato;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getDescrizione() {
        return descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }
}
