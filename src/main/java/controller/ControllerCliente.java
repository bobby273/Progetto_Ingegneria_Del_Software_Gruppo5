package controller;

import entity.Cliente;
import entity.Ordine;
import entity.Stato;
import java.util.List;

public class ControllerCliente {
    Cliente cliente;
    //costruttore
    //TODO: verificarne la correttezza
    public ControllerCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public static boolean AggiungiAlCarrello(String NomeProdotto, int qtaDesiderata){
        //Metodo controller richiamatato dal boundary FrameDettaglioProdotto
        //TODO: implementare logica per cui, se già presente aggiorno la qtà nel carrello, altrimenti aggiungo al carrello
        return false;
    }

    public List<Ordine> richiediElencoOrdini() {
        //Metodo controller richiamato dal boundary  FrameStoricoOrdini
        return this.cliente.visualizzaElencoOrdini();
    }
}
