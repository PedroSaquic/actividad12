package com.tienda.facade;

import com.tienda.cart.Cart;

public class PaymentService {
    public boolean charge(double amount) {
        // Simula cargo: siempre exitoso en demo
        try { Thread.sleep(300); } catch (InterruptedException e) {}
        System.out.println("Procesando pago por Q" + String.format("%.2f", amount) + "...");
        return true;
    }
}