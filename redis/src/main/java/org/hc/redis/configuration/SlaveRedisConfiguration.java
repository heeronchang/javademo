package org.hc.redis.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import java.time.Duration;

/**
 * slave redis config
 * @author 叽哒嘎叽
 * @since 2024/9/27
 */

@Configuration
@ConfigurationProperties(prefix = "spring.slave-redis")
public class SlaveRedisConfiguration {

    private String host;
    private Integer port;
    private String password;
    private Integer database;
    private LettucePool lettuce;

    public static class LettucePool {
        private Pool pool;

        public static class Pool {
            private Integer maxActive;
            private Integer maxIdle;
            private Long maxWait;
            private Integer minIdle;

            public Integer getMaxActive() {
                return maxActive;
            }

            public void setMaxActive(Integer maxActive) {
                this.maxActive = maxActive;
            }

            public Integer getMaxIdle() {
                return maxIdle;
            }

            public void setMaxIdle(Integer maxIdle) {
                this.maxIdle = maxIdle;
            }

            public Long getMaxWait() {
                return maxWait;
            }

            public void setMaxWait(Long maxWait) {
                this.maxWait = maxWait;
            }

            public Integer getMinIdle() {
                return minIdle;
            }

            public void setMinIdle(Integer minIdle) {
                this.minIdle = minIdle;
            }
        }

        public Pool getPool() {
            return pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }
    }


    @Bean
    public GenericObjectPoolConfig slavePoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(lettuce.getPool().getMaxActive());
        genericObjectPoolConfig.setMaxIdle(lettuce.getPool().getMaxIdle());
        genericObjectPoolConfig.setMinIdle(lettuce.getPool().getMinIdle());
        genericObjectPoolConfig.setMaxWait(Duration.ofMillis(lettuce.getPool().getMaxWait()));
        return genericObjectPoolConfig;
    }

    @Bean
    public RedisStandaloneConfiguration slaveRedisConfig() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return redisStandaloneConfiguration;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public LettucePool getLettuce() {
        return lettuce;
    }

    public void setLettuce(LettucePool lettuce) {
        this.lettuce = lettuce;
    }
}
