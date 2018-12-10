package mcjty.mymod.proxy;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import mcjty.mymod.*;
import mcjty.mymod.generator.DamageTracker;
import mcjty.mymod.mana.ManaTickHandler;
import mcjty.mymod.network.Messages;
import mcjty.mymod.playermana.PlayerMana;
import mcjty.mymod.playermana.PlayerPropertyEvents;
import mcjty.mymod.worldgen.OreGenerator;
import mcjty.mymod.worldgen.WorldTickHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Messages.registerMessages("mymod");
        GameRegistry.registerWorldGenerator(OreGenerator.instance, 5);

        MinecraftForge.EVENT_BUS.register(OreGenerator.instance);
        MinecraftForge.EVENT_BUS.register(ManaTickHandler.instance);
        MinecraftForge.EVENT_BUS.register(PlayerPropertyEvents.instance);

        CapabilityManager.INSTANCE.register(PlayerMana.class, new Capability.IStorage<PlayerMana>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<PlayerMana> capability, PlayerMana instance, EnumFacing side) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void readNBT(Capability<PlayerMana> capability, PlayerMana instance, EnumFacing side, NBTBase nbt) {
                throw new UnsupportedOperationException();
            }
        }, () -> null);

        ModEntities.init();
        ModLiquids.init();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MyMod.instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(WorldTickHandler.instance);
        MinecraftForge.EVENT_BUS.register(DamageTracker.instance);
    }

    public void postInit(FMLPostInitializationEvent e) {
        GameRegistry.addSmelting(ModBlocks.blockFancyOre, new ItemStack(ModItems.itemFancyIngot, 1), 0.5f);
        OreDictionary.registerOre("oreFancy", ModBlocks.blockFancyOre);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.register(event.getRegistry());
    }

    @Nullable
    public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
        return null;
    }


    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}
