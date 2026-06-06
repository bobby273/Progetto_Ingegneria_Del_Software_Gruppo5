package entity;
import java.util.List;

public class ClientFacade {
    //costruttore per permettere la comunicazione col controller
    private Cliente cliente;
    public ClientFacade(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean annullaOrdine(Ordine ordine){
        boolean done = cliente.annullaOrdine(ordine);
        return false;
    }

    public List<Ordine> visualizzaElencoOrdini(){
        return cliente.visualizzaElencoOrdini();
    }

}
