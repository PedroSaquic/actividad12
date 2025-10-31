package com.tienda.cart;

import com.tienda.model.Product;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product p, int q) { this.product = p; this.quantity = q; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int q) { this.quantity = q; }
    public double getSubtotal() { return product.getCost() * quantity; }
}