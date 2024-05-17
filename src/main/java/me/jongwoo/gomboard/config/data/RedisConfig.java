package me.jongwoo.gomboard.config.data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableConfigurationProperties
public class RedisConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.data.redis.jwt-key")
    public RedisStandaloneConfiguration redisStandaloneConfigurationJwtKeyDatabase() {
        return new RedisStandaloneConfiguration();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.data.redis.user-jwt")
    public RedisStandaloneConfiguration redisStandaloneConfigurationUserJwtDatabase() {
        return new RedisStandaloneConfiguration();
    }

    @Bean
    @Primary
    public RedisConnectionFactory jwtKeyRedisConnectionFactory(
            @Qualifier("redisStandaloneConfigurationJwtKeyDatabase") RedisStandaloneConfiguration redisStandaloneConfigurationJwtKeyDatabase
    ) {
        return new LettuceConnectionFactory(redisStandaloneConfigurationJwtKeyDatabase);
    }

    @Bean
    public RedisConnectionFactory userJwtRedisConnectionFactory(
            @Qualifier("redisStandaloneConfigurationUserJwtDatabase") RedisStandaloneConfiguration redisStandaloneConfigurationUserJwtDatabase
    ) {
        return new LettuceConnectionFactory(redisStandaloneConfigurationUserJwtDatabase);
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> jwtKeyRedisTemplate(
            @Qualifier("jwtKeyRedisConnectionFactory") RedisConnectionFactory jwtKeyRedisConnectionFactory
    ) {
        RedisTemplate<String, Object> jwtKeyRedisTemplate = new RedisTemplate<>();
        jwtKeyRedisTemplate.setConnectionFactory(jwtKeyRedisConnectionFactory);
        jwtKeyRedisTemplate.setKeySerializer(new StringRedisSerializer());
        jwtKeyRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return jwtKeyRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> userJwtRedisTemplate(
            @Qualifier("userJwtRedisConnectionFactory") RedisConnectionFactory userJwtRedisConnectionFactory
    ) {
        RedisTemplate<String, Object> userJwtRedisTemplate = new RedisTemplate<>();
        userJwtRedisTemplate.setConnectionFactory(userJwtRedisConnectionFactory);
        userJwtRedisTemplate.setKeySerializer(new StringRedisSerializer());
        userJwtRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return userJwtRedisTemplate;
    }
}
