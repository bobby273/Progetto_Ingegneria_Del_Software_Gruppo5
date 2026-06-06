package controller;

import entity.ClientFacade; //per comunicare con livello entity

public class ControllerCliente {

    //Attributi
    private static ClientFacade clientFacade = new ClientFacade();

    //TODO: simulo la mail di cliente attuale
    private static final String MAIL_CLIENTE = "fornataro.ma@gmail.com";


    //Metodi esposti
    public static boolean AggiungiAlCarrello(String NomeProdotto, int qtaDesiderata){
        //true -> aggiunto al carrello, false -> prodotto (o carrello) non trovato

        System.out.println("Eseguendo UC: AggiungiProdottoAlCarrello per "+ MAIL_CLIENTE);
        //Metodo controller richiamatato dal boundary FrameDettaglioProdotto
        return clientFacade.aggiungiOAggiornaProdottoACarrello(MAIL_CLIENTE, NomeProdotto, qtaDesiderata);

    }
}
