
package com.tienda.observer;

import com.tienda.AppLogger;
import com.tienda.cart.Cart;

public class CartTotalObserver implements Observer{
    private AppLogger logger = AppLogger.getInstance();

    @Override
    public void update(String evento, Object datos) {
        if ("TOTAL_CHANGED".equals(evento) && datos instanceof Cart) {
            Cart cart = (Cart) datos;
            System.out.println("[Notificación] Total del carrito actualizado: Q" + String.format("%.2f", cart.getTotal()));
            logger.info("Total del carrito actualizado: Q" + String.format("%.2f", cart.getTotal()));
        }
    }
}
