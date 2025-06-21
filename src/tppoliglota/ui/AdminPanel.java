package tppoliglota.ui;

import tppoliglota.controller.ProductoController;
import tppoliglota.controller.UsuarioController;
import tppoliglota.model.Producto;
import tppoliglota.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends JPanel {
    public AdminPanel(MainWindow mainWindow, Usuario usuario, ProductoController productoController, UsuarioController usuarioController) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 230, 245));
        JLabel lblTitulo = new JLabel("Panel de Administración", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // TABLA DE PRODUCTOS
        DefaultTableModel modeloProductos = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio", "Stock", "Categoría"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tablaProductos = new JTable(modeloProductos);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        tabs.addTab("Productos", scrollProductos);

        List<Producto> productos = productoController.obtenerTodos();
        for (Producto p : productos) {
            modeloProductos.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecioActual(), p.getStock(), p.getCategoria()});
        }

        // Botones para productos
        JPanel panelProd = new JPanel();
        JButton btnAgregar = new JButton("Agregar Producto");
        JButton btnEditar = new JButton("Editar Producto");
        JButton btnEliminar = new JButton("Eliminar Producto");
        panelProd.add(btnAgregar);
        panelProd.add(btnEditar);
        panelProd.add(btnEliminar);
        scrollProductos.setColumnHeaderView(panelProd);

        // TABLA DE USUARIOS
        DefaultTableModel modeloUsuarios = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Email", "Rol"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tablaUsuarios = new JTable(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        tabs.addTab("Usuarios", scrollUsuarios);

        List<Usuario> usuarios = usuarioController.obtenerTodos();
        for (Usuario u : usuarios) {
            modeloUsuarios.addRow(new Object[]{u.getId(), u.getNombre(), u.getApellido(), u.getEmail(), u.getRol()});
        }

        // Botón para editar usuario
        JPanel panelUsr = new JPanel();
        JButton btnEditarUsr = new JButton("Editar Usuario");
        panelUsr.add(btnEditarUsr);
        scrollUsuarios.setColumnHeaderView(panelUsr);

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

        // Acciones de productos
        btnAgregar.addActionListener(e -> {
            Producto nuevo = mostrarFormularioProducto(null);
            if (nuevo != null) {
                productoController.registrarProducto(nuevo);
                modeloProductos.addRow(new Object[]{nuevo.getId(), nuevo.getNombre(), nuevo.getPrecioActual(), nuevo.getStock(), nuevo.getCategoria()});
                JOptionPane.showMessageDialog(this, "Producto agregado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnEditar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = (String) modeloProductos.getValueAt(fila, 0);
            Producto prod = productoController.buscarProductoPorId(id);
            Producto editado = mostrarFormularioProducto(prod);
            if (editado != null) {
                productoController.actualizarProducto(editado);
                modeloProductos.setValueAt(editado.getNombre(), fila, 1);
                modeloProductos.setValueAt(editado.getPrecioActual(), fila, 2);
                modeloProductos.setValueAt(editado.getStock(), fila, 3);
                modeloProductos.setValueAt(editado.getCategoria(), fila, 4);
                JOptionPane.showMessageDialog(this, "Producto editado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnEliminar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = (String) modeloProductos.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar el producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                productoController.eliminarProducto(id);
                modeloProductos.removeRow(fila);
                JOptionPane.showMessageDialog(this, "Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Acciones de usuario (edición real)
        btnEditarUsr.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = (String) modeloUsuarios.getValueAt(fila, 0);
            Usuario usr = usuarioController.buscarUsuarioPorId(id);
            Usuario editado = mostrarFormularioUsuario(usr);
            if (editado != null) {
                usuarioController.actualizarUsuario(editado);
                modeloUsuarios.setValueAt(editado.getNombre(), fila, 1);
                modeloUsuarios.setValueAt(editado.getApellido(), fila, 2);
                modeloUsuarios.setValueAt(editado.getEmail(), fila, 3);
                JOptionPane.showMessageDialog(this, "Usuario editado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private Producto mostrarFormularioProducto(Producto producto) {
        JTextField txtNombre = new JTextField(producto != null ? producto.getNombre() : "");
        JTextField txtPrecio = new JTextField(producto != null ? String.valueOf(producto.getPrecioActual()) : "");
        JTextField txtStock = new JTextField(producto != null ? String.valueOf(producto.getStock()) : "");
        JTextField txtCategoria = new JTextField(producto != null ? producto.getCategoria() : "");
        JTextField txtDescripcion = new JTextField(producto != null ? producto.getDescripcion() : "");
        JTextField txtImagen = new JTextField(producto != null && producto.getImagenes() != null && !producto.getImagenes().isEmpty() ? producto.getImagenes().get(0) : "");
        JButton btnSeleccionarImagen = new JButton("Seleccionar imagen...");
        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes JPG y PNG", "jpg", "jpeg", "png"));
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                txtImagen.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Precio:")); panel.add(txtPrecio);
        panel.add(new JLabel("Stock:")); panel.add(txtStock);
        panel.add(new JLabel("Categoría:")); panel.add(txtCategoria);
        panel.add(new JLabel("Descripción:")); panel.add(txtDescripcion);
        panel.add(new JLabel("Ruta de imagen (opcional):"));
        JPanel panelImg = new JPanel(new BorderLayout());
        panelImg.add(txtImagen, BorderLayout.CENTER);
        panelImg.add(btnSeleccionarImagen, BorderLayout.EAST);
        panel.add(panelImg);
        int res = JOptionPane.showConfirmDialog(this, panel, producto == null ? "Agregar Producto" : "Editar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Producto p = producto != null ? producto : new Producto();
                p.setNombre(txtNombre.getText());
                p.setPrecioActual(Double.parseDouble(txtPrecio.getText()));
                p.setStock(Integer.parseInt(txtStock.getText()));
                p.setCategoria(txtCategoria.getText());
                p.setDescripcion(txtDescripcion.getText());
                List<String> imagenes = new ArrayList<>();
                if (!txtImagen.getText().isEmpty()) imagenes.add(txtImagen.getText());
                p.setImagenes(imagenes);
                return p;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private Usuario mostrarFormularioUsuario(Usuario usuario) {
        JTextField txtNombre = new JTextField(usuario != null ? usuario.getNombre() : "");
        JTextField txtApellido = new JTextField(usuario != null ? usuario.getApellido() : "");
        JTextField txtEmail = new JTextField(usuario != null ? usuario.getEmail() : "");
        JTextField txtTelefono = new JTextField(usuario != null ? usuario.getTelefono() : "");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Apellido:")); panel.add(txtApellido);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("Teléfono:")); panel.add(txtTelefono);
        int res = JOptionPane.showConfirmDialog(this, panel, "Editar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                usuario.setNombre(txtNombre.getText());
                usuario.setApellido(txtApellido.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setTelefono(txtTelefono.getText());
                return usuario;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
} 