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



    //Metodi


}
