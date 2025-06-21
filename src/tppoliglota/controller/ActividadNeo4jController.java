package tppoliglota.controller;

import tppoliglota.dao.neo4j.ActividadNeo4jDAO;
import tppoliglota.model.Actividad;

public class ActividadNeo4jController {
    private final ActividadNeo4jDAO actividadDAO;

    public ActividadNeo4jController(ActividadNeo4jDAO actividadDAO) {
        this.actividadDAO = actividadDAO;
    }

    public void registrarActividad(Actividad actividad) {
        actividadDAO.guardarActividad(actividad);
    }
} 