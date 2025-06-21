package tppoliglota.model;

import java.util.Date;

public class Factura {
    private String id;
    private String pedidoId;
    private String usuarioId;
    private String cuit;
    private String iva;
    private String domicilioFiscal;
    private Date fecha;
    private double total;
    private double impuestos;
    private String formaPago;
    private String datosPago;

    public Factura() {
    }

    public Factura(String id, String pedidoId, String usuarioId, String cuit, String iva, String domicilioFiscal, Date fecha, double total, double impuestos, String formaPago, String datosPago) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.usuarioId = usuarioId;
        this.cuit = cuit;
        this.iva = iva;
        this.domicilioFiscal = domicilioFiscal;
        this.fecha = fecha;
        this.total = total;
        this.impuestos = impuestos;
        this.formaPago = formaPago;
        this.datosPago = datosPago;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public String getIva() { return iva; }
    public void setIva(String iva) { this.iva = iva; }
    public String getDomicilioFiscal() { return domicilioFiscal; }
    public void setDomicilioFiscal(String domicilioFiscal) { this.domicilioFiscal = domicilioFiscal; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public double getImpuestos() { return impuestos; }
    public void setImpuestos(double impuestos) { this.impuestos = impuestos; }
    public String getFormaPago() { return formaPago; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }
    public String getDatosPago() { return datosPago; }
    public void setDatosPago(String datosPago) { this.datosPago = datosPago; }
} 