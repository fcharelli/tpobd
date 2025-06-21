package tppoliglota.model;

import java.util.Date;
import java.util.List;

public class Carrito {
    private String id;
    private String usuarioId;
    private List<ItemCarrito> productos;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public List<ItemCarrito> getProductos() { return productos; }
    public void setProductos(List<ItemCarrito> productos) { this.productos = productos; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Date getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Date fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
} 