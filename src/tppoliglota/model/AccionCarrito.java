package tppoliglota.model;

import java.util.Date;

public class AccionCarrito {
    private String tipoAccion; // agregar, eliminar, modificar
    private String productoId;
    private int cantidadAnterior;
    private int cantidadNueva;
    private Date timestamp;
    private String tipo; // "agregar" o "quitar"
    private ItemCarrito item;

    public AccionCarrito(String tipoAccion, String productoId, int cantidadAnterior, int cantidadNueva, Date timestamp) {
        this.tipoAccion = tipoAccion;
        this.productoId = productoId;
        this.cantidadAnterior = cantidadAnterior;
        this.cantidadNueva = cantidadNueva;
        this.timestamp = timestamp;
    }

    public String getTipoAccion() { return tipoAccion; }
    public void setTipoAccion(String tipoAccion) { this.tipoAccion = tipoAccion; }
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }
    public int getCantidadAnterior() { return cantidadAnterior; }
    public void setCantidadAnterior(int cantidadAnterior) { this.cantidadAnterior = cantidadAnterior; }
    public int getCantidadNueva() { return cantidadNueva; }
    public void setCantidadNueva(int cantidadNueva) { this.cantidadNueva = cantidadNueva; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getTipo() {
        return tipo;
    }

    public ItemCarrito getItem() {
        return item;
    }
} 