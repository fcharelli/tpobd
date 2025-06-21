package tppoliglota.dao.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import tppoliglota.model.AuditoriaCatalogo;
import java.time.Instant;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AuditoriaCatalogoCassandraDAO {
    private final CqlSession session;

    public AuditoriaCatalogoCassandraDAO(CqlSession session) {
        this.session = session;
    }

    public void guardarAuditoria(AuditoriaCatalogo auditoria) {
        session.execute(
            "INSERT INTO auditoria_catalogo (producto_id, timestamp, campo_modificado, valor_anterior, valor_nuevo, operador_id) VALUES (?, ?, ?, ?, ?, ?)",
            auditoria.getProductoId(),
            Instant.ofEpochMilli(auditoria.getTimestamp().getTime()),
            auditoria.getCampoModificado(),
            auditoria.getValorAnterior(),
            auditoria.getValorNuevo(),
            auditoria.getOperadorId()
        );
    }
    
    public List<AuditoriaCatalogo> obtenerTodos() {
        List<AuditoriaCatalogo> auditorias = new ArrayList<>();
        ResultSet rs = session.execute("SELECT producto_id, timestamp, campo_modificado, valor_anterior, valor_nuevo, operador_id FROM auditoria_catalogo");
        for (Row row : rs) {
            auditorias.add(new AuditoriaCatalogo(
                row.getUuid("producto_id"),
                Date.from(row.getInstant("timestamp")),
                row.getString("campo_modificado"),
                row.getString("valor_anterior"),
                row.getString("valor_nuevo"),
                row.getString("operador_id")
            ));
        }
        return auditorias;
    }
} 