package tppoliglota.dao.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import tppoliglota.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMongoDAO {
    private final MongoCollection<Document> coleccion;

    public UsuarioMongoDAO(MongoDatabase db) {
        this.coleccion = db.getCollection("usuarios");
    }

    public void guardar(Usuario usuario) {
        Document doc = new Document("_id", usuario.getId())
                .append("nombre", usuario.getNombre())
                .append("apellido", usuario.getApellido())
                .append("direccion", usuario.getDireccion())
                .append("documento", usuario.getDocumento())
                .append("telefono", usuario.getTelefono())
                .append("mail", usuario.getMail())
                .append("email", usuario.getEmail())
                .append("rol", usuario.getRol())
                .append("cuit", usuario.getCuit())
                .append("iva", usuario.getIva())
                .append("domicilioFiscal", usuario.getDomicilioFiscal())
                .append("clave", usuario.getClave());
        coleccion.insertOne(doc);
    }

    public Usuario buscarPorId(String id) {
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) return null;
        return documentToUsuario(doc);
    }

    public Usuario buscarPorEmail(String email) {
        Document doc = coleccion.find(Filters.eq("email", email)).first();
        if (doc == null) return null;
        return documentToUsuario(doc);
    }

    public void actualizar(Usuario usuario) {
        coleccion.replaceOne(Filters.eq("_id", usuario.getId()), new Document()
                .append("_id", usuario.getId())
                .append("nombre", usuario.getNombre())
                .append("apellido", usuario.getApellido())
                .append("direccion", usuario.getDireccion())
                .append("documento", usuario.getDocumento())
                .append("telefono", usuario.getTelefono())
                .append("mail", usuario.getMail())
                .append("email", usuario.getEmail())
                .append("rol", usuario.getRol())
                .append("cuit", usuario.getCuit())
                .append("iva", usuario.getIva())
                .append("domicilioFiscal", usuario.getDomicilioFiscal())
                .append("clave", usuario.getClave()));
    }

    public void eliminar(String id) {
        coleccion.deleteOne(Filters.eq("_id", id));
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        for (Document doc : coleccion.find()) {
            usuarios.add(documentToUsuario(doc));
        }
        return usuarios;
    }

    private Usuario documentToUsuario(Document doc) {
        Usuario usuario = new Usuario();
        usuario.setId(doc.getString("_id"));
        usuario.setNombre(doc.getString("nombre"));
        usuario.setApellido(doc.getString("apellido"));
        usuario.setDireccion(doc.getString("direccion"));
        usuario.setDocumento(doc.getString("documento"));
        usuario.setTelefono(doc.getString("telefono"));
        usuario.setMail(doc.getString("mail"));
        usuario.setEmail(doc.getString("email"));
        usuario.setRol(doc.getString("rol"));
        usuario.setCuit(doc.getString("cuit"));
        usuario.setIva(doc.getString("iva"));
        usuario.setDomicilioFiscal(doc.getString("domicilioFiscal"));
        usuario.setClave(doc.getString("clave"));
        return usuario;
    }
} 