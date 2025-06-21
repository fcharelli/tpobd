package tppoliglota.dao.neo4j;

import org.neo4j.driver.*;

public class RelacionUsuarioProductoNeo4jDAO {
    private final Driver driver;

    public RelacionUsuarioProductoNeo4jDAO(Driver driver) {
        this.driver = driver;
    }

    public void crearRelacionCompra(String usuarioId, String productoId) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run(
                """
                MERGE (u:Usuario {id: $usuarioId})
                MERGE (p:Producto {id: $productoId})
                MERGE (u)-[:COMPRO]->(p)
                """,
                Values.parameters("usuarioId", usuarioId, "productoId", productoId)
            ));
        }
    }
} 