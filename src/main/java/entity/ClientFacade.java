package entity;

public class ClientFacade {

    public boolean annullaOrdine(Cliente cliente, Ordine ordine){
        boolean done = cliente.annullaOrdine(ordine);
        return false;
    }


}
