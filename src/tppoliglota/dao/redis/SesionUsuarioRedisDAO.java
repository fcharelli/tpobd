package tppoliglota.dao.redis;

import redis.clients.jedis.Jedis;
import tppoliglota.model.SesionUsuario;
import com.google.gson.Gson;

public class SesionUsuarioRedisDAO {
    private final Jedis jedis;
    private final Gson gson = new Gson();

    public SesionUsuarioRedisDAO(Jedis jedis) {
        this.jedis = jedis;
    }

    public void guardarSesion(SesionUsuario sesion) {
        String key = "sesion:" + sesion.getUsuarioId();
        jedis.set(key, gson.toJson(sesion));
    }

    public SesionUsuario buscarSesionPorUsuarioId(String usuarioId) {
        String key = "sesion:" + usuarioId;
        String json = jedis.get(key);
        if (json == null) return null;
        return gson.fromJson(json, SesionUsuario.class);
    }

    public void eliminarSesion(String usuarioId) {
        String key = "sesion:" + usuarioId;
        jedis.del(key);
    }
} 