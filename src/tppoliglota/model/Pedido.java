package tppoliglota.model;

import java.util.Date;
import java.util.List;

public class Pedido {
    private String id;
    private String usuarioId;
    private List<ItemPedido> productos;
    private Date fecha;
    private String estado;
    private double total;

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public List<ItemPedido> getProductos() { return productos; }
    public void setProductos(List<ItemPedido> productos) { this.productos = productos; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
} 