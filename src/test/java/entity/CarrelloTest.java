package entity;

import entity.Carrello_per_testing.Prodotto_per_testing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CarrelloTest {

    private Carrello_per_testing carrello;
    private Prodotto_per_testing prodotto;

    @BeforeEach
    void setUp() {
        carrello = new Carrello_per_testing("user@example.com");
        prodotto = new Prodotto_per_testing("Prodotto1", 10, "Descrizione prodotto 1", 20.5f, "Categoria1", true, false);
    }

    @Test
    void test_AggiungiNuovoProdotto() {
        // Act
        boolean result = carrello.aggiungiOAggiornaProdotto_test(prodotto, 3);

        // Assert
        assertTrue(result, "The product should be added successfully.");
        assertEquals(1, carrello.getProdottiContenuti_test().size(), "The cart should contain one product.");
        assertEquals(3, carrello.getProdottiContenuti_test().get(0).quantita, "The product quantity in the cart should be 3.");
    }

    @Test
    void test_AggiornaQuantitaProdottoEsistente() {
        // Arrange
        carrello.aggiungiOAggiornaProdotto_test(prodotto, 3);

        // Act
        boolean result = carrello.aggiungiOAggiornaProdotto_test(prodotto, 2);

        // Assert
        assertTrue(result, "The product quantity should be updated successfully.");
        assertEquals(1, carrello.getProdottiContenuti_test().size(), "The cart should still contain one product.");
        assertEquals(5, carrello.getProdottiContenuti_test().get(0).quantita, "The product quantity in the cart should now be 5.");
    }

    @Test
    void test_AggiungiProdottoDifferente() {
        // Arrange
        Prodotto_per_testing prodotto2 = new Prodotto_per_testing("Prodotto2", 15, "Descrizione prodotto 2", 30.0f, "Categoria2", true, false);
        carrello.aggiungiOAggiornaProdotto_test(prodotto, 3);

        // Act
        boolean result = carrello.aggiungiOAggiornaProdotto_test(prodotto2, 4);

        // Assert
        assertTrue(result, "The second product should be added successfully.");
        assertEquals(2, carrello.getProdottiContenuti_test().size(), "The cart should now contain two products.");
        assertEquals(4, carrello.getProdottiContenuti_test().get(1).quantita, "The quantity of the second product in the cart should be 4.");
    }

    @Test
    void test_AggiungiProdottoConQuantitaZero() {
        // Arrange

        // Act
        boolean result = carrello.aggiungiOAggiornaProdotto_test(prodotto, 0);

        // Assert
        assertTrue(result, "The product should be added even with a quantity of zero.");
        assertEquals(1, carrello.getProdottiContenuti_test().size(), "The cart should contain one product.");
        assertEquals(0, carrello.getProdottiContenuti_test().get(0).quantita, "The quantity of the product in the cart should be 0.");
    }

    @Test
    void testRimuoviProdottoFromCart_ExistingProduct() {
        // Arrange
        carrello.aggiungiOAggiornaProdotto_test(prodotto, 1);

        // Act
        boolean result = carrello.rimuoviProdotto_test(prodotto);

        // Assert
        assertTrue(result, "The product should be successfully removed.");
        assertTrue(carrello.getProdottiContenuti_test().isEmpty(), "The cart should be empty after removal.");
    }

    @Test
    void testRimuoviProdottoFromCart_NonExistingProduct() {
                // Act
        boolean result = carrello.rimuoviProdotto_test(prodotto);

        // Assert
        assertFalse(result, "The product should not be removed since it does not exist in the cart.");
        assertTrue(carrello.getProdottiContenuti_test().isEmpty(), "The cart should remain empty.");
    }

    @Test
    void testRimuoviProdottoFromCart_MultipleProducts() {
        // Arrange
        Prodotto_per_testing prodotto2 = new Prodotto_per_testing("Product2", 5, "Description2", 50.0f, "Category2", true, false);
        carrello.aggiungiOAggiornaProdotto_test(prodotto, 1);
        carrello.aggiungiOAggiornaProdotto_test(prodotto2, 1);

        // Act
        boolean result = carrello.rimuoviProdotto_test(prodotto);

        // Assert
        assertTrue(result, "The first product should be successfully removed.");
        assertEquals(1, carrello.getProdottiContenuti_test().size(), "Only one product should remain in the cart.");
        assertEquals("Product2", carrello.getProdottiContenuti_test().get(0).prodotto.getNome_test(), "The remaining product should be Product2.");
    }

    @Test
    void testRimuoviProdottoFromCart_EmptyCart() {

        // Act
        boolean result = carrello.rimuoviProdotto_test(prodotto);

        // Assert
        assertFalse(result, "Removing from an empty cart should return false.");
        assertTrue(carrello.getProdottiContenuti_test().isEmpty(), "The cart should remain empty after the operation.");
    }

    @Test
    void testRimuoviProdotto_DuplicateProducts() {
        // Arrange
        carrello.aggiungiOAggiornaProdotto_test(prodotto, 1);
        carrello.aggiungiOAggiornaProdotto_test(prodotto, 2);

        // Act
        boolean result = carrello.rimuoviProdotto_test(prodotto);

        // Assert
        assertTrue(result, "The product should be removed successfully.");
        assertTrue(carrello.getProdottiContenuti_test().isEmpty(), "The cart should be empty as the duplicate products are removed.");
    }

    @Test
    void testMailNulla(){
        Carrello_per_testing carrello = new Carrello_per_testing();
        assertNull(carrello.getMailUtente_test());
    }

    @Test
    void testQtaDisponibile() {
        assertEquals(10, prodotto.getQtaDisponibile_test()); //test del get
        prodotto.setQtaDisponibile_test(20);
        assertEquals(20, prodotto.getQtaDisponibile_test());//test del set
    }

    @Test
    void testNome() {
        assertEquals("Prodotto1", prodotto.getNome_test()); //test del get
        prodotto.setNome_test("NuovoProdotto");
        assertEquals("NuovoProdotto", prodotto.getNome_test());//test del set
    }

    @Test
    void testDescrizione() {
        assertEquals("Descrizione prodotto 1", prodotto.getDescrizione_test()); //test del get
        prodotto.setDescrizione_test("Nuova descrizione");
        assertEquals("Nuova descrizione", prodotto.getDescrizione_test());//test del set
    }

    @Test
    void testPrezzo() {
        assertEquals(20.5f, prodotto.getPrezzo_test()); //test del get
        prodotto.setPrezzo_test(25.0f);
        assertEquals(25.0f, prodotto.getPrezzo_test());//test del set
    }

    @Test
    void testCategoria() {
        assertEquals("Categoria1", prodotto.getCategoria_test()); //test del get
        prodotto.setCategoria_test("NuovaCategoria");
        assertEquals("NuovaCategoria", prodotto.getCategoria_test());//test del set
    }

    @Test
    void testIsDisponibile() {
        assertTrue(prodotto.isDisponibile_test()); //test del get
        prodotto.setDisponibile_test(false);
        assertFalse(prodotto.isDisponibile_test());//test del set
    }

    @Test
    void testIsScontato() {
        assertFalse(prodotto.isScontato_test()); //test del get
        prodotto.setScontato_test(true);
        assertTrue(prodotto.isScontato_test());//test del set
    }
    
    @Test
    void testIsEliminato(){
        assertFalse(prodotto.isEliminato_test()); //test del get
        prodotto.setEliminato_test(true);
        assertTrue(prodotto.isEliminato_test());//test del set
    }

    @Test
    void testQtaDisponibile_UsoCostruttorePredefinito() {
        // Arrange
        Prodotto_per_testing prodotto = new Prodotto_per_testing();

        // Act
        int result = prodotto.getQtaDisponibile_test();

        // Assert
        assertEquals(0, result, "Default constructor should initialize qtaDisponibile to 0.");
    }

    @Test
    void testQtaDisponibile_settataNegativa() {
        // Arrange
        Prodotto_per_testing prodotto = new Prodotto_per_testing();
        prodotto.setQtaDisponibile_test(-5);

        // Act
        int result = prodotto.getQtaDisponibile_test();

        // Assert
        assertEquals(-5, result, "The method should handle negative values and return them accordingly.");
    }

}