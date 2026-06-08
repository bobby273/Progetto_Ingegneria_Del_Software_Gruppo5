package entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Utente {
    private String email;
    private String nome;
    private String cognome;
    private String password;

    public Utente(){}
    Utente(String email, String nome, String cognome, String password){
        this.email=email;
        this.nome=nome;
        this.cognome=cognome;
        this.password=password;
    }

    //Getter e setter
    String getNome() {
        return nome;
    }
    String getCognome() {
        return cognome;
    }

    // l'unico get utile è quello dell'email in quanto serve per la visualizzazione degli ordini personali
    String getEmail() {
        return email;
    }
}
