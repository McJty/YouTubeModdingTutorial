package mcjty.mymod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IProxy {

    void setup(final FMLCommonSetupEvent event);

    EntityPlayer getClientPlayer();
}
