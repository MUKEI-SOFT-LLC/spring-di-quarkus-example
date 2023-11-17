package jp.co.mukeisoftllc.ex;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jp.co.mukeisoftllc.ex.EnvironmentInjector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class EnvironmentInjectorTest {
    private static final String EXPECTED_PATH = System.getProperty("java.io.tmpdir");
    @Inject
    private EnvironmentInjector environmentInjector;

    @Test
    public void testForProfileConfiguration() {
        assertArrayEquals(new String[] {"ut"}, environmentInjector.createSpringEnvironment().getActiveProfiles());
    }

    @Test
    public void testForTextsSavingPath() {
        assertEquals(EXPECTED_PATH, environmentInjector.createSpringEnvironment().getProperty("path.for.texts"));
    }

    @Test
    public void testForImagesSavingPath() {
        assertEquals(EXPECTED_PATH, environmentInjector.createSpringEnvironment().getProperty("path.for.images"));
    }
}
