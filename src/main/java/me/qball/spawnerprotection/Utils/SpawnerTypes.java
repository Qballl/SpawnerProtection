package me.qball.spawnerprotection.Utils;

import org.bukkit.entity.EntityType;

public enum SpawnerTypes {
    BAT("Bat", "bat", EntityType.BAT),  BLAZE("Blaze", "blaze", EntityType.BLAZE), CHICKEN("Chicken", "chicken", EntityType.CHICKEN),
    COW("Cow", "cow",EntityType.COW),  CREEPER("Creeper", "creeper",EntityType.CREEPER), ENDERMAN("Enderman", "enderman", EntityType.ENDERMAN),
    ENDERMITE("Endermite", "endermite",EntityType.ENDERMITE),  GHAST("Ghast", "ghast",EntityType.GHAST),  GUARDIAN("Guardian", "guardian",EntityType.GUARDIAN),
    PIG("Pig", "pig",EntityType.PIG),  RABBIT("Rabbit", "rabbit", EntityType.RABBIT),  SHEEP("Sheep", "sheep",EntityType.SHEEP),  SILVERFISH("Silverfish", "silverfish", EntityType.SILVERFISH),
    SKELETON("Skeleton", "skeleton", EntityType.SKELETON), SPIDER("Spider", "spider", EntityType.SPIDER),  SQUID("Squid", "squid", EntityType.SQUID),  SLIME("Slime", "slime", EntityType.SLIME),
    VILLAGER("Villager", "villager", EntityType.VILLAGER),  WITCH("Witch", "witch", EntityType.WITCH), WOLF("Wolf", "wolf", EntityType.WOLF),  CAVESPIDER("CaveSpider", "cavespider", EntityType.CAVE_SPIDER),
    ZOMBIE("Zombie", "zombie", EntityType.ZOMBIE),  ENDERDRAGON("EnderDragon", "enderdragon", EntityType.ENDER_DRAGON), GIANT("Giant", "giant", EntityType.GIANT),  SNOWMAN("Snowman", "snowman", EntityType.SNOWMAN),
    HORSE("Horse", "entityhorse", EntityType.HORSE),  MOOSHROOM("Mooshroom", "mushroomcow", EntityType.MUSHROOM_COW), OCELOT("Ocelot", "ozelot", EntityType.OCELOT),  PIGMAN("Pigman", "pigzombie", EntityType.PIG_ZOMBIE),
    MAGMACUBE("Magmacube", "lavaslime", EntityType.MAGMA_CUBE), WITHER("Wither", "witherboss", EntityType.WITHER), IRONGOLEM("IronGolem", "villager_golem", EntityType.IRON_GOLEM),  SHULKER("Shulker", "shulker", EntityType.SHULKER),
    POLARBEAR("PolarBear", "polarbear", EntityType.POLAR_BEAR),  LLAMA("Llama", "llama", EntityType.LLAMA);
    private String displayName;
    private String type;
    private EntityType entityType;
    SpawnerTypes(String displayName, String type, EntityType entityType){
        this.displayName = displayName;
        this.type = type;
        this.entityType = entityType;
    }
    public String getType(){
        return this.type;
    }
    public String getDisplayName(){
        return this.displayName;
    }
    public EntityType getEntityType(){
        return this.entityType;
    }
    public static String findName(String creatureName) {
        for (SpawnerTypes type : SpawnerTypes.values()) {
            if (type.getType().equalsIgnoreCase(creatureName))
                return type.getDisplayName();
        }
        return "";
    }
}