package mcjty.mymod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ServerProxy implements IProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {

    }

    @Override
    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("Can't call this server-side!");
    }
}
