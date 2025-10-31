
package com.tienda.observer;

import com.tienda.AppLogger;

public class InventoryObserver implements Observer {
    private AppLogger logger = AppLogger.getInstance();

    @Override
    public void update(String evento, Object datos) {
        if ("OUT_OF_STOCK".equals(evento) && datos instanceof String) {
            String producto = (String) datos;
            System.out.println("[ALERTA INVENTARIO] Producto agotado: " + producto);
            logger.info("Alerta inventario: Producto agotado - " + producto);
        }
    }
}