package me.qball.spawnerprotection.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SpawnerType {

    BAT("Bat", Version.V1_8),
    BLAZE("Blaze", Version.V1_8),
    CHICKEN("Chicken", Version.V1_8),
    COW("Cow", Version.V1_8),
    CREEPER("Creeper", Version.V1_8),
    ENDERMAN("Enderman", Version.V1_8),
    ENDERMITE("Endermite", Version.V1_8),
    GHAST("Ghast", Version.V1_8),
    GUARDIAN("Guardian", Version.V1_8),
    PIG("Pig", Version.V1_8),
    RABBIT("Rabbit", Version.V1_8),
    SHEEP("Sheep", Version.V1_8),
    SILVERFISH("Silverfish", Version.V1_8),
    SKELETON("Skeleton", Version.V1_8),
    SPIDER("Spider", Version.V1_8),
    SQUID("Squid", Version.V1_8),
    SLIME("Slime", Version.V1_8),
    VILLAGER("Villager", Version.V1_8),
    WITCH("Witch", Version.V1_8),
    WOLF("Wolf", Version.V1_8),
    CAVE_SPIDER("CaveSpider", Version.V1_8),
    ZOMBIE("Zombie", Version.V1_8),
    ENDER_DRAGON("EnderDragon", Version.V1_8),
    GIANT("Giant", Version.V1_8),
    SNOWMAN("Snowman", Version.V1_8),
    HORSE("Horse", Version.V1_8),
    MUSHROOM_COW("Mooshroom", Version.V1_8),
    OCELOT("Ocelot", Version.V1_8),
    PIG_ZOMBIE("Pigman", Version.V1_8),
    MAGMA_CUBE("Magmacube", Version.V1_8),
    WITHER("Wither", Version.V1_8),
    IRON_GOLEM("IronGolem", Version.V1_8),
    SHULKER("Shulker", Version.V1_9), // 1.9
    POLAR_BEAR("PolarBear", Version.V1_10), // 1.10
    LLAMA("Llama", Version.V1_11), // 1.11
    PARROT("Parrot", Version.V1_12); // 1.12

    private final String displayName, type;
    private final Version version;

    SpawnerType(String displayName, Version version) {
        this.displayName = displayName;
        this.type = name().toLowerCase();
        this.version = version;
    }

    public static String findName(String creatureName) {
        return Arrays.stream(values())
                .filter(spawnerType -> Objects.equals(spawnerType.getType(), creatureName))
                .map(SpawnerType::getDisplayName)
                .findAny().orElse("");
    }

    public static Set<SpawnerType> getByVersion(Version version) {
        Stream<Version> versions = Version.getVersions(version).stream();
        return Arrays.stream(values()).filter(spawnerType -> versions.anyMatch(ver -> spawnerType.version == ver))
                .collect(Collectors.toSet());
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getType() {
        return this.type;
    }

}