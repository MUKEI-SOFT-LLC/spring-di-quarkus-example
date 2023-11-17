package jp.co.mukeisoftllc.ex;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.StandardEnvironment;

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
    @Qualifier("env")
    public StandardEnvironment createSpringEnvironment() {
        System.setProperty("path.for.texts", pathForText);
        System.setProperty("path.for.images", pathForImage);
        final var result = new StandardEnvironment();
        result.setActiveProfiles(springActiveProfile);
        return result;
    }
}
