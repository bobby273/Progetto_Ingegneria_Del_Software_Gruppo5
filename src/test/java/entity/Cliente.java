package entity;

import java.util.List;

public class Cliente {
    private String indirizzoSpedizione;
    private byte immagineProfilo;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private List<Ordine> ordiniPersonali;

    boolean  annullaOrdine(Ordine ordine){
        if(ordine == null) return false;
        if(!ordiniPersonali.contains(ordine)) return false;
        ordine.setStato(Stato.ANNULLATO);

    }
}
