package me.qball.spawnerprotection.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Version {

    V1_8("1.8"),
    V1_9("1.9"),
    V1_10("1.10"),
    V1_11("1.11"),
    V1_12("1.12");

    private final String id;

    Version(String id) {
        this.id = id;
    }

    public static Version getVersion(String string) {
        return Arrays.stream(values()).filter(version -> string.contains(version.id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to determine version from: " + string + "!"));
    }

    public static Set<Version> getVersions(Version version) {
        return Arrays.stream(values()).filter(ver -> ver.ordinal() <= version.ordinal()).collect(Collectors.toSet());
    }

    public String getId() {
        return id;
    }

}
