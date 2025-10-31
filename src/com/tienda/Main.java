package com.tienda;

import com.tienda.cart.Cart;
import com.tienda.inventory.Inventory;
import com.tienda.model.SimpleProduct;
import com.tienda.model.Product;
import com.tienda.model.decorators.*;
import com.tienda.observer.*;
import com.tienda.facade.CheckoutFacade;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppLogger logger = AppLogger.getInstance();
        Inventory inventory = new Inventory();
        Cart cart = new Cart();

        // Observers
        CartTotalObserver cartObserver = new CartTotalObserver();
        InventoryObserver inventoryObserver = new InventoryObserver();
        cart.addObserver(cartObserver);
        inventory.addObserver(inventoryObserver);

        // Poblamos inventario
        SimpleProduct p1 = new SimpleProduct(1, "Laptop", 6500.00);
        SimpleProduct p2 = new SimpleProduct(2, "Mouse", 150.00);
        SimpleProduct p3 = new SimpleProduct(3, "Teclado", 300.00);

        inventory.addProduct(p1, 5);
        inventory.addProduct(p2, 10);
        inventory.addProduct(p3, 3);

        CheckoutFacade facade = new CheckoutFacade(inventory);

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n=== TIENDA ONLINE ===");
            System.out.println("1. Listar productos");
            System.out.println("2. Agregar producto al carrito");
            System.out.println("3. Eliminar producto del carrito");
            System.out.println("4. Ver carrito");
            System.out.println("5. Aplicar decorador a producto en carrito");
            System.out.println("6. Checkout");
            System.out.println("7. Salir");
            System.out.print("Opcion: ");
            int opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    System.out.println("--- Productos ---");
                    for (var prod : inventory.listProducts()) {
                        System.out.println(prod.getId() + ". " + prod.getDescription() + " - Q" + prod.getCost() + " (stock: " + inventory.getStock(prod.getId()) + ")");
                    }
                    break;
                case 2:
                    System.out.print("ID producto a agregar: ");
                    int idA = Integer.parseInt(sc.nextLine());
                    Product prodA = inventory.getProductById(idA);
                    if (prodA == null) { System.out.println("Producto no existe."); break; }
                    System.out.print("Cantidad: ");
                    int qtyA = Integer.parseInt(sc.nextLine());
                    if (inventory.getStock(idA) < qtyA) {
                        System.out.println("Stock insuficiente.");
                    } else {
                        cart.addProduct(prodA, qtyA);
                        System.out.println("Producto agregado al carrito.");
                        logger.info("Agregado al carrito: " + prodA.getDescription() + " x" + qtyA);
                    }
                    break;
                case 3:
                    System.out.print("ID producto a remover: ");
                    int idR = Integer.parseInt(sc.nextLine());
                    System.out.print("Cantidad a remover: ");
                    int qtyR = Integer.parseInt(sc.nextLine());
                    boolean removed = cart.removeProduct(idR, qtyR);
                    System.out.println(removed ? "Removido." : "Producto no encontrado en carrito.");
                    break;
                case 4:
                    System.out.println("--- Carrito ---");
                    if (cart.getItems().isEmpty()) System.out.println("Carrito vacío.");
                    for (var it : cart.getItems()) {
                        System.out.println(it.getProduct().getId() + ". " + it.getProduct().getDescription() + " x" + it.getQuantity() + " - Q" + String.format("%.2f", it.getSubtotal()));
                    }
                    System.out.println("TOTAL: Q" + String.format("%.2f", cart.getTotal()));
                    break;
                case 5:
                    System.out.print("ID producto en carrito a decorar: ");
                    int idD = Integer.parseInt(sc.nextLine());
                    var found = cart.getItems().stream().filter(i -> i.getProduct().getId() == idD).findFirst();
                    if (found.isEmpty()) { System.out.println("No esta en carrito."); break; }
                    var item = found.get();
                    Product current = item.getProduct();
                    System.out.println("1. Envoltorio (+Q5.00)\n2. Garantia (+10%)\n3. Descuento (%)");
                    System.out.print("Opcion decorador: ");
                    int od = Integer.parseInt(sc.nextLine());
                    if (od == 1) current = new GiftWrapDecorator(current);
                    else if (od == 2) current = new WarrantyDecorator(current);
                    else if (od == 3) {
                        System.out.print("Porcentaje (ej: 10 para 10%): ");
                        double pct = Double.parseDouble(sc.nextLine()) / 100.0;
                        current = new DiscountDecorator(current, pct);
                    } else { System.out.println("Opcion invalida."); break; }
                    item = new com.tienda.cart.CartItem(current, item.getQuantity());
                   
                    var items = new java.util.ArrayList<>(cart.getItems());
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).getProduct().getId() == idD) {
                            items.set(i, item);
                            break;
                        }
                    }
                    
                    var oldObservers = new java.util.ArrayList<>( ((com.tienda.observer.Subject)cart).getClass().isInstance(cart) ? new java.util.ArrayList<>() : new java.util.ArrayList<>() );
                    System.out.println("Decorador aplicado. Nota: El ID permanece igual; descripcion y precio actualizados.");
                    cart.removeProduct(idD, item.getQuantity());
                    cart.addProduct(current, item.getQuantity());
                    break;
                case 6:
                    System.out.println("Iniciando checkout...");
                    boolean ok = facade.checkout(cart);
                    if (ok) {
                        logger.info("Checkout exitoso.");
                        cart = new Cart();
                        cart.addObserver(cartObserver);
                    } else {
                        System.out.println("Checkout fallo.");
                    }
                    break;
                case 7:
                    running = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        sc.close();
    }
}