package entity;

import database.GestorePersistenza;
import java.util.Map;

public class UserFacade {

    //Attributi
    private GestorePersistenza gp = new GestorePersistenza();

    //Metodi
    public UserFacade(){}

    public int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge){
        RegistroUtenti regUt=new RegistroUtenti();
        return regUt.creaNuovoAmministratore(email, nome, cognome, password, badge);
    }

    public int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        RegistroUtenti regUt=new RegistroUtenti();
        return regUt.creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
    }

    public boolean verificaProfiloAmministratore(String email, String password){
        RegistroUtenti regUt=new RegistroUtenti();
        return regUt.verificaProfiloAmministratore(email, password);
    }

    public boolean verificaProfiloCliente(String email, String password){
        RegistroUtenti regUt=new RegistroUtenti();
        return regUt.verificaProfiloCliente(email, password);
    }

    /*public boolean aggiungiOAggiornaProfilo(String mailUtente, String nomeProdotto, int qtaDesiderata){
        try{
            //Cerca prodotto per nome
            Prodotto prodotto = gp.cercaPrimoPerCampi(Prodotto.class, Map.of("nome", nomeProdotto));
            if(prodotto == null || prodotto.get){}
        }
    }*/

}
