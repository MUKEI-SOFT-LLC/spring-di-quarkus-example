package jp.co.mukeisoftllc.ex;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class BootApplication {
    public static void main(String[] args) {
        Quarkus.run(Initializer.class, args);
    }
}