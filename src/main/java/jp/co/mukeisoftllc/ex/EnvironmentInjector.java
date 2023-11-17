package jp.co.mukeisoftllc.ex;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Properties;

@ApplicationScoped
public class EnvironmentInjector {

    @Inject
    @ConfigProperty(name = "spring.active.profile", defaultValue = "dev")
    private String springActiveProfile;
    @Inject
    @ConfigProperty(name = "path.for.texts", defaultValue = "./build")
    private String pathForText;

    @Inject
    @ConfigProperty(name = "path.for.images", defaultValue = "./build")
    private String pathForImage;

    @Produces
    @Named("additionalProperties")
    public Properties declareAdditionalProperties() {
        final var result = new Properties();
        result.setProperty("spring.active.profiles", springActiveProfile);
        result.setProperty("path.for.texts", pathForText);
        result.setProperty("path.for.images", pathForImage);
        return result;
    }
}
