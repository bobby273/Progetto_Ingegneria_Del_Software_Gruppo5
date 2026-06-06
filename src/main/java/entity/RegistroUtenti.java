package entity;

import database.GestorePersistenza;
import java.util.List;
import java.util.Map;

public class RegistroUtenti {
    private GestorePersistenza gestorePers;
    public RegistroUtenti(){
        gestorePers=new GestorePersistenza();
    }

    public int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge){
        return 1;//per farlo contento
    }

    public int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        return 1;//per farlo contento
    }
}
