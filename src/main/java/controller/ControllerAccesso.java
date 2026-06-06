package controller;

import entity.UserFacade;

public class ControllerAccesso {
    public static final int AMM_CREATO=0;
    public static final int CLIE_CREATO=1;
    public static final boolean CLIE_VER=true;
    public static final boolean AMM_VER=true;
    public static final int UTENTE_NON_TROVATO=2;
    public static final int UTENTE_AMM=3;
    public static final int UTENTE_CLIE=4;

    public static int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge){
        UserFacade userFacade=new UserFacade();
        return userFacade.creaNuovoAmministratore(email, nome, cognome, password, badge);//per farlo contento
    }
    public static int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        UserFacade userFacade=new UserFacade();
        return userFacade.creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
    }


    public static boolean verificaProfiloCliente(String email, String password){
        return CLIE_VER;
    }
    public static boolean verificaProfiloAmministratore(String email, String password){
        return AMM_VER;
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
}
