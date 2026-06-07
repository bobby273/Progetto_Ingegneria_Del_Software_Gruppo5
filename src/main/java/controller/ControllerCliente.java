package controller;

//import StubPagamento.InterfacciaPagamento;
import entity.ClientFacade; //per comunicare con livello entity
import entity.Cliente;

public class ControllerCliente {

    //Attributi
    private static ClientFacade clientFacade = new ClientFacade();

    //TODO: simulo la mail di cliente attuale
    private static final String MAIL_CLIENTE = "fornataro.ma@gmail.com";


    //Metodi esposti
    public static boolean AggiungiAlCarrello(String NomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        System.out.println("Eseguendo UC: AggiungiProdottoAlCarrello per "+ MAIL_CLIENTE);
        //Metodo controller richiamato dal boundary FrameDettaglioProdotto
        return clientFacade.aggiungiOAggiornaProdottoACarrello(MAIL_CLIENTE, NomeProdotto, qtaDesiderata);

    }

    public boolean annullaOrdine(Cliente cliente, String id_ordine){
        return clientFacade.annullaOrdine(cliente, id_ordine);
    }

    public void creaOrdine(Cliente cliente, String indirizzo, String num_carta, int CCV, int meseScadenza, int annoScadenza){
        clientFacade.creaOrdine(cliente, indirizzo,num_carta,CCV,meseScadenza,annoScadenza);
    }


}
