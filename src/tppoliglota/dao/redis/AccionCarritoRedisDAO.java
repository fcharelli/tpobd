package tppoliglota.dao.redis;

import redis.clients.jedis.Jedis;
import tppoliglota.model.AccionCarrito;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import tppoliglota.model.ItemCarrito;

public class AccionCarritoRedisDAO {
    private final Jedis jedis;
    private static final String KEY_PREFIX = "acciones_carrito:";
    private final Gson gson = new Gson();

    public AccionCarritoRedisDAO(Jedis jedis) {
        this.jedis = jedis;
    }

    public void guardarAccion(String usuarioId, AccionCarrito accion) {
        String key = "carrito:historial:" + usuarioId;
        jedis.lpush(key, gson.toJson(accion));
    }

    public List<AccionCarrito> obtenerHistorial(String usuarioId) {
        String key = "carrito:historial:" + usuarioId;
        List<String> jsonList = jedis.lrange(key, 0, -1);
        List<AccionCarrito> acciones = new ArrayList<>();
        for (String json : jsonList) {
            acciones.add(gson.fromJson(json, AccionCarrito.class));
        }
        return acciones;
    }

    public void limpiarHistorial(String usuarioId) {
        String key = "carrito:historial:" + usuarioId;
        jedis.del(key);
    }

    public void registrarAccion(String usuarioId, AccionCarrito accion) {
        try (Jedis jedis = new Jedis()) {
            String key = KEY_PREFIX + usuarioId;
            jedis.lpush(key, gson.toJson(accion));
        }
    }

    public AccionCarrito obtenerYRemoverUltimaAccion(String usuarioId) {
        try (Jedis jedis = new Jedis()) {
            String key = KEY_PREFIX + usuarioId;
            String accionJson = jedis.lpop(key);

            if (accionJson != null) {
                return gson.fromJson(accionJson, AccionCarrito.class);
            }
            return null;
        }
    }
} 