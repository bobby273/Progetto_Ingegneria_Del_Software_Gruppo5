package entity;

public class Utente {
    private String email;
    private String nome;
    private String cognome;
    private String password;



    // l'unico get utile è quello dell'email in quanto serve per la visualizzazione degli ordini personali
    public String getEmail() {
        return email;
    }
}
