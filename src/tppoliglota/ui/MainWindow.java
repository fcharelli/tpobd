package tppoliglota.ui;

import javax.swing.*;
import java.awt.*;
import com.mongodb.client.MongoDatabase;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import com.datastax.oss.driver.api.core.CqlSession;
import redis.clients.jedis.Jedis;
import tppoliglota.controller.*;
import tppoliglota.dao.cassandra.AuditoriaCatalogoCassandraDAO;
import tppoliglota.dao.mongo.*;
import tppoliglota.dao.redis.AccionCarritoRedisDAO;
import tppoliglota.dao.redis.SesionUsuarioRedisDAO;
import tppoliglota.dao.neo4j.RelacionUsuarioProductoNeo4jDAO;
import tppoliglota.service.CompraService;

import java.net.InetSocketAddress;

public class MainWindow extends JFrame {
    private JPanel panelCentral;

    // Conexiones a BBDD
    private Jedis jedis;
    private MongoDatabase mongoDatabase;
    private CqlSession cqlSession;
    private Driver neo4jDriver;

    // DAOs
    private UsuarioMongoDAO usuarioDAO;
    private ProductoMongoDAO productoDAO;
    private CarritoMongoDAO carritoDAO;
    private PedidoMongoDAO pedidoDAO;
    private FacturaMongoDAO facturaDAO;
    private PromocionMongoDAO promocionDAO;
    private HistorialPrecioMongoDAO historialPrecioDAO;
    private AccionCarritoRedisDAO accionCarritoDAO;
    private SesionUsuarioRedisDAO sesionDAO;
    private AuditoriaCatalogoCassandraDAO auditoriaDAO;
    private RelacionUsuarioProductoNeo4jDAO relacionDAO;

    // Controllers
    private UsuarioController usuarioController;
    private ProductoController productoController;
    private CarritoController carritoController;
    private PedidoController pedidoController;
    private FacturaController facturaController;
    private PromocionController promocionController;
    private HistorialPrecioController historialPrecioController;
    private SesionController sesionController;
    private AuditoriaController auditoriaController;
    private RelacionUsuarioProductoNeo4jController relacionController;
    
    // Services
    private CompraService compraService;

    public MainWindow() {
        super("Frassifour");
        
        inicializarDependencias();
        
        setTitle("Frassifour");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initUI();
    }

    private void inicializarDependencias() {
        try {
            // Inicializar Conexiones
            this.mongoDatabase = MongoConnection.getDatabase();
            this.jedis = new Jedis("localhost", 6379);
            this.cqlSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1")
                .withKeyspace("tppoliglota")
                .build();
            this.neo4jDriver = GraphDatabase.driver("bolt://localhost:7687");

            // Inicializar DAOs
            this.usuarioDAO = new UsuarioMongoDAO(this.mongoDatabase);
            this.productoDAO = new ProductoMongoDAO(this.mongoDatabase);
            this.carritoDAO = new CarritoMongoDAO(this.mongoDatabase);
            this.pedidoDAO = new PedidoMongoDAO();
            this.facturaDAO = new FacturaMongoDAO(this.mongoDatabase);
            this.promocionDAO = new PromocionMongoDAO(this.mongoDatabase);
            this.historialPrecioDAO = new HistorialPrecioMongoDAO(this.mongoDatabase);
            this.accionCarritoDAO = new AccionCarritoRedisDAO(this.jedis);
            this.sesionDAO = new SesionUsuarioRedisDAO(this.jedis);
            this.auditoriaDAO = new AuditoriaCatalogoCassandraDAO(this.cqlSession);
            this.relacionDAO = new RelacionUsuarioProductoNeo4jDAO(this.neo4jDriver);

            // Inicializar Controllers
            this.usuarioController = new UsuarioController(this.usuarioDAO);
            this.productoController = new ProductoController(this.productoDAO);
            this.carritoController = new CarritoController(this.carritoDAO, this.accionCarritoDAO);
            this.pedidoController = new PedidoController(this.pedidoDAO);
            this.facturaController = new FacturaController(this.facturaDAO);
            this.promocionController = new PromocionController(this.promocionDAO);
            this.historialPrecioController = new HistorialPrecioController(this.historialPrecioDAO);
            this.sesionController = new SesionController(this.sesionDAO, null);
            this.auditoriaController = new AuditoriaController(this.auditoriaDAO);
            this.relacionController = new RelacionUsuarioProductoNeo4jController(this.relacionDAO);
            
            // Inicializar Services
            this.compraService = new CompraService(this.carritoController, this.pedidoController, this.facturaController, this.relacionController, this.sesionController);
        
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con las bases de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initUI() {
        // Barra superior con el nombre
        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(new Color(210, 230, 255)); // Pastel azul
        JLabel label = new JLabel("Frassifour", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(new Color(60, 60, 90));
        barraSuperior.add(label);
        add(barraSuperior, BorderLayout.NORTH);

        // Panel central intercambiable
        panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(245, 245, 255));
        add(panelCentral, BorderLayout.CENTER);
    }

    public void mostrarPanel(JPanel nuevoPanel) {
        panelCentral.removeAll();
        panelCentral.add(nuevoPanel, BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow ventana = new MainWindow();
            LoginPanel loginPanel = new LoginPanel(ventana, ventana.usuarioController, ventana.sesionController);
            ventana.mostrarPanel(loginPanel);
            ventana.setVisible(true);
        });
    }

    // Getters para pasar a los paneles
    public UsuarioController getUsuarioController() { return usuarioController; }
    public ProductoController getProductoController() { return productoController; }
    public CarritoController getCarritoController() { return carritoController; }
    public PedidoController getPedidoController() { return pedidoController; }
    public FacturaController getFacturaController() { return facturaController; }
    public PromocionController getPromocionController() { return promocionController; }
    public HistorialPrecioController getHistorialPrecioController() { return historialPrecioController; }
    public SesionController getSesionController() { return sesionController; }
    public AuditoriaController getAuditoriaController() { return auditoriaController; }
    public RelacionUsuarioProductoNeo4jController getRelacionController() { return relacionController; }
    public CompraService getCompraService() { return compraService; }
    
    @Override
    public void dispose() {
        super.dispose();
        try {
            if (jedis != null) jedis.close();
            if (cqlSession != null) cqlSession.close();
            if (neo4jDriver != null) neo4jDriver.close();
            MongoConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 