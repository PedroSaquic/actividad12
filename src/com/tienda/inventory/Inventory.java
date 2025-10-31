package com.tienda.inventory;

import com.tienda.model.SimpleProduct;
import com.tienda.observer.Subject;
import com.tienda.observer.Observer;

import java.util.*;

public class Inventory implements Subject {
    private Map<Integer, Integer> stock = new HashMap<>();
    private Map<Integer, SimpleProduct> catalog = new HashMap<>();
    private List<Observer> observers = new ArrayList<>();

    public void addProduct(SimpleProduct p, int cantidad) {
        catalog.put(p.getId(), p);
        stock.put(p.getId(), stock.getOrDefault(p.getId(),0) + cantidad);
        notifyObservers("STOCK_CHANGED", p);
    }

    public SimpleProduct getProductById(int id) {
        return catalog.get(id);
    }

    public boolean reduceStock(int id, int amount) {
        int s = stock.getOrDefault(id, 0);
        if (s >= amount) {
            stock.put(id, s - amount);
            if (stock.get(id) == 0) {
                notifyObservers("OUT_OF_STOCK", catalog.get(id).getDescription());
            } else {
                notifyObservers("STOCK_CHANGED", catalog.get(id));
            }
            return true;
        }
        return false;
    }

    public void increaseStock(int id, int amount) {
        stock.put(id, stock.getOrDefault(id, 0) + amount);
        notifyObservers("STOCK_CHANGED", catalog.get(id));
    }

    public int getStock(int id) {
        return stock.getOrDefault(id, 0);
    }

    public Collection<SimpleProduct> listProducts() {
        return catalog.values();
    }

    @Override
    public void addObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers(String evento, Object datos) {
        for (Observer o : new ArrayList<>(observers)) {
            o.update(evento, datos);
        }
    }
}