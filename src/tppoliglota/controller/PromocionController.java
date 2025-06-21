package tppoliglota.controller;

import tppoliglota.dao.mongo.PromocionMongoDAO;
import tppoliglota.model.Promocion;

public class PromocionController {
    private final PromocionMongoDAO promocionDAO;

    public PromocionController(PromocionMongoDAO promocionDAO) {
        this.promocionDAO = promocionDAO;
    }

    public void registrarPromocion(Promocion promocion) {
        promocionDAO.guardar(promocion);
    }

    public Promocion buscarPromocionPorId(String id) {
        return promocionDAO.buscarPorId(id);
    }

    public void actualizarPromocion(Promocion promocion) {
        promocionDAO.actualizar(promocion);
    }

    public void eliminarPromocion(String id) {
        promocionDAO.eliminar(id);
    }
} 