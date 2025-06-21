package tppoliglota.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import tppoliglota.model.Pedido;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;

public class PedidoMongoDAO extends MongoConnection {
    private MongoCollection<Pedido> collection;

    public PedidoMongoDAO() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("pedidos", Pedido.class);
    }

    public void guardar(Pedido pedido) {
        collection.insertOne(pedido);
    }

    public Pedido buscarPorId(String id) {
        return collection.find(eq("_id", id)).first();
    }

    public void actualizar(Pedido pedido) {
        collection.replaceOne(eq("_id", pedido.getId()), pedido);
    }

    public void eliminar(String id) {
        collection.deleteOne(eq("_id", id));
    }

    public List<Pedido> buscarPorUsuario(String usuarioId) {
        List<Pedido> pedidos = new ArrayList<>();
        try (MongoCursor<Pedido> cursor = collection.find(eq("usuarioId", usuarioId)).iterator()) {
            while (cursor.hasNext()) {
                pedidos.add(cursor.next());
            }
        }
        return pedidos;
    }
} 