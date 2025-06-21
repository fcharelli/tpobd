package tppoliglota.controller;

import tppoliglota.dao.mongo.CarritoMongoDAO;
import tppoliglota.dao.redis.AccionCarritoRedisDAO;
import tppoliglota.model.Carrito;
import tppoliglota.model.AccionCarrito;
import tppoliglota.model.ItemCarrito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CarritoController {
    private final CarritoMongoDAO carritoDAO;
    private final AccionCarritoRedisDAO accionCarritoDAO;

    public CarritoController(CarritoMongoDAO carritoDAO, AccionCarritoRedisDAO accionCarritoDAO) {
        this.carritoDAO = carritoDAO;
        this.accionCarritoDAO = accionCarritoDAO;
    }

    private Carrito obtenerOCrearCarrito(String usuarioId) {
        Carrito carrito = carritoDAO.buscarPorUsuario(usuarioId);
        if (carrito == null) {
            carrito = new Carrito();
            // El ID del carrito puede ser el mismo que el del usuario para simplicidad
            carrito.setId(usuarioId);
            carrito.setUsuarioId(usuarioId);
            carrito.setProductos(new ArrayList<>());
            carrito.setFechaCreacion(new Date());
            carritoDAO.guardar(carrito); // Guardamos el carrito nuevo
        }
        return carrito;
    }

    public void guardarCarrito(Carrito carrito) {
        carritoDAO.guardar(carrito);
    }

    public Carrito buscarCarritoPorId(String id) {
        return carritoDAO.buscarPorId(id);
    }

    public void actualizarCarrito(Carrito carrito) {
        carritoDAO.actualizar(carrito);
    }

    public void eliminarCarrito(String id) {
        carritoDAO.eliminar(id);
    }

    public void registrarAccionCarrito(String usuarioId, AccionCarrito accion) {
        accionCarritoDAO.guardarAccion(usuarioId, accion);
    }

    public List<AccionCarrito> obtenerHistorialAcciones(String usuarioId) {
        return accionCarritoDAO.obtenerHistorial(usuarioId);
    }

    public void limpiarHistorialAcciones(String usuarioId) {
        accionCarritoDAO.limpiarHistorial(usuarioId);
    }

    public void agregarProductoAlCarrito(String usuarioId, String productoId, int cantidad) {
        Carrito carrito = obtenerOCrearCarrito(usuarioId);
        List<ItemCarrito> items = carrito.getProductos();
        Optional<ItemCarrito> itemExistente = items.stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            int cantidadAnterior = item.getCantidad();
            item.setCantidad(cantidadAnterior + cantidad);
            registrarAccionCarrito(usuarioId, "modificar", productoId, cantidadAnterior, item.getCantidad());
        } else {
            ItemCarrito nuevoItem = new ItemCarrito(productoId, cantidad);
            items.add(nuevoItem);
            registrarAccionCarrito(usuarioId, "agregar", productoId, 0, cantidad);
        }

        carrito.setFechaActualizacion(new Date());
        carritoDAO.actualizar(carrito);
    }

    public void quitarProductoDelCarrito(String usuarioId, String productoId) {
        Carrito carrito = carritoDAO.buscarPorUsuario(usuarioId);
        if (carrito != null) {
            List<ItemCarrito> items = carrito.getProductos();
            Optional<ItemCarrito> itemParaQuitar = items.stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .findFirst();

            if (itemParaQuitar.isPresent()) {
                ItemCarrito item = itemParaQuitar.get();
                items.remove(item);
                registrarAccionCarrito(usuarioId, "quitar", productoId, item.getCantidad(), 0);
                carrito.setFechaActualizacion(new Date());
                carritoDAO.actualizar(carrito);
            }
        }
    }

    public void deshacerUltimaAccion(String usuarioId) {
        AccionCarrito ultimaAccion = accionCarritoDAO.obtenerYRemoverUltimaAccion(usuarioId);
        if (ultimaAccion == null) return;

        Carrito carrito = obtenerOCrearCarrito(usuarioId);
        List<ItemCarrito> items = carrito.getProductos();

        String productoId = ultimaAccion.getProductoId();

        switch (ultimaAccion.getTipo()) {
            case "agregar":
                // Si la acción fue agregar, la deshacemos quitando el item.
                items.removeIf(item -> item.getProductoId().equals(productoId));
                break;
            case "quitar":
                // Si la acción fue quitar, la deshacemos agregando el item de nuevo con su cantidad anterior.
                items.add(new ItemCarrito(productoId, ultimaAccion.getCantidadAnterior()));
                break;
            case "modificar":
                // Si la acción fue modificar, restauramos la cantidad anterior.
                items.stream()
                    .filter(item -> item.getProductoId().equals(productoId))
                    .findFirst()
                    .ifPresent(item -> item.setCantidad(ultimaAccion.getCantidadAnterior()));
                break;
        }

        carrito.setFechaActualizacion(new Date());
        carritoDAO.actualizar(carrito);
    }

    public void limpiarCarrito(String usuarioId) {
        Carrito carrito = carritoDAO.buscarPorUsuario(usuarioId);
        if (carrito != null) {
            // Registramos la eliminación de cada item para poder deshacerla si es necesario
            for(ItemCarrito item : carrito.getProductos()){
                registrarAccionCarrito(usuarioId, "quitar", item.getProductoId(), item.getCantidad(), 0);
            }
            carrito.getProductos().clear();
            carrito.setFechaActualizacion(new Date());
            carritoDAO.actualizar(carrito);
        }
    }

    public Carrito obtenerCarrito(String usuarioId) {
        return carritoDAO.buscarPorUsuario(usuarioId);
    }
    
    private void registrarAccionCarrito(String usuarioId, String tipo, String productoId, int cantAnterior, int cantNueva) {
        AccionCarrito accion = new AccionCarrito(tipo, productoId, cantAnterior, cantNueva, new Date());
        accionCarritoDAO.guardarAccion(usuarioId, accion);
    }
} 