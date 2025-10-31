package com.tienda.model.decorators;

import com.tienda.model.Product;

public class WarrantyDecorator extends ProductDecorator {
    private double warrantyPercent = 0.10; // 10%

    public WarrantyDecorator(Product producto) { super(producto); }

    @Override
    public String getDescription() { return producto.getDescription() + " [+Garantía]"; }

    @Override
    public double getCost() { return producto.getCost() + producto.getCost() * warrantyPercent; }
}