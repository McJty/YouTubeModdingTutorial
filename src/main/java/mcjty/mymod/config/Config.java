package mcjty.mymod.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import mcjty.mymod.MyMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

import static net.minecraftforge.fml.Logging.CORE;

@Mod.EventBusSubscriber
public class Config {

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {
        FastFurnaceConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        GeneratorConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        OregenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }


    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        MyMod.logger.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        MyMod.logger.debug("Built TOML config for {}", path.toString());
        configData.load();
        MyMod.logger.debug("Loaded TOML config file {}", path.toString());
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        MyMod.logger.debug("Loaded {} config file {}", MyMod.MODID, configEvent.getConfig().getFileName());

    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
        MyMod.logger.fatal(CORE, "{} config just got changed on the file system!", MyMod.MODID);
    }

}
