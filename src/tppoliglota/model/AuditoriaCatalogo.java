package tppoliglota.model;

import java.util.Date;
import java.util.UUID;

public class AuditoriaCatalogo {
    private String id;
    private UUID productoId;
    private Date timestamp;
    private String campoModificado; // precio, imagen, video, descripcion, etc.
    private String valorAnterior;
    private String valorNuevo;
    private String operadorId; // usuario que realizó el cambio

    public AuditoriaCatalogo() {}

    public AuditoriaCatalogo(UUID productoId, Date timestamp, String campoModificado, String valorAnterior, String valorNuevo, String operadorId) {
        this.productoId = productoId;
        this.timestamp = timestamp;
        this.campoModificado = campoModificado;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.operadorId = operadorId;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public String getCampoModificado() { return campoModificado; }
    public void setCampoModificado(String campoModificado) { this.campoModificado = campoModificado; }
    public String getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(String valorAnterior) { this.valorAnterior = valorAnterior; }
    public String getValorNuevo() { return valorNuevo; }
    public void setValorNuevo(String valorNuevo) { this.valorNuevo = valorNuevo; }
    public String getOperadorId() { return operadorId; }
    public void setOperadorId(String operadorId) { this.operadorId = operadorId; }
} 