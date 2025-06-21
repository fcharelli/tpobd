package tppoliglota.ui;

import tppoliglota.model.Usuario;
import tppoliglota.service.CompraService;
import tppoliglota.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private final MainWindow mainWindow;
    private final Usuario usuario;
    private final ProductoController productoController;
    private final CarritoController carritoController;
    private final PedidoController pedidoController;
    private final FacturaController facturaController;
    private final CompraService compraService;
    private final AuditoriaController auditoriaController;

    public MenuPanel(MainWindow mainWindow, Usuario usuario, ProductoController productoController,
                     CarritoController carritoController, PedidoController pedidoController,
                     FacturaController facturaController, CompraService compraService,
                     AuditoriaController auditoriaController) {
        this.mainWindow = mainWindow;
        this.usuario = usuario;
        this.productoController = productoController;
        this.carritoController = carritoController;
        this.pedidoController = pedidoController;
        this.facturaController = facturaController;
        this.compraService = compraService;
        this.auditoriaController = auditoriaController;

        setLayout(new GridLayout(6, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 230, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblBienvenida = new JLabel("Bienvenido/a, " + usuario.getNombre() + " (" + usuario.getRol() + ")", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblBienvenida, gbc);
        gbc.gridy++;

        if (usuario.getRol().equalsIgnoreCase("admin")) {
            JButton btnAdmin = new JButton("Panel de Administrador");
            btnAdmin.addActionListener(e -> mainWindow.mostrarPanel(new AdminPanel(mainWindow, usuario, productoController, mainWindow.getUsuarioController())));
            add(btnAdmin);

            JButton btnAuditoria = new JButton("Ver Auditoria de Catalogo");
            btnAuditoria.addActionListener(e -> mainWindow.mostrarPanel(new AuditoriaPanel(mainWindow, usuario, auditoriaController)));
            add(btnAuditoria);
        }

        JButton btnCatalogo = new JButton("Ver Catalogo de Productos");
        btnCatalogo.addActionListener(e -> mainWindow.mostrarPanel(new CatalogoPanel(mainWindow, usuario, productoController, carritoController)));
        add(btnCatalogo);

        JButton btnCarrito = new JButton("Ver Carrito");
        btnCarrito.addActionListener(e -> mainWindow.mostrarPanel(new CarritoPanel(mainWindow, usuario, carritoController, productoController, compraService)));
        add(btnCarrito);

        JButton btnHistorial = new JButton("Ver Historial de Compras");
        btnHistorial.addActionListener(e -> mainWindow.mostrarPanel(new HistorialPanel(mainWindow, usuario, pedidoController, facturaController)));
        add(btnHistorial);

        JButton btnLogout = new JButton("Cerrar Sesion");
        btnLogout.addActionListener(e -> mainWindow.mostrarPanel(new LoginPanel(mainWindow, mainWindow.getUsuarioController(), mainWindow.getSesionController())));
        add(btnLogout);
    }
} 