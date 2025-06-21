package tppoliglota.dao.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import tppoliglota.model.Actividad;
import java.time.Instant;

public class ActividadCassandraDAO {
    private final CqlSession session;

    public ActividadCassandraDAO(CqlSession session) {
        this.session = session;
    }

    public void guardarActividad(Actividad actividad) {
        session.execute(
            "INSERT INTO actividad_usuario (usuario_id, timestamp, tipo_actividad, detalles) VALUES (?, ?, ?, ?)",
            actividad.getUsuarioId(),
            Instant.ofEpochMilli(actividad.getTimestamp().getTime()),
            actividad.getTipoActividad(),
            actividad.getDetalles()
        );
    }
} 