package jp.co.mukeisoftllc.ex;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class EnvironmentInjectorTest {
    private static final String EXPECTED_PATH = System.getProperty("java.io.tmpdir");
    @Inject
    private Properties additionalProperties;

    @Test
    public void testForProfileConfiguration() {
        assertEquals("ut",  additionalProperties.getProperty("spring.active.profiles"));
    }

    @Test
    public void testForTextsSavingPath() {
        assertEquals(EXPECTED_PATH, additionalProperties.getProperty("path.for.texts"));
    }

    @Test
    public void testForImagesSavingPath() {
        assertEquals(EXPECTED_PATH, additionalProperties.getProperty("path.for.images"));
    }
}
