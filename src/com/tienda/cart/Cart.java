package com.tienda.cart;

import com.tienda.observer.Subject;
import com.tienda.observer.Observer;
import com.tienda.model.Product;

import java.util.*;

public class Cart implements Subject {
    private List<CartItem> items = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addProduct(Product p, int qty) {
        for (CartItem it : items) {
            if (it.getProduct().getId() == p.getId()) {
                it.setQuantity(it.getQuantity() + qty);
                notifyObservers("TOTAL_CHANGED", this);
                return;
            }
        }
        items.add(new CartItem(p, qty));
        notifyObservers("TOTAL_CHANGED", this);
    }

    public boolean removeProduct(int productId, int qty) {
        Iterator<CartItem> it = items.iterator();
        while (it.hasNext()) {
            CartItem ci = it.next();
            if (ci.getProduct().getId() == productId) {
                if (ci.getQuantity() > qty) {
                    ci.setQuantity(ci.getQuantity() - qty);
                } else {
                    it.remove();
                }
                notifyObservers("TOTAL_CHANGED", this);
                return true;
            }
        }
        return false;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem ci : items) total += ci.getSubtotal();
        return total;
    }

    public List<CartItem> getItems() { return Collections.unmodifiableList(items); }

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