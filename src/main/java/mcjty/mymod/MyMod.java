package mcjty.mymod;

import mcjty.mymod.generator.DamageTracker;
import mcjty.mymod.mana.ManaTickHandler;
import mcjty.mymod.network.Messages;
import mcjty.mymod.playermana.PlayerMana;
import mcjty.mymod.playermana.PlayerPropertyEvents;
import mcjty.mymod.proxy.ClientProxy;
import mcjty.mymod.proxy.IProxy;
import mcjty.mymod.proxy.ServerProxy;
import mcjty.mymod.worldgen.WorldTickHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;


@Mod(MyMod.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MyMod {

    public static final String MODID = "mymod";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());


    public static ItemGroup creativeTab = new ItemGroup("mymod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.blockFastFurnace);
        }
    };

    public MyMod() {
        // This has to be done VERY early
        FluidRegistry.enableUniversalBucket();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    public static final Logger logger = LogManager.getLogger();


    private void setup(final FMLCommonSetupEvent event) {
        Messages.registerMessages("mymod");
        // @todo 1.13
//        GameRegistry.registerWorldGenerator(OreGenerator.instance, 5);

//        MinecraftForge.EVENT_BUS.register(OreGenerator.instance);
        MinecraftForge.EVENT_BUS.register(ManaTickHandler.instance);
        MinecraftForge.EVENT_BUS.register(PlayerPropertyEvents.instance);

        CapabilityManager.INSTANCE.register(PlayerMana.class, new Capability.IStorage<PlayerMana>() {
            @Nullable
            @Override
            public INBTBase writeNBT(Capability<PlayerMana> capability, PlayerMana instance, EnumFacing side) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void readNBT(Capability<PlayerMana> capability, PlayerMana instance, EnumFacing side, INBTBase nbt) {
                throw new UnsupportedOperationException();
            }
        }, () -> null);

        ModEntities.init();
        ModLiquids.init();

        // @todo 1.13
//        NetworkRegistry.INSTANCE.registerGuiHandler(MyMod.instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(WorldTickHandler.instance);
        MinecraftForge.EVENT_BUS.register(DamageTracker.instance);

        // @todo 1.13
//        GameRegistry.addSmelting(ModBlocks.blockFancyOre, new ItemStack(ModItems.itemFancyIngot, 1), 0.5f);
//        OreDictionary.registerOre("oreFancy", ModBlocks.blockFancyOre);

        proxy.setup(event);
    }

    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {
        ModBlocks.registerTiles(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        ModEntities.register(event.getRegistry());
    }


    @SubscribeEvent
    public static void serverStarted(FMLServerStartedEvent event) {
        DamageTracker.instance.reset();
    }

    @SubscribeEvent
    public static void serverStopped(FMLServerStoppedEvent event) {
        DamageTracker.instance.reset();
    }


//    @SubscribeEvent
//    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
//        if (event.getModID().equals(MODID)) {
//            ConfigManager.sync(MODID, Config.Type.INSTANCE);
//        }
//    }

}
