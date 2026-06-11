import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Ordine_test {
    @BeforeEach
    void setUp() {
        Carrello carrello = new Carrello();
    }

    @Test
    void testOrdine(){
        Ordine ordine = new Ordine();
        assert ordine != null;
    }
}
