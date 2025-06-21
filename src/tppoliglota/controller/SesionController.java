package tppoliglota.controller;

import tppoliglota.dao.redis.SesionUsuarioRedisDAO;
import tppoliglota.dao.cassandra.ActividadCassandraDAO;
import tppoliglota.model.SesionUsuario;
import tppoliglota.model.Actividad;

import java.util.Date;

public class SesionController {
    private final SesionUsuarioRedisDAO sesionDAO;
    private final ActividadCassandraDAO actividadDAO;

    public SesionController(SesionUsuarioRedisDAO sesionDAO, ActividadCassandraDAO actividadDAO) {
        this.sesionDAO = sesionDAO;
        this.actividadDAO = actividadDAO;
    }

    public void iniciarSesion(String usuarioId, String rol) {
        SesionUsuario sesion = new SesionUsuario(usuarioId, rol, new Date());
        sesionDAO.guardarSesion(sesion);
    }

    public void cerrarSesion(String usuarioId) {
        sesionDAO.eliminarSesion(usuarioId);
    }

    public void guardarSesion(SesionUsuario sesion) {
        sesionDAO.guardarSesion(sesion);
    }

    public SesionUsuario buscarSesionPorUsuarioId(String usuarioId) {
        return sesionDAO.buscarSesionPorUsuarioId(usuarioId);
    }

    public void registrarActividad(Actividad actividad) {
        actividadDAO.guardarActividad(actividad);
    }
} 