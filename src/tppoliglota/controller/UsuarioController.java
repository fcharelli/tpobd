package tppoliglota.controller;

import tppoliglota.dao.mongo.UsuarioMongoDAO;
import tppoliglota.model.Usuario;
import tppoliglota.utils.PasswordUtils;

import java.util.List;

public class UsuarioController {
    private final UsuarioMongoDAO usuarioDAO;

    public UsuarioController(UsuarioMongoDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void registrarUsuario(Usuario usuario, String password) {
        usuario.setClave(PasswordUtils.hashPassword(password));
        usuarioDAO.guardar(usuario);
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarioDAO.buscarPorId(id);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioDAO.obtenerTodos();
    }

    public boolean validarLogin(String email, String password) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null) return false;
        return PasswordUtils.verifyPassword(password, usuario.getClave());
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    public void eliminarUsuario(String id) {
        usuarioDAO.eliminar(id);
    }
} 