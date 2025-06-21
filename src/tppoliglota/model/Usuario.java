package tppoliglota.model;

public class Usuario {
    private String id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String documento;
    private String telefono;
    private String mail;
    private String email;
    private String rol;
    private String cuit;
    private String iva;
    private String domicilioFiscal;
    private String clave;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public String getIva() { return iva; }
    public void setIva(String iva) { this.iva = iva; }
    public String getDomicilioFiscal() { return domicilioFiscal; }
    public void setDomicilioFiscal(String domicilioFiscal) { this.domicilioFiscal = domicilioFiscal; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
} 