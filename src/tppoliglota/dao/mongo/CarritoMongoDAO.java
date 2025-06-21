package tppoliglota.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import tppoliglota.model.Carrito;

import static com.mongodb.client.model.Filters.eq;

public class CarritoMongoDAO {
    private final MongoCollection<Carrito> collection;

    public CarritoMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("carritos", Carrito.class);
    }

    public void guardar(Carrito carrito) {
        collection.insertOne(carrito);
    }

    public Carrito buscarPorId(String id) {
        return collection.find(eq("_id", id)).first();
    }

    public Carrito buscarPorUsuario(String usuarioId) {
        return collection.find(eq("usuarioId", usuarioId)).first();
    }

    public void actualizar(Carrito carrito) {
        collection.replaceOne(eq("_id", carrito.getId()), carrito);
    }

    public void eliminar(String id) {
        collection.deleteOne(eq("_id", id));
    }
} 