# Tienda Online - Patrones de Diseño

## 1. Patrones aplicados
- **Observer**: Cart e Inventory son sujetos; CartTotalObserver y InventoryObserver reciben notificaciones (total cambiado, producto agotado).
- **Decorator**: ProductDecorator y sus concretos (GiftWrapDecorator, WarrantyDecorator, DiscountDecorator) permiten agregar características a productos sin modificar clases base.
- **Facade**: CheckoutFacade encapsula validación de stock, cobro (PaymentService) y generación de recibo (ReceiptService).
- **Singleton**: AppLogger es una instancia única para registrar eventos.

## 2. Cómo compilar y ejecutar
Desde la raíz del proyecto

## 3. capturas
img/1.png - Notificaciones del Observer.
img/2.png - Aplicación de decoradores, Ejecución de la fachada de checkout.

# 5) Reflexión ejemplo (para README)
> El patrón que me resultó más complicado de aplicar fue **Decorator** en el carrito porque los CartItem deben mantener la identidad del producto (ID) mientras su descripción
> y precio cambian. Tuve que asegurar que el decorador **preserve getId()** para que el inventario y las operaciones posteriores puedan identificar correctamente el producto.
> Los patrones trabajan juntos: el **Singleton** (logger) registra eventos que ocurren en el **Facade** (checkout); la **Facade** consulta el **Inventory**; **Observer** notifica
> cambios en Cart e Inventory; y **Decorator** añade características a Product sin romper la lógica de inventario ni el flujo de checkout.

