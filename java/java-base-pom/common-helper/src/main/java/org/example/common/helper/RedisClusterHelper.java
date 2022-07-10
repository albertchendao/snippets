package org.example.common.helper;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Redis 集群工具
 */
@Slf4j
public class RedisClusterHelper {

    private JedisCluster redisCluster;
    private JedisPool jedisPool;
    private String configKey;

    public RedisClusterHelper(String configKey) {
        this.configKey = configKey;
        createJedisClusterClient();
    }

    private void createJedisClusterClient() {
        log.debug("create redis cluster ....");
        Properties pro = new Properties();

        String propertiesFileName = "redis-config.properties";
        InputStream resourceAsStream = null;
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(100);
        try {
            resourceAsStream = RedisClusterHelper.class.getResourceAsStream(propertiesFileName);
            if (resourceAsStream == null) {
                resourceAsStream = RedisClusterHelper.class.getClassLoader().getResourceAsStream(propertiesFileName);
            }
            pro.load(resourceAsStream);
            String s = pro.getProperty(configKey);
            log.debug("redis config is {}", s);
//            if (clustersEnum == RedisSrcEnum.SOO_YING){
            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            //Jedis Cluster will attempt to discover cluster nodes automatically
            for (String hostAndPortStr : s.split(",")) {
                String[] hpArr = hostAndPortStr.split(":");
                jedisClusterNodes.add(new HostAndPort(hpArr[0], Integer.valueOf(hpArr[1])));
            }

            this.redisCluster = new JedisCluster(jedisClusterNodes, 60 * 1000, poolConfig);
            log.info("create cluster of {} success ", configKey);
//            }
        } catch (Exception e) {
            log.error("get config file error:{} ", e.getMessage());
            throw new RuntimeException("create redis cluster failed ...");
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public JedisCluster getRedisCluster() {
        return redisCluster;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 关闭所有连接
     */
    public void closeCluster() {
        try {
            this.redisCluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
