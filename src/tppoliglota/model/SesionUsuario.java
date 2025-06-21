package tppoliglota.model;

import java.util.Date;
import java.util.List;

public class SesionUsuario {
    private String usuarioId;
    private String nombreUsuario;
    private Date horaConexion;
    private Date horaDesconexion;
    private int minutosConectado;
    private List<String> actividad; // Lista de acciones realizadas

    public SesionUsuario() {}

    public SesionUsuario(String usuarioId, String rol, Date horaConexion) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = rol; // Asumimos que el rol es el nombre de usuario para la sesión
        this.horaConexion = horaConexion;
    }

    // Getters y setters
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public Date getHoraConexion() { return horaConexion; }
    public void setHoraConexion(Date horaConexion) { this.horaConexion = horaConexion; }
    public Date getHoraDesconexion() { return horaDesconexion; }
    public void setHoraDesconexion(Date horaDesconexion) { this.horaDesconexion = horaDesconexion; }
    public int getMinutosConectado() { return minutosConectado; }
    public void setMinutosConectado(int minutosConectado) { this.minutosConectado = minutosConectado; }
    public List<String> getActividad() { return actividad; }
    public void setActividad(List<String> actividad) { this.actividad = actividad; }
} 