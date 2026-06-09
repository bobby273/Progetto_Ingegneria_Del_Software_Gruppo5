package StubPagamento;

import entity.Ordine;

public class InterfacciaPagamento {

    //stub
    public static boolean PagaOrdine(long num_carta, int CCV, int meseScadenza, int annoScadenza, String id_ordine, float totale){
        System.out.println("Pagamento effettuato con successo!");
        return true;
    }

    //stub
    public static boolean RimborsaOrdine(Ordine ordine){
        System.out.println("Rimborso avvenuto con successo!");
        return true;
    }
}
