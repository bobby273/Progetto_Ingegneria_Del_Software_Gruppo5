package entity;

public class UserFacade {
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

}
