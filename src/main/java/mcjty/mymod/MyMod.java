package mcjty.mymod;

import mcjty.mymod.generator.DamageTracker;
import mcjty.mymod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;


@Mod(modid = MyMod.MODID, name = MyMod.MODNAME, version = MyMod.MODVERSION, dependencies = "required-after:forge@[14.23.5.2768,)", useMetadata = true)
public class MyMod {

    public static final String MODID = "mymod";
    public static final String MODNAME = "My Mod";
    public static final String MODVERSION= "0.0.1";

    @SidedProxy(clientSide = "mcjty.mymod.proxy.ClientProxy", serverSide = "mcjty.mymod.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs creativeTab = new CreativeTabs("mymod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.blockFastFurnace);
        }
    };

    public MyMod() {
        // This has to be done VERY early
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.Instance
    public static MyMod instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        DamageTracker.instance.reset();
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        DamageTracker.instance.reset();
    }


}
