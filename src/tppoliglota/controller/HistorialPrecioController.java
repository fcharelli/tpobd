package tppoliglota.controller;

import tppoliglota.dao.mongo.HistorialPrecioMongoDAO;
import tppoliglota.model.HistorialPrecio;

public class HistorialPrecioController {
    private final HistorialPrecioMongoDAO historialPrecioDAO;

    public HistorialPrecioController(HistorialPrecioMongoDAO historialPrecioDAO) {
        this.historialPrecioDAO = historialPrecioDAO;
    }

    public void registrarHistorial(HistorialPrecio historial) {
        historialPrecioDAO.guardar(historial);
    }

    public HistorialPrecio buscarHistorialPorId(String id) {
        return historialPrecioDAO.buscarPorId(id);
    }

    public void actualizarHistorial(HistorialPrecio historial) {
        historialPrecioDAO.actualizar(historial);
    }

    public void eliminarHistorial(String id) {
        historialPrecioDAO.eliminar(id);
    }
} 