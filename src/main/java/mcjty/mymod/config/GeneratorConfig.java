package mcjty.mymod.config;

import mcjty.mymod.MyMod;
import net.minecraftforge.common.config.Config;

@Config(modid = MyMod.MODID, category = "generator")
public class GeneratorConfig {

    @Config.Comment(value = "Maximum amount of power for the generator")
    public static int MAX_POWER = 100000;
}
