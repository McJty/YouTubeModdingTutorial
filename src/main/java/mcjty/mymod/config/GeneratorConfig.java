package mcjty.mymod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneratorConfig {

    public static ForgeConfigSpec.IntValue MAX_POWER;
    public static ForgeConfigSpec.DoubleValue POWER_DAMAGE_FACTOR;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Generator");
//        CLIENT_BUILDER.comment("Generator");

        MAX_POWER = SERVER_BUILDER
                .comment("Maximum amount of power for the generator")
                .defineInRange("generator.maxPower", 100000, 1, 1000000000);
        POWER_DAMAGE_FACTOR = SERVER_BUILDER
                .comment("Factor to control how much power is generated per damage unit")
                .defineInRange("generator.powerDamageFactor", 5.0, 0.0001, 1000000000);

    }
}
