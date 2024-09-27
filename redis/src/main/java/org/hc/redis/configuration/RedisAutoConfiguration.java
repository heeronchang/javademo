package org.hc.redis.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 使用lettuce，先读取配置初始化ConnectionFactory，然后创建RedisTemplate实例，设置连接工厂
 * @author 叽哒嘎叽
 * @since 2024/9/27
 */

@Configuration
public class RedisAutoConfiguration {
    @Bean
    @Primary
    public LettuceConnectionFactory defaultLettuceConnectionFactory(RedisStandaloneConfiguration defaultRedisConfig, GenericObjectPoolConfig defaultPoolConfig) {
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(1000))
                .poolConfig(defaultPoolConfig)
                .build();

        return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, String> defaultRedisTemplate(LettuceConnectionFactory defaultLettuceConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(defaultLettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
//        System.out.println("serializer "+ template.getKeySerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnBean(name = "slaveRedisConfiguration")
    public LettuceConnectionFactory slaveLettuceConnectionFactory(@Qualifier("slaveRedisConfig") RedisStandaloneConfiguration slaveRedisConfig,
                                                                  GenericObjectPoolConfig slavePoolConfig) {
        LettuceClientConfiguration clientConfig =
                LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(100))
                        .poolConfig(slavePoolConfig).build();
        return new LettuceConnectionFactory(slaveRedisConfig, clientConfig);
    }

    @Bean
    @ConditionalOnBean(name = "slaveLettuceConnectionFactory")
    public RedisTemplate<String, String> slaveRedisTemplate(@Qualifier("slaveLettuceConnectionFactory") LettuceConnectionFactory slaveLettuceConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(slaveLettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
