package com.ziembatomasz.ranking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

//@Configuration
public class AutoConfig {

//    @Bean(value = "selfRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redis = new StringRedisTemplate();
        redis.setConnectionFactory(redisConnectionFactory);

        // Setting the default serialization of String/Value for redis
        DefaultSerializer stringRedisSerializer = new DefaultSerializer();
        redis.setKeySerializer(stringRedisSerializer);
        redis.setValueSerializer(stringRedisSerializer);
        redis.setHashKeySerializer(stringRedisSerializer);
        redis.setHashValueSerializer(stringRedisSerializer);

        redis.afterPropertiesSet();
        return redis;
    }
}
