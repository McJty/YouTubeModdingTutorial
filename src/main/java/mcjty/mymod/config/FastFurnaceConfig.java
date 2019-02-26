package mcjty.mymod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FastFurnaceConfig {

    public static ForgeConfigSpec.IntValue MAX_PROGRESS;
    public static ForgeConfigSpec.IntValue MAX_POWER;
    public static ForgeConfigSpec.IntValue RF_PER_TICK_INPUT;
    public static ForgeConfigSpec.IntValue RF_PER_TICK;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Fast furnace");
//        CLIENT_BUILDER.comment("Fast furnace");

        MAX_PROGRESS = SERVER_BUILDER
                .comment("Number of ticks for one smelting operation")
                .defineInRange("fast_furnace.maxProgress", 40, 1, 1000000000);
        MAX_POWER = SERVER_BUILDER
                .comment("Maximum amount of power for the fast furnace")
                .defineInRange("fast_furnace.maxPower", 100000, 1, 1000000000);
        RF_PER_TICK_INPUT = SERVER_BUILDER
                .comment("How much energy per tick the fast furnace can receive")
                .defineInRange("fast_furnace.rfPerTickInput", 100, 1, 1000000000);
        RF_PER_TICK = SERVER_BUILDER
                .comment("How much energy per tick the fast furnace uses while smelting")
                .defineInRange("fast_furnace.rfPerTick", 20, 1, 1000000000);

    }
}
