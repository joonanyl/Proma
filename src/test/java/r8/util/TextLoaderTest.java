package r8.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TextLoaderTest {
    @BeforeAll
    static void setLocale() {
        TextLoader.getInstance().setLocale("en", "US");
    }

    @Test
    void isSingleton() {
        assertEquals(TextLoader.getInstance().hashCode(), TextLoader.getInstance().hashCode());
    }

    @Test
    void retrievesString() {
        assertTrue(TextLoader.getInstance().getResource("appTitle").equals("Proma - Project Manager"));
    }
}
