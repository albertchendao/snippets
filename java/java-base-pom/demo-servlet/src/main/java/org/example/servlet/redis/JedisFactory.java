package org.example.servlet.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

import java.util.HashMap;
import java.util.Map;


public class JedisFactory {
    private final static int MAX_ACTIVE = 2000;
    private final static int MAX_IDLE = 800;
    private final static int MAX_WAIT = 10000;
    private final static int TIMEOUT = 6000;

    private static Map<String, JedisPool> jedisPools = new HashMap<String, JedisPool>();

    /**
     * 初始化jedis连接池
     *
     * @param jedisName jedis名称
     * @return jedis连接池
     */
    public static JedisPool initJedisPool(String jedisName) {
        JedisPool jPool = jedisPools.get(jedisName);
        if (jPool == null) {
            System.out.println("init jpool");
            // TODO: 2019-08-05
            String host = "localhost";
            int port = 6379;
            jPool = newJedisPool(host, port);
            jedisPools.put(jedisName, jPool);
        }
        return jPool;
    }

    /**
     * 获取jedis对象实例
     *
     * @param jedisName jedis名称
     * @return jedis对象
     */
    public static Jedis getJedisInstance(String jedisName) {
        JedisPool jedisPool = jedisPools.get(jedisName);
        if (jedisPool == null) {
            jedisPool = initJedisPool(jedisName);
        }

        Jedis jedis = null;
        for (int i = 0; i < 4; i++) {
            try {
                jedis = jedisPool.getResource();
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                }
            }
        }
        return jedis;
    }

    /**
     * 创建一个新的jedis连接池
     *
     * @param host 主机地址
     * @param port 端口号
     * @return jedis连接池
     */
    private static JedisPool newJedisPool(String host, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnReturn(false);
        config.setTestOnBorrow(false);
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        return new JedisPool(config, host, port, TIMEOUT);
    }

    /**
     * 配合使用getJedisInstance方法后将jedis对象释放回连接池中
     *
     * @param jedis 使用完毕的Jedis对象
     */
    public static void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 将jedis连接池中的所有jedis连接全部销毁
     */
    public static void destroy() {
        jedisPools.values().stream().forEach(Pool::destroy);

    }

    /**
     * 指定销毁具体的一个jedis连接池
     *
     * @param poolName 连接池名称
     */
    public static void destroy(String poolName) {
        try {
            jedisPools.get(poolName).destroy();
        } catch (Exception e) {

        }
    }
}
