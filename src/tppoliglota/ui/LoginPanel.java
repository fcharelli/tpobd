package tppoliglota.ui;

import tppoliglota.controller.UsuarioController;
import tppoliglota.dao.mongo.MongoConnection;
import tppoliglota.dao.mongo.UsuarioMongoDAO;
import tppoliglota.model.Usuario;
import tppoliglota.ui.MenuPanel;
import tppoliglota.controller.SesionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private final MainWindow mainWindow;
    private final CardLayout cardLayout;
    private final JPanel cards;
    private final UsuarioController usuarioController;
    private final SesionController sesionController;

    public LoginPanel(MainWindow mainWindow, UsuarioController usuarioController, SesionController sesionController) {
        this.mainWindow = mainWindow;
        this.usuarioController = usuarioController;
        this.sesionController = sesionController;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(crearPanelLogin(), "login");
        cards.add(crearPanelRegistro(), "registro");
        add(cards, BorderLayout.CENTER);
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuario = new JLabel("Usuario o Email:");
        JTextField txtUsuario = new JTextField(20);
        JLabel lblClave = new JLabel("Contraseña:");
        JPasswordField txtClave = new JPasswordField(20);
        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnRegistro = new JButton("¿No tienes cuenta? Regístrate aquí");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblClave, gbc);
        gbc.gridx = 1;
        panel.add(txtClave, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);
        gbc.gridy = 3;
        panel.add(btnRegistro, gbc);

        btnLogin.addActionListener(e -> {
            String email = txtUsuario.getText();
            String clave = new String(txtClave.getPassword());

            if (usuarioController.validarLogin(email, clave)) {
                Usuario usuario = usuarioController.buscarUsuarioPorEmail(email);
                sesionController.iniciarSesion(usuario.getId(), usuario.getRol());
                mostrarPopup("Bienvenido, " + usuario.getNombre() + ".", true);
                
                MenuPanel menuPanel = new MenuPanel(
                    mainWindow, 
                    usuario,
                    mainWindow.getProductoController(),
                    mainWindow.getCarritoController(),
                    mainWindow.getPedidoController(),
                    mainWindow.getFacturaController(),
                    mainWindow.getCompraService(),
                    mainWindow.getAuditoriaController()
                );
                mainWindow.mostrarPanel(menuPanel);
            } else {
                mostrarPopup("Usuario o contraseña incorrectos.", false);
            }
        });

        btnRegistro.addActionListener(e -> cardLayout.show(cards, "registro"));
        return panel;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(20);
        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField(20);
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);
        JLabel lblClave = new JLabel("Contraseña:");
        JPasswordField txtClave = new JPasswordField(20);
        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnVolver = new JButton("¿Ya tienes cuenta? Inicia sesión");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblNombre, gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblApellido, gbc);
        gbc.gridx = 1;
        panel.add(txtApellido, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblClave, gbc);
        gbc.gridx = 1;
        panel.add(txtClave, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);
        gbc.gridy = 5;
        panel.add(btnVolver, gbc);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String email = txtEmail.getText();
            String clave = new String(txtClave.getPassword());

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || clave.isEmpty()) {
                mostrarPopup("Por favor, complete todos los campos.", false);
                return;
            }

            if (usuarioController.buscarUsuarioPorEmail(email) != null) {
                mostrarPopup("El email ya se encuentra registrado.", false);
                return;
            }

            Usuario nuevoUsuario = new Usuario(nombre, apellido, email, "usuario");
            usuarioController.registrarUsuario(nuevoUsuario, clave);

            mostrarPopup("Registro exitoso. Ahora puedes iniciar sesión.", true);
            cardLayout.show(cards, "login");
        });

        btnVolver.addActionListener(e -> cardLayout.show(cards, "login"));
        return panel;
    }

    private void mostrarPopup(String mensaje, boolean exito) {
        JOptionPane.showMessageDialog(this, mensaje,
                exito ? "Éxito" : "Error",
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
} 