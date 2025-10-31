package com.tienda.facade;

import com.tienda.cart.Cart;

public class ReceiptService {
    public void generate(Cart cart) {
        System.out.println("=== RECIBO ===");
        cart.getItems().forEach(i -> {
            System.out.println(i.getProduct().getDescription() + " x" + i.getQuantity() + " - Q" + String.format("%.2f", i.getSubtotal()));
        });
        System.out.println("TOTAL: Q" + String.format("%.2f", cart.getTotal()));
        System.out.println("==============");
    }
}