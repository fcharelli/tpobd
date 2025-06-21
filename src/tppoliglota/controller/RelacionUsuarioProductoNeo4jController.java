package tppoliglota.controller;

import tppoliglota.dao.neo4j.RelacionUsuarioProductoNeo4jDAO;

public class RelacionUsuarioProductoNeo4jController {
    private final RelacionUsuarioProductoNeo4jDAO relacionDAO;

    public RelacionUsuarioProductoNeo4jController(RelacionUsuarioProductoNeo4jDAO relacionDAO) {
        this.relacionDAO = relacionDAO;
    }

    public void crearRelacionCompra(String usuarioId, String productoId) {
        relacionDAO.crearRelacionCompra(usuarioId, productoId);
    }
} 