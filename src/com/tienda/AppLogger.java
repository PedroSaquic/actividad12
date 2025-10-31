package com.tienda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppLogger {
    private static AppLogger instancia;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AppLogger() {}

    public static synchronized AppLogger getInstance() {
        if (instancia == null) {
            instancia = new AppLogger();
        }
        return instancia;
    }

    public void info(String msg) {
        System.out.println("[INFO] " + df.format(LocalDateTime.now()) + " - " + msg);
    }

    public void error(String msg) {
        System.err.println("[ERROR] " + df.format(LocalDateTime.now()) + " - " + msg);
    }
}