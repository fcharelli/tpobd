package tppoliglota.controller;

import tppoliglota.dao.mongo.PedidoMongoDAO;
import tppoliglota.model.Pedido;

import java.util.List;

public class PedidoController {
    private final PedidoMongoDAO pedidoDAO;

    public PedidoController(PedidoMongoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public void registrarPedido(Pedido pedido) {
        pedidoDAO.guardar(pedido);
    }

    public Pedido buscarPedidoPorId(String id) {
        return pedidoDAO.buscarPorId(id);
    }

    public List<Pedido> buscarPorUsuario(String usuarioId) {
        return pedidoDAO.buscarPorUsuario(usuarioId);
    }

    public void actualizarPedido(Pedido pedido) {
        pedidoDAO.actualizar(pedido);
    }

    public void eliminarPedido(String id) {
        pedidoDAO.eliminar(id);
    }
} 