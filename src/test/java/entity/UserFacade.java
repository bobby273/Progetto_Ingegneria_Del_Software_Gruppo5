package entity;

public class UserFacade {
    private RegistroUtenti regUt;
    public UserFacade(){
        RegistroUtenti regUt=new RegistroUtenti();
    }

    public int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge){
        return regUt.creaNuovoCliente(email, nome, cognome, password, badge);
    }

    public int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione){
        return regUt.creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
    }
}
