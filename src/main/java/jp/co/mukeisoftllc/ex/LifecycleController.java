package jp.co.mukeisoftllc.ex;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jp.co.mukeisoftllc.ex.spring.world.services.FileService;

@ApplicationScoped
public class LifecycleController {
    @Inject
    private FileService fileService;

    public void onStart(@Observes StartupEvent ev) {
        fileService.postConstruct();
    }

    public void onStop(@Observes ShutdownEvent ev) {
        fileService.preDestroy();;
    }
}
