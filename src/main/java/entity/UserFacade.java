package entity;

public class UserFacade {
    private RegistroUtenti regUt;
    public UserFacade(){
        RegistroUtenti regUt=new RegistroUtenti();
    }

    public int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge){
        return regUt.creaNuovoAmministratore(email, nome, cognome, password, badge);
    }

    public int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        return regUt.creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
    }

    public boolean verificaProfiloAmministratore(String email, String password){
        return regUt.verificaProfiloAmministratore(email, password);
    }

    public boolean verificaProfiloCliente(String email, String password){
        return regUt.verificaProfiloCliente(email, password);
    }

}
