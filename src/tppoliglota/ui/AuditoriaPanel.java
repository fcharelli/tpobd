package tppoliglota.ui;

import tppoliglota.controller.AuditoriaController;
import tppoliglota.model.AuditoriaCatalogo;
import tppoliglota.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AuditoriaPanel extends JPanel {
    public AuditoriaPanel(MainWindow mainWindow, Usuario usuario, AuditoriaController auditoriaController) {
        setLayout(new BorderLayout());
        setBackground(new Color(235, 245, 255));
        JLabel lblTitulo = new JLabel("Auditoría de Cambios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        // Filtro por producto
        JPanel panelFiltro = new JPanel();
        JTextField txtProducto = new JTextField(15);
        JButton btnFiltrar = new JButton("Filtrar");
        panelFiltro.add(new JLabel("Producto ID:"));
        panelFiltro.add(txtProducto);
        panelFiltro.add(btnFiltrar);
        add(panelFiltro, BorderLayout.PAGE_START);

        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"Producto ID", "Campo", "Valor Anterior", "Valor Nuevo", "Operador", "Fecha"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar auditoría
        List<AuditoriaCatalogo> auditorias = auditoriaController.obtenerAuditoriaCatalogo();
        cargarAuditoria(auditorias, modeloTabla);

        btnFiltrar.addActionListener(e -> {
            String filtro = txtProducto.getText().trim();
            List<AuditoriaCatalogo> filtrado = auditorias;
            if (!filtro.isEmpty()) {
                filtrado = auditorias.stream().filter(a -> a.getProductoId().toString().equalsIgnoreCase(filtro)).collect(Collectors.toList());
            }
            cargarAuditoria(filtrado, modeloTabla);
        });

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

    private void cargarAuditoria(List<AuditoriaCatalogo> auditorias, DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0);
        for (AuditoriaCatalogo a : auditorias) {
            modeloTabla.addRow(new Object[]{a.getProductoId(), a.getCampoModificado(), a.getValorAnterior(), a.getValorNuevo(), a.getOperadorId(), a.getTimestamp()});
        }
    }
} 