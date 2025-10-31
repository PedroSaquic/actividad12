
package com.tienda.model.decorators;


import com.tienda.model.Product;

public abstract class ProductDecorator implements Product{
    protected Product producto;

    public ProductDecorator(Product producto) {
        this.producto = producto;
    }

    @Override public int getId() { return producto.getId(); }
    @Override public String getDescription() { return producto.getDescription(); }
    @Override public double getCost() { return producto.getCost(); }
}
