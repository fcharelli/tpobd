package tppoliglota.ui;

import tppoliglota.controller.PedidoController;
import tppoliglota.controller.FacturaController;
import tppoliglota.model.Pedido;
import tppoliglota.model.Factura;
import tppoliglota.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistorialPanel extends JPanel {
    private DefaultTableModel modeloPedidos;
    private DefaultTableModel modeloFacturas;
    private PedidoController pedidoController;
    private FacturaController facturaController;
    private Usuario usuario;

    public HistorialPanel(MainWindow mainWindow, Usuario usuario, PedidoController pedidoController, FacturaController facturaController) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 255, 230));
        JLabel lblTitulo = new JLabel("Historial de Compras y Facturas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // Tabla de pedidos
        modeloPedidos = new DefaultTableModel(new Object[]{"ID", "Fecha", "Total", "Estado"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tablaPedidos = new JTable(modeloPedidos);
        JScrollPane scrollPedidos = new JScrollPane(tablaPedidos);
        tabs.addTab("Pedidos", scrollPedidos);

        this.pedidoController = pedidoController;
        this.facturaController = facturaController;
        this.usuario = usuario;

        cargarHistorial();

        // Tabla de facturas
        modeloFacturas = new DefaultTableModel(new Object[]{"ID", "Fecha", "Total", "Forma de Pago"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tablaFacturas = new JTable(modeloFacturas);
        JScrollPane scrollFacturas = new JScrollPane(tablaFacturas);
        tabs.addTab("Facturas", scrollFacturas);

        add(tabs, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.addActionListener(e -> {
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
        });
        add(btnVolver, BorderLayout.SOUTH);
    }

    private void cargarHistorial() {
        modeloPedidos.setRowCount(0);
        modeloFacturas.setRowCount(0);

        try {
            List<Pedido> pedidos = pedidoController.buscarPorUsuario(usuario.getId());
            for (Pedido pedido : pedidos) {
                modeloPedidos.addRow(new Object[]{pedido.getId(), pedido.getFecha(), pedido.getEstado(), String.format("%.2f", pedido.getTotal())});
            }

            List<Factura> facturas = facturaController.obtenerFacturasPorUsuario(usuario.getId());
            for (Factura factura : facturas) {
                modeloFacturas.addRow(new Object[]{factura.getId(), factura.getFecha(), String.format("%.2f", factura.getTotal())});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el historial: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
} 