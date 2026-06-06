package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Prodotto {
    //Attributi
    private int quantita;
    @Id
    private String nome;

    //Getter e setter
    public int getQuantita() {
        return quantita;
    }

    public String getNome() {
        return nome;
    }

    //Costruttore vuoto per Hibernate
    public Prodotto() {
    }

    //Metodi

}
