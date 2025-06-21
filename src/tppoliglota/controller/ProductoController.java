package tppoliglota.controller;

import tppoliglota.dao.mongo.ProductoMongoDAO;
import tppoliglota.model.Producto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductoController {
    private final ProductoMongoDAO productoDAO;

    public ProductoController(ProductoMongoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public void registrarProducto(Producto producto) {
        productoDAO.guardar(producto);
    }

    public Producto buscarProductoPorId(String id) {
        return productoDAO.buscarPorId(id);
    }

    public void actualizarProducto(Producto producto) {
        productoDAO.actualizar(producto);
    }

    public void eliminarProducto(String id) {
        productoDAO.eliminar(id);
    }

    public List<Producto> obtenerTodos() {
        return productoDAO.obtenerTodos();
    }

    public Set<String> obtenerCategorias() {
        return obtenerTodos().stream().map(Producto::getCategoria).collect(Collectors.toSet());
    }

    public Producto buscarPorNombre(String nombre) {
        return obtenerTodos().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }
} 