package me.jongwoo.gomboard.domains.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class RedisKeyUtil {
    private final RedisTemplate<String, Object> jwtKeyRedisTemplate;

    private static final String REDIS_PUBLIC_KEY = "redisPublicKey";
    private static final String REDIS_PRIVATE_KEY = "redisPrivateKey";

    public void saveKeyPair(KeyPair keyPair) {
        var publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        var privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        jwtKeyRedisTemplate.opsForValue().set(REDIS_PUBLIC_KEY, publicKey);
        jwtKeyRedisTemplate.opsForValue().set(REDIS_PRIVATE_KEY, privateKey);
    }

    public KeyPair loadKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        var publicKeyString = (String) jwtKeyRedisTemplate.opsForValue().get(REDIS_PUBLIC_KEY);
        var privateKeyString = (String) jwtKeyRedisTemplate.opsForValue().get(REDIS_PRIVATE_KEY);

        if (publicKeyString == null || privateKeyString == null) {
            throw new IllegalArgumentException("Key not found.");
        }

        var publicBytes = Base64.getDecoder().decode(publicKeyString);
        var privateBytes = Base64.getDecoder().decode(privateKeyString);

        var keyFactory = KeyFactory.getInstance("RSA");

        var publicKeySpec = new X509EncodedKeySpec(publicBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        var privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return new KeyPair(publicKey, privateKey);
    }

    public boolean keyExist() {
        return Boolean.TRUE.equals(jwtKeyRedisTemplate.hasKey(REDIS_PUBLIC_KEY)) && Boolean.TRUE.equals(jwtKeyRedisTemplate.hasKey(REDIS_PRIVATE_KEY));
    }
}
