package tppoliglota.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import tppoliglota.model.Factura;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;

public class FacturaMongoDAO {
    private MongoCollection<Factura> collection;

    public FacturaMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("facturas", Factura.class);
    }

    public void guardar(Factura factura) {
        collection.insertOne(factura);
    }

    public Factura buscarPorId(String id) {
        return collection.find(eq("_id", id)).first();
    }

    public void eliminar(String id) {
        collection.deleteOne(eq("_id", id));
    }

    public List<Factura> buscarPorUsuario(String usuarioId) {
        List<Factura> facturas = new ArrayList<>();
        MongoCursor<Factura> cursor = collection.find(eq("usuarioId", usuarioId)).iterator();
        try {
            while (cursor.hasNext()) {
                facturas.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return facturas;
    }
} 