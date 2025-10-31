package com.tienda.model.decorators;

import com.tienda.model.Product;

public class DiscountDecorator extends ProductDecorator {
    private double percent; // 0.15 = 15%

    public DiscountDecorator(Product producto, double percent) { 
        super(producto); 
        this.percent = percent;
    }

    @Override
    public String getDescription() { return producto.getDescription() + " [+Descuento " + (int)(percent*100) + "%]"; }

    @Override
    public double getCost() { return producto.getCost() * (1 - percent); }
}