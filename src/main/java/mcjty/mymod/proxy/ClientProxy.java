package mcjty.mymod.proxy;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.ModEntities;
import mcjty.mymod.ModItems;
import mcjty.mymod.MyMod;
import mcjty.mymod.input.KeyBindings;
import mcjty.mymod.input.KeyInputHandler;
import mcjty.mymod.rendering.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {
        OBJLoader.INSTANCE.addDomain(MyMod.MODID);
        MinecraftForge.EVENT_BUS.register(OverlayRenderer.instance);
        ModBlocks.initModels();
        ModItems.initModels();
        ModEntities.initModels();
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    //    public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
//        return ModelLoaderRegistry.loadASM(location, parameters);
//    }
}
