package org.hc.redis.configuration;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/6
 */
public class DefaultSerializer implements RedisSerializer {
    private final Charset charset;

    public DefaultSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public DefaultSerializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return o == null ? null : String.valueOf(o).getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : new String(bytes, charset);
    }
}
