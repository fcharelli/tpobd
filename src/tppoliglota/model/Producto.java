package tppoliglota.model;

import java.util.List;

public class Producto {
    private String id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private int stock;
    private List<String> imagenes;
    private List<String> videos;
    private double precioActual;

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public List<String> getImagenes() { return imagenes; }
    public void setImagenes(List<String> imagenes) { this.imagenes = imagenes; }
    public List<String> getVideos() { return videos; }
    public void setVideos(List<String> videos) { this.videos = videos; }
    public double getPrecioActual() { return precioActual; }
    public void setPrecioActual(double precioActual) { this.precioActual = precioActual; }
} 