package mcjty.mymod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OregenConfig {

    public static ForgeConfigSpec.BooleanValue RETROGEN;
    public static ForgeConfigSpec.BooleanValue VERBOSE;
    public static ForgeConfigSpec.BooleanValue GENERATE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue GENERATE_NETHER;
    public static ForgeConfigSpec.BooleanValue GENERATE_END;

    public static ForgeConfigSpec.IntValue MIN_VEIN_SIZE;
    public static ForgeConfigSpec.IntValue MAX_VEIN_SIZE;
    public static ForgeConfigSpec.IntValue CHANCES_TO_SPAWN;
    public static ForgeConfigSpec.IntValue MIN_Y;
    public static ForgeConfigSpec.IntValue MAX_Y;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Oregen");
//        CLIENT_BUILDER.comment("Oregen");

        RETROGEN = SERVER_BUILDER
                .comment("Enable retrogen")
                .define("oregen.retrogen", true);
        VERBOSE = SERVER_BUILDER
                .comment("Enable verbose logging for retrogen")
                .define("oregen.verbose", false);
        GENERATE_OVERWORLD = SERVER_BUILDER
                .comment("Generate ore in the overworld")
                .define("oregen.generateOverworld", true);
        GENERATE_NETHER = SERVER_BUILDER
                .comment("Generate ore in the nether")
                .define("oregen.generateNether", true);
        GENERATE_END = SERVER_BUILDER
                .comment("Generate ore in the end")
                .define("oregen.generateEnd", true);

        MIN_VEIN_SIZE = SERVER_BUILDER
                .comment("Minimum size of every ore vein")
                .defineInRange("oregen.minVeinSize", 4, 0, 500);
        MAX_VEIN_SIZE = SERVER_BUILDER
                .comment("Maximum size of every ore vein")
                .defineInRange("oregen.maxVeinSize", 8, 0, 500);
        CHANCES_TO_SPAWN = SERVER_BUILDER
                .comment("Maximum veins per chunk")
                .defineInRange("oregen.chancesToSpawn", 9, 0, 1000000000);
        MIN_Y = SERVER_BUILDER
                .comment("Minimum height for the ore")
                .defineInRange("oregen.minY", 2, 1, 256);
        MAX_Y = SERVER_BUILDER
                .comment("Maximum height for the ore")
                .defineInRange("oregen.maxY", 50, 1, 256);
    }
}
