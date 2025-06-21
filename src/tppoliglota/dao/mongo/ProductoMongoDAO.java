package tppoliglota.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import tppoliglota.model.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoMongoDAO {
    private final MongoCollection<Document> coleccion;

    public ProductoMongoDAO(MongoDatabase db) {
        this.coleccion = db.getCollection("productos");
    }

    public void guardar(Producto producto) {
        Document doc = new Document("_id", producto.getId())
                .append("codigo", producto.getCodigo())
                .append("nombre", producto.getNombre())
                .append("descripcion", producto.getDescripcion())
                .append("categoria", producto.getCategoria())
                .append("stock", producto.getStock())
                .append("imagenes", producto.getImagenes())
                .append("videos", producto.getVideos())
                .append("precioActual", producto.getPrecioActual());
        coleccion.insertOne(doc);
    }

    public Producto buscarPorId(String id) {
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) return null;
        return documentToProducto(doc);
    }

    public void actualizar(Producto producto) {
        coleccion.replaceOne(Filters.eq("_id", producto.getId()), new Document()
                .append("_id", producto.getId())
                .append("codigo", producto.getCodigo())
                .append("nombre", producto.getNombre())
                .append("descripcion", producto.getDescripcion())
                .append("categoria", producto.getCategoria())
                .append("stock", producto.getStock())
                .append("imagenes", producto.getImagenes())
                .append("videos", producto.getVideos())
                .append("precioActual", producto.getPrecioActual()));
    }

    public void eliminar(String id) {
        coleccion.deleteOne(Filters.eq("_id", id));
    }

    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        for (Document doc : coleccion.find()) {
            productos.add(documentToProducto(doc));
        }
        return productos;
    }

    public Producto buscarPorNombre(String nombre) {
        Document doc = coleccion.find(Filters.eq("nombre", nombre)).first();
        if (doc == null) return null;
        return documentToProducto(doc);
    }

    private Producto documentToProducto(Document doc) {
        Producto producto = new Producto();
        producto.setId(doc.getString("_id"));
        producto.setCodigo(doc.getString("codigo"));
        producto.setNombre(doc.getString("nombre"));
        producto.setDescripcion(doc.getString("descripcion"));
        producto.setCategoria(doc.getString("categoria"));
        producto.setStock(doc.getInteger("stock", 0));
        producto.setImagenes((List<String>) doc.get("imagenes", List.class));
        producto.setVideos((List<String>) doc.get("videos", List.class));
        producto.setPrecioActual(doc.getDouble("precioActual"));
        return producto;
    }
} 