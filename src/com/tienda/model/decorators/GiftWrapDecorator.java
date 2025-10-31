
package com.tienda.model.decorators;

import com.tienda.model.Product;

public class GiftWrapDecorator extends ProductDecorator{
    private double wrapCost = 5.00;

    public GiftWrapDecorator(Product producto) { super(producto); }

    @Override
    public String getDescription() { return producto.getDescription() + " [+Envoltorio]"; }

    @Override
    public double getCost() { return producto.getCost() + wrapCost; }
}
