package entity;

import database.GestorePersistenza;
import java.util.Map;

public class RegistroUtenti {
    private static RegistroUtenti instance;
    private GestorePersistenza gestorePers;
    private RegistroUtenti(){
        this.gestorePers=new GestorePersistenza();
    }

    static RegistroUtenti getInstance(){
        if(instance==null){
            instance=new RegistroUtenti();
        }
        return instance;
    }

    int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge){
        //verifico prima che le credenziali non siano gia' in uso
        if(verificaProfiloAmministratore(email, password) || verificaProfiloCliente(email,password))
                return 5;
        Amministratore admin=new Amministratore(email, nome, cognome, password, badge);
        if(gestorePers.salva(admin))
            return 0;
        return -1;
    }

    int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        if(verificaProfiloAmministratore(email, password) || verificaProfiloCliente(email, password))
            return 5;
        Cliente clt=new Cliente(email, nome, cognome, password, indirizzoSpedizione);
        if(gestorePers.salva(clt))
            return 1;
        return -1;
    }

    boolean verificaProfiloAmministratore(String email, String password){
        Amministratore admin= gestorePers.cercaPrimoPerCampi(Amministratore.class, Map.of("email",email, "password", password));
        return admin !=null;
    }

    boolean verificaProfiloCliente(String email, String password){
        Cliente clt= gestorePers.cercaPrimoPerCampi(Cliente.class, Map.of("email",email, "password", password));
        return clt !=null;
    }

    boolean verificaEsistenzaCliente(String email){
        Cliente clt= gestorePers.cercaPrimoPerCampi(Cliente.class, Map.of("email",email));
        return clt !=null;
    }

    boolean verificaEsistenzaAmministratore(String email){
        Amministratore admin= gestorePers.cercaPrimoPerCampi(Amministratore.class, Map.of("email",email));
        return admin !=null;
    }
}
