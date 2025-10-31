
package com.tienda.model;

public class SimpleProduct implements Product{
    private int id;
    private String name;
    private double price;

    public SimpleProduct(int id, String name, double price) {
        this.id = id; this.name = name; this.price = price;
    }

    @Override
    public String getDescription() { return name; }

    @Override
    public double getCost() { return price; }

    @Override
    public int getId() { return id; }
}