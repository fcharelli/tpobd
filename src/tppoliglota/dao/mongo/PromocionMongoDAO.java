package tppoliglota.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import tppoliglota.model.Promocion;
import java.util.Date;

public class PromocionMongoDAO {
    private final MongoCollection<Document> coleccion;

    public PromocionMongoDAO(MongoDatabase db) {
        this.coleccion = db.getCollection("promociones");
    }

    public void guardar(Promocion promocion) {
        Document doc = new Document("_id", promocion.getId())
                .append("productoId", promocion.getProductoId())
                .append("descripcion", promocion.getDescripcion())
                .append("descuento", promocion.getDescuento())
                .append("fechaInicio", promocion.getFechaInicio())
                .append("fechaFin", promocion.getFechaFin())
                .append("operadorId", promocion.getOperadorId());
        coleccion.insertOne(doc);
    }

    public Promocion buscarPorId(String id) {
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) return null;
        Promocion promocion = new Promocion();
        promocion.setId(doc.getString("_id"));
        promocion.setProductoId(doc.getString("productoId"));
        promocion.setDescripcion(doc.getString("descripcion"));
        promocion.setDescuento(doc.getDouble("descuento"));
        promocion.setFechaInicio(doc.getDate("fechaInicio"));
        promocion.setFechaFin(doc.getDate("fechaFin"));
        promocion.setOperadorId(doc.getString("operadorId"));
        return promocion;
    }

    public void actualizar(Promocion promocion) {
        coleccion.replaceOne(Filters.eq("_id", promocion.getId()), new Document()
                .append("_id", promocion.getId())
                .append("productoId", promocion.getProductoId())
                .append("descripcion", promocion.getDescripcion())
                .append("descuento", promocion.getDescuento())
                .append("fechaInicio", promocion.getFechaInicio())
                .append("fechaFin", promocion.getFechaFin())
                .append("operadorId", promocion.getOperadorId()));
    }

    public void eliminar(String id) {
        coleccion.deleteOne(Filters.eq("_id", id));
    }
} 