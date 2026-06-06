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
        //verifico prima che le credenziali non siano gia' in uso
        if(verificaProfiloAmministratore(email, password))
            return 5;
        Amministratore admin=new Amministratore(email, nome, cognome, password, badge);
        if(gestorePers.salva(admin))
            return 0;
        return -1;
    }

    public int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        if(verificaProfiloCliente(email, password))
            return 6;
        Cliente clt=new Cliente(email, nome, cognome, password, indirizzoSpedizione);
        if(gestorePers.salva(clt))
            return 1;
        return -1;
    }

    public boolean verificaProfiloAmministratore(String email, String password){
        Amministratore admin= gestorePers.cercaPrimoPerCampi(Amministratore.class, Map.of("email",email, "password", password));
        return admin !=null;
    }

    public boolean verificaProfiloCliente(String email, String password){
        Cliente clt= gestorePers.cercaPrimoPerCampi(Cliente.class, Map.of("email",email, "password", password));
        return clt !=null;
    }

}
