package tppoliglota.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import tppoliglota.model.HistorialPrecio;
import java.util.Date;

public class HistorialPrecioMongoDAO {
    private final MongoCollection<Document> coleccion;

    public HistorialPrecioMongoDAO(MongoDatabase db) {
        this.coleccion = db.getCollection("historial_precios");
    }

    public void guardar(HistorialPrecio historial) {
        Document doc = new Document("_id", historial.getId())
                .append("productoId", historial.getProductoId())
                .append("precio", historial.getPrecio())
                .append("fechaInicio", historial.getFechaInicio())
                .append("fechaFin", historial.getFechaFin())
                .append("operadorId", historial.getOperadorId());
        coleccion.insertOne(doc);
    }

    public HistorialPrecio buscarPorId(String id) {
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) return null;
        HistorialPrecio historial = new HistorialPrecio();
        historial.setId(doc.getString("_id"));
        historial.setProductoId(doc.getString("productoId"));
        historial.setPrecio(doc.getDouble("precio"));
        historial.setFechaInicio(doc.getDate("fechaInicio"));
        historial.setFechaFin(doc.getDate("fechaFin"));
        historial.setOperadorId(doc.getString("operadorId"));
        return historial;
    }

    public void actualizar(HistorialPrecio historial) {
        coleccion.replaceOne(Filters.eq("_id", historial.getId()), new Document()
                .append("_id", historial.getId())
                .append("productoId", historial.getProductoId())
                .append("precio", historial.getPrecio())
                .append("fechaInicio", historial.getFechaInicio())
                .append("fechaFin", historial.getFechaFin())
                .append("operadorId", historial.getOperadorId()));
    }

    public void eliminar(String id) {
        coleccion.deleteOne(Filters.eq("_id", id));
    }
} 