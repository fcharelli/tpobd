package tppoliglota.ui;

import tppoliglota.controller.ProductoController;
import tppoliglota.controller.CarritoController;
import tppoliglota.model.Producto;
import tppoliglota.model.Usuario;
import tppoliglota.model.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CatalogoPanel extends JPanel {
    private final ProductoController productoController;
    private final CarritoController carritoController;
    private final Usuario usuario;
    private JTable tabla;
    private JComboBox<String> comboCategoria;
    private JTextField txtBuscar;
    private DefaultTableModel modeloTabla;

    public CatalogoPanel(MainWindow mainWindow, Usuario usuario, ProductoController productoController, CarritoController carritoController) {
        this.usuario = usuario;
        this.productoController = productoController;
        this.carritoController = carritoController;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 255, 245));

        JLabel lblTitulo = new JLabel("Catálogo de Productos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel();
        txtBuscar = new JTextField(15);
        comboCategoria = new JComboBox<>();
        comboCategoria.addItem("Todas");
        for (String cat : productoController.obtenerCategorias()) {
            comboCategoria.addItem(cat);
        }
        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(new JLabel("Categoría:"));
        panelBusqueda.add(comboCategoria);
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.PAGE_START);

        modeloTabla = new DefaultTableModel(new Object[]{"Imagen", "Nombre", "Precio", "Stock", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? ImageIcon.class : String.class;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(60);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        cargarProductos(productoController.obtenerTodos());

        btnBuscar.addActionListener(e -> filtrarProductos());
        comboCategoria.addActionListener(e -> filtrarProductos());

        JPanel panelInferior = new JPanel();
        JLabel lblCantidad = new JLabel("Cantidad:");
        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JButton btnAgregar = new JButton("Agregar al Carrito");
        JButton btnVolver = new JButton("Volver al Menú");
        panelInferior.add(lblCantidad);
        panelInferior.add(spinnerCantidad);
        panelInferior.add(btnAgregar);
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                mostrarPopup("Selecciona un producto.", false);
                return;
            }
            String nombre = (String) modeloTabla.getValueAt(fila, 1);
            Producto prod = productoController.buscarPorNombre(nombre);
            if (prod == null) {
                mostrarPopup("Producto no encontrado.", false);
                return;
            }
            int cantidad = (Integer) spinnerCantidad.getValue();
            if (cantidad > prod.getStock()) {
                mostrarPopup("No hay suficiente stock.", false);
                return;
            }
            carritoController.agregarProductoAlCarrito(usuario.getId(), prod.getId(), cantidad);
            mostrarPopup("Producto agregado al carrito.", true);
        });

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
    }

    private void cargarProductos(List<Producto> productos) {
        modeloTabla.setRowCount(0);
        for (Producto p : productos) {
            ImageIcon icono = null;
            if (p.getImagenes() != null && !p.getImagenes().isEmpty()) {
                String ruta = p.getImagenes().get(0);
                java.io.File imgFile = new java.io.File(ruta);
                if (imgFile.exists()) {
                    icono = new ImageIcon(ruta);
                    Image img = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    icono = new ImageIcon(img);
                }
            }
            modeloTabla.addRow(new Object[]{icono, p.getNombre(), String.valueOf(p.getPrecioActual()), String.valueOf(p.getStock()), p.getCategoria()});
        }
    }

    private void filtrarProductos() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        String categoria = (String) comboCategoria.getSelectedItem();
        List<Producto> productos = productoController.obtenerTodos();
        productos = productos.stream()
                .filter(p -> (categoria.equals("Todas") || p.getCategoria().equalsIgnoreCase(categoria)) &&
                        (texto.isEmpty() || p.getNombre().toLowerCase().contains(texto)))
                .toList();
        cargarProductos(productos);
    }

    private void mostrarPopup(String mensaje, boolean exito) {
        JOptionPane.showMessageDialog(this, mensaje,
                exito ? "Éxito" : "Error",
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
} 