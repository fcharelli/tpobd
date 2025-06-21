package tppoliglota.dao.neo4j;

import org.neo4j.driver.*;
import tppoliglota.model.Actividad;

public class ActividadNeo4jDAO {
    private final Driver driver;

    public ActividadNeo4jDAO(Driver driver) {
        this.driver = driver;
    }

    public void guardarActividad(Actividad actividad) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run(
                "CREATE (a:Actividad {id: $id, usuarioId: $usuarioId, tipo: $tipo, timestamp: $timestamp, detalles: $detalles})",
                Values.parameters(
                    "id", actividad.getId(),
                    "usuarioId", actividad.getUsuarioId(),
                    "tipo", actividad.getTipoActividad(),
                    "timestamp", actividad.getTimestamp().toString(),
                    "detalles", actividad.getDetalles()
                )
            ));
        }
    }
} 