package entity;

import database.GestorePersistenza;
import java.util.Map;

public class Catalogo {

    public static Catalogo instance; //SINGLETON

    private GestorePersistenza gp;

    //costruttore privato
    private Catalogo() {
        gp = new GestorePersistenza();
    }

    static Catalogo getInstance() {
        if(instance == null) {
            instance = new Catalogo(); //Non sono sicuro che vada messo, controlliamo!
        }
        return instance;

    } //TODO: l'ho creato perchè mi serviva per accedere ai prodotti, va completato!

    //Metodi
    Prodotto ricercaProdotto(String nomeProdotto) {
        try{
            return gp.cercaPrimoPerCampi(Prodotto.class, Map.of("nome", nomeProdotto));
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("[CATALOGO] Errore nella ricerca del prodotto");
            return null; //Prodtto non trovato
        }
    }
}
