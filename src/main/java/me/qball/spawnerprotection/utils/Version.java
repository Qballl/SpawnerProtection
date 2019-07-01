package me.qball.spawnerprotection.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public enum Version {

    V1_8("1.8"),
    V1_9("1.9"),
    V1_10("1.10"),
    V1_11("1.11"),
    V1_12("1.12"),
    V1_13("1.13"),
    V1_14("1.14");

    private final String id;

    Version(String id) {
        this.id = id;
    }

    public static Version getVersion(String string) {
        for (Version version : values()) {
            if (string.contains(version.id)) {
                return Optional.of(version)
                        .orElseThrow(() -> new RuntimeException("Failed to determine version from: " + string + "!"));
            }
        }
        return Optional.<Version>empty()
                .orElseThrow(() -> new RuntimeException("Failed to determine version from: " + string + "!"));
    }

    public static Set<Version> getVersions(Version version) {
        Set<Version> set = new HashSet<>();
        for (Version ver : values()) {
            if (ver.ordinal() <= version.ordinal()) {
                set.add(ver);
            }
        }
        return set;
    }

    public String getId() {
        return id;
    }
}
