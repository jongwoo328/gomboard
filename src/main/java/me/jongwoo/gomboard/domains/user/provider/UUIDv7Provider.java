package me.jongwoo.gomboard.domains.user.provider;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.security.SecureRandom;
import java.util.UUID;

public class UUIDv7Provider {
    private static final SecureRandom secureRandom = new SecureRandom();

    private UUIDv7Provider() {
    }

    private static SecureRandom getSecureRandom() {
        return secureRandom;
    }

    private static TimeBasedEpochRandomGenerator getGenerator() {
        return Generators.timeBasedEpochRandomGenerator(getSecureRandom());
    }

    public static UUID getUUIDv7() {
        return getGenerator().generate();
    }
}
