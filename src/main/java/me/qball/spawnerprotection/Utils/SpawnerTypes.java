package me.qball.spawnerprotection.Utils;

import org.bukkit.entity.EntityType;

public enum SpawnerTypes {
    BAT("Bat", "bat"), BLAZE("Blaze", "blaze"), CHICKEN("Chicken", "chicken"), COW("Cow", "cow"),
    CREEPER("Creeper", "creeper"), ENDERMAN("Enderman", "enderman"), ENDERMITE("Endermite", "endermite"),
    GHAST("Ghast", "ghast"), GUARDIAN("Guardian", "guardian"), PIG("Pig", "pig"),
    RABBIT("Rabbit", "rabbit"), SHEEP("Sheep", "sheep"), SILVERFISH("Silverfish", "silverfish"),
    SKELETON("Skeleton", "skeleton"), SPIDER("Spider", "spider"), SQUID("Squid", "squid"),
    SLIME("Slime", "slime"), VILLAGER("Villager", "villager"), WITCH("Witch", "witch"),
    WOLF("Wolf", "wolf"), CAVESPIDER("CaveSpider", "cave_spider"), ZOMBIE("Zombie", "zombie"),
    ENDERDRAGON("EnderDragon", "enderdragon"), GIANT("Giant", "giant"), SNOWMAN("Snowman", "snowman"),
    HORSE("Horse", "entityhorse"), MOOSHROOM("Mooshroom", "mushroom_cow"), OCELOT("Ocelot", "ozelot"),
    PIGMAN("Pigman", "pig_zombie"), MAGMACUBE("Magmacube", "magma_cube"),
    WITHER("Wither", "wither"), IRONGOLEM("IronGolem", "iron_golem"),
    SHULKER("Shulker", "shulker"), POLARBEAR("PolarBear", "polar_bear"), LLAMA("Llama", "llama");

    private String displayName;
    private String type;

    SpawnerTypes(String displayName, String type) {
        this.displayName = displayName;
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
    public String getDisplayName(){
        return this.displayName;
    }
    public static String findName(String creatureName) {
        for (SpawnerTypes type : SpawnerTypes.values()) {
            if (type.getType().equalsIgnoreCase(creatureName))
                return type.getDisplayName();
        }
        return "";
    }
}