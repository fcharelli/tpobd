package tppoliglota.model;

import java.util.Date;

public class Actividad {
    private String id;
    private String usuarioId;
    private Date timestamp;
    private String tipoActividad; // conexion, desconexion, agregar_carrito, compra
    private String detalles; // JSON o texto con información adicional

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public String getTipoActividad() { return tipoActividad; }
    public void setTipoActividad(String tipoActividad) { this.tipoActividad = tipoActividad; }
    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }
} 