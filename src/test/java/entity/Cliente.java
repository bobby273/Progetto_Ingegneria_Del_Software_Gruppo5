package entity;

import java.util.List;

public class Cliente {
    private String indirizzoSpedizione;
    private byte immagineProfilo;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private List<Ordine> ordiniPersonali;

    boolean  annullaOrdine(Ordine ordine){
        if(ordine == null) return false;
        if(!ordiniPersonali.contains(ordine)) return false;
        ordine.setStato(Stato.ANNULLATO);
        return true;
    }

    public List<Ordine> visualizzaElencoOrdini() {

        //idea di base: invoco l'information expert StoricoOrdini e cerco gli ordini del cliente
        //successivamente, li passo ad ordiniPersonali, ovvero la lista visualizzabile dalla singola istanza di Cliente

        //parte di transferimento
        StoricoOrdini storico = StoricoOrdini.getInstance();
        List<Ordine> tuttiGliOrdini= storico.getOrdini();
        for (Ordine ordine : tuttiGliOrdini) {
            if(ordiniPersonali.getEmail().equals(this.getEmail())){
                ordiniPersonali.add(ordine);
            };
        }


    }

}
