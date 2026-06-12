package entity;

import java.util.Map;

public class UserFacade {
    public UserFacade() {
    }

    //tutti i metodi chiamano a loro volta l'istanza di RegistroUtenti al fine di completare l'operazione.
    public int creaNuovoAmministratore(String email, String nome, String cognome, String password, String badge) {
        return RegistroUtenti.getInstance().creaNuovoAmministratore(email, nome, cognome, password, badge);
    }

    public int creaNuovoCliente(String email, String nome, String cognome, String password, String indirizzoSpedizione) {
        return RegistroUtenti.getInstance().creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
    }

    public boolean verificaProfiloAmministratore(String email, String password) {
        return RegistroUtenti.getInstance().verificaProfiloAmministratore(email, password);
    }

    public boolean verificaProfiloCliente(String email, String password) {
        return RegistroUtenti.getInstance().verificaProfiloCliente(email, password);
    }

    public boolean verificaEsistenzaCliente(String email) {
        return RegistroUtenti.getInstance().verificaEsistenzaCliente(email);
    }
    public boolean verificaEsistenzaAmministratore(String email) {
        return RegistroUtenti.getInstance().verificaEsistenzaAmministratore(email);
    }
}