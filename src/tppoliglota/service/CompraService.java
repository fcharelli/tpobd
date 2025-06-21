package tppoliglota.service;

import tppoliglota.controller.*;
import tppoliglota.model.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CompraService {
    private final CarritoController carritoController;
    private final PedidoController pedidoController;
    private final FacturaController facturaController;
    private final RelacionUsuarioProductoNeo4jController relacionNeo4jController;
    private final SesionController sesionController;

    public CompraService(
            CarritoController carritoController,
            PedidoController pedidoController,
            FacturaController facturaController,
            RelacionUsuarioProductoNeo4jController relacionNeo4jController,
            SesionController sesionController
    ) {
        this.carritoController = carritoController;
        this.pedidoController = pedidoController;
        this.facturaController = facturaController;
        this.relacionNeo4jController = relacionNeo4jController;
        this.sesionController = sesionController;
    }

    public void convertirCarritoAPedidoYFacturar(String usuarioId, String formaPago, String datosPago) {
        // 1. Obtener el carrito activo del usuario
        Carrito carrito = carritoController.buscarCarritoPorId(usuarioId);
        if (carrito == null || carrito.getProductos() == null || carrito.getProductos().isEmpty()) {
            throw new RuntimeException("El carrito está vacío.");
        }

        // 2. Crear el pedido a partir del carrito
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID().toString());
        pedido.setUsuarioId(usuarioId);
        pedido.setProductos(convertirItemsCarritoAPedido(carrito.getProductos()));
        pedido.setFecha(new Date());
        pedido.setEstado("pendiente");
        pedido.setTotal(calcularTotal(pedido.getProductos()));
        pedidoController.registrarPedido(pedido);

        // 3. Registrar la relación de compra en Neo4j
        for (ItemPedido item : pedido.getProductos()) {
            relacionNeo4jController.crearRelacionCompra(usuarioId, item.getProductoId());
        }

        // 4. Registrar la actividad en Cassandra
        Actividad actividad = new Actividad();
        actividad.setUsuarioId(usuarioId);
        actividad.setTipoActividad("compra");
        actividad.setTimestamp(new Date());
        actividad.setDetalles("Pedido ID: " + pedido.getId());
        sesionController.registrarActividad(actividad);

        // 5. Generar la factura
        Factura factura = new Factura(
            null, // El ID se genera en la BD
            pedido.getId(),
            usuarioId,
            "20-12345678-9", // CUIT de ejemplo
            "21%", // IVA de ejemplo
            "Av. Siempre Viva 742", // Domicilio fiscal de ejemplo
            new Date(),
            pedido.getTotal(),
            pedido.getTotal() * 0.21, // Impuestos de ejemplo
            "Tarjeta de Credito", // o el método que corresponda
            "************1234"
        );
        facturaController.generarFactura(factura);

        // 6. Limpiar el carrito
        carritoController.limpiarCarrito(usuarioId);
    }

    private List<ItemPedido> convertirItemsCarritoAPedido(List<ItemCarrito> itemsCarrito) {
        // Aquí deberías mapear cada ItemCarrito a ItemPedido, consultando precios, descuentos, etc.
        // Por simplicidad, se asume precio fijo y sin descuentos.
        // En la práctica, deberías consultar el precio actual del producto.
        return itemsCarrito.stream()
                .map(item -> new ItemPedido(item.getProductoId(), item.getCantidad(), 100.0, 0.0, 21.0))
                .toList();
    }

    private double calcularTotal(List<ItemPedido> items) {
        return items.stream()
                .mapToDouble(item -> (item.getPrecioUnitario() - item.getDescuento()) * item.getCantidad() + item.getImpuestos())
                .sum();
    }

    private double calcularImpuestos(double total) {
        // Lógica de impuestos según condición fiscal
        return total * 0.21; // Ejemplo: 21% IVA
    }
} 