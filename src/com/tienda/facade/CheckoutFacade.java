package com.tienda.facade;

import com.tienda.cart.Cart;
import com.tienda.inventory.Inventory;
import com.tienda.AppLogger;

public class CheckoutFacade {
    private PaymentService payment = new PaymentService();
    private ReceiptService receipt = new ReceiptService();
    private Inventory inventory;
    private AppLogger logger = AppLogger.getInstance();

    public CheckoutFacade(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean checkout(Cart cart) {
        // Validación stock
        for (var item : cart.getItems()) {
            int id = item.getProduct().getId();
            if (inventory.getStock(id) < item.getQuantity()) {
                System.out.println("Error: Stock insuficiente para " + item.getProduct().getDescription());
                logger.error("Checkout falló: stock insuficiente para " + item.getProduct().getDescription());
                return false;
            }
        }

        // Reservar/reducir stock
        for (var item : cart.getItems()) {
            inventory.reduceStock(item.getProduct().getId(), item.getQuantity());
        }

        // Procesar pago
        boolean paid = payment.charge(cart.getTotal());
        if (!paid) {
            System.out.println("Pago rechazado.");
            logger.error("Pago rechazado.");
            return false;
        }

        // Generar recibo
        receipt.generate(cart);
        logger.info("Pago completado por Q" + String.format("%.2f", cart.getTotal()));
        return true;
    }
}