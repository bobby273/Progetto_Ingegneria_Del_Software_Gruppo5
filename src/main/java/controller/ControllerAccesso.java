package controller;

import entity.UserFacade;

public class ControllerAccesso {
    //instanzio delle variabili con valore pre-determinato
    public static final int AMM_CREATO=0;
    public static final int CLIE_CREATO=1;
    public static final int UTENTE_NON_TROVATO=2;
    public static final int UTENTE_AMM=3;
    public static final int UTENTE_CLIE=4;
    public static final int UTENTE_EXS=5;
    public static final int AMMINISTRATORE=7;
    public static final int CLIENTE=8;

    public static int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge){
        UserFacade userFacade=new UserFacade();
        return userFacade.creaNuovoAmministratore(email, nome, cognome, password, badge);
    }
    public static int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        UserFacade userFacade=new UserFacade();
        return userFacade.creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
    }

    public static boolean verificaProfiloCliente(String email, String password){
        UserFacade userFacade=new UserFacade();
        return userFacade.verificaProfiloCliente(email, password);
    }
    public static boolean verificaProfiloAmministratore(String email, String password){
        UserFacade userFacade=new UserFacade();
        return userFacade.verificaProfiloAmministratore(email, password);
    }
    /* per far funzionare login uso entrambi i verifica~() insieme altrimenti bisogna
    aggiungere un checkbox "amministratore" che porterebbe alla creazione di un altro
    caso d'uso e non mi sembra il caso*/
    public static int checkTipoUtente(String email, String password){
        if(!verificaProfiloAmministratore(email, password)){
            if(!verificaProfiloCliente(email, password))
                return UTENTE_NON_TROVATO;
            else
                return UTENTE_CLIE;
        } else
            return UTENTE_AMM;
    }

    public static boolean checkEsistenzaCliente(String email){
        UserFacade userFacade=new UserFacade();
        return userFacade.verificaEsistenzaCliente(email);
    }

    public static boolean checkEsistenzaAmministratore(String email){
        UserFacade userFacade=new UserFacade();
        return userFacade.verificaEsistenzaAmministratore(email);
    }

    /*
    Uso il metodo checkLogin nelle funzioni che necessitano di autenticazione o permessi Amministratore
    Esso chiama a sua volta i metodi di checkEsistenzaAmministratore/Cliente al fine di verificare che l'utente
    autenticato disponga dei permessi validi per le varie funzioni
     */

    public static int checkLogin(String email, int tipo){
        if(!checkEsistenzaAmministratore(email)){
            if(!checkEsistenzaCliente(email))
                return -1;
            else
                return CLIENTE;
        } else
            return AMMINISTRATORE;
    }
}
