package tppoliglota.ui;

import tppoliglota.controller.CarritoController;
import tppoliglota.controller.ProductoController;
import tppoliglota.model.Carrito;
import tppoliglota.model.ItemCarrito;
import tppoliglota.model.Producto;
import tppoliglota.model.Usuario;
import tppoliglota.service.CompraService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CarritoPanel extends JPanel {
    public CarritoPanel(MainWindow mainWindow, Usuario usuario, CarritoController carritoController, ProductoController productoController, CompraService compraService) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 245, 230));
        JLabel lblTitulo = new JLabel("Carrito de Compras", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Cantidad", "Precio"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        Carrito carrito = carritoController.buscarCarritoPorId(usuario.getId());
        if (carrito != null && carrito.getProductos() != null) {
            for (ItemCarrito item : carrito.getProductos()) {
                Producto prod = productoController.buscarProductoPorId(item.getProductoId());
                if (prod != null) {
                    modeloTabla.addRow(new Object[]{prod.getNombre(), item.getCantidad(), prod.getPrecioActual()});
                }
            }
        }

        JPanel panelBotones = new JPanel();
        JButton btnUndo = new JButton("Deshacer última acción");
        JButton btnConfirmar = new JButton("Confirmar compra");
        JButton btnVolver = new JButton("Volver al Menú");
        panelBotones.add(btnUndo);
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        btnUndo.addActionListener(e -> {
            carritoController.limpiarHistorialAcciones(usuario.getId());
            JOptionPane.showMessageDialog(this, "Acción deshecha.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mainWindow.mostrarPanel(new CarritoPanel(mainWindow, usuario, carritoController, productoController, compraService));
        });

        btnConfirmar.addActionListener(e -> {
            try {
                // Aquí puedes pedir forma de pago y datos de pago
                String[] formas = {"efectivo", "tarjeta", "cta. cte.", "punto de retiro"};
                String formaPago = (String) JOptionPane.showInputDialog(this, "Selecciona forma de pago:", "Forma de Pago", JOptionPane.QUESTION_MESSAGE, null, formas, formas[0]);
                if (formaPago == null) return;
                String datosPago = JOptionPane.showInputDialog(this, "Datos del pago (opcional):");
                compraService.convertirCarritoAPedidoYFacturar(usuario.getId(), formaPago, datosPago);
                JOptionPane.showMessageDialog(this, "Compra confirmada y facturada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarMenu(mainWindow, usuario);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> mostrarMenu(mainWindow, usuario));
    }
    
    private void mostrarMenu(MainWindow mainWindow, Usuario usuario) {
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
    }
} 