package me.qball.spawnerprotection.utils;

import org.bukkit.Bukkit;

import java.util.*;
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
    VEX("Vex", Version.V1_11),
    EVOKER("Evoker", Version.V1_11),
    VINDICATOR("Vindicator", Version.V1_11),
    PARROT("Parrot", Version.V1_12),// 1.12
    TURTLE("Turtle", Version.V1_13),
    FOX("Fox",Version.V1_14),
    PANDA("Panda",Version.V1_14),
    RAVAGER("Ravager",Version.V1_14),
    PILLAGER("Pillager",Version.V1_14),
    CAT("Cat",Version.V1_14);



    private final String displayName;
    private final String type;
    private final Version version;

    SpawnerType(String displayName, Version version) {
        this.displayName = displayName;
        this.type = name().toLowerCase();
        this.version = version;
    }

    public static String findName(String creatureName) {
        return Arrays.stream(values())
                .filter(spawnerType -> Objects.equals(spawnerType.getType(), creatureName))
                .map(SpawnerType::getType)
                .findAny().orElse("");
    }

    public static Set<SpawnerType> getByVersion(Version version) {
        Set<SpawnerType> set = new HashSet<>();
        for (SpawnerType spawnerType : values()) {
            if (Version.getVersions(version).stream().anyMatch(ver -> spawnerType.version == ver)) {
                set.add(spawnerType);
            }
        }
        return sort(set);
    }

    private static Set<SpawnerType> sort(Set<SpawnerType> unsorted){
        List<String> types = new ArrayList<>();
        for(SpawnerType type : unsorted){
            types.add(type.getType());
        }
        Collections.sort(types);
        Set<SpawnerType> sorted = new TreeSet<>();
        for(String type : types){
            sorted.add(SpawnerType.valueOf(type.toUpperCase()));
        }
        return sorted;
    }

    public static ArrayList<SpawnerType> getSpawnersByVersion(Version version){
        String[] tmp = Bukkit.getBukkitVersion().split("MC: ");
        Version ver = Version.getVersion(tmp[1]);
        ArrayList<SpawnerType> types = new ArrayList<>();
        for(SpawnerType type : SpawnerType.values());
        return new ArrayList<>();
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getType() {
        return this.type;
    }

}