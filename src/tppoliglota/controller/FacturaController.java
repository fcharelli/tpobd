package tppoliglota.controller;

import tppoliglota.dao.mongo.FacturaMongoDAO;
import tppoliglota.model.Factura;

import java.util.List;

public class FacturaController {
    private FacturaMongoDAO facturaDAO;

    public FacturaController(FacturaMongoDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    public void generarFactura(Factura factura) {
        facturaDAO.guardar(factura);
    }

    public Factura buscarFacturaPorId(String id) {
        return facturaDAO.buscarPorId(id);
    }

    public List<Factura> obtenerFacturasPorUsuario(String usuarioId) {
        return facturaDAO.buscarPorUsuario(usuarioId);
    }
} 