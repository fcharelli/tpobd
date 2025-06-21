package tppoliglota.controller;

import tppoliglota.dao.cassandra.AuditoriaCatalogoCassandraDAO;
import tppoliglota.model.AuditoriaCatalogo;

import java.util.List;

public class AuditoriaController {
    private final AuditoriaCatalogoCassandraDAO auditoriaDAO;

    public AuditoriaController(AuditoriaCatalogoCassandraDAO auditoriaDAO) {
        this.auditoriaDAO = auditoriaDAO;
    }

    public void registrarAuditoria(AuditoriaCatalogo auditoria) {
        auditoriaDAO.guardarAuditoria(auditoria);
    }

    public List<AuditoriaCatalogo> obtenerAuditoriaCatalogo() {
        return auditoriaDAO.obtenerTodos();
    }
} 