package mcjty.mymod.playermana;

import mcjty.mymod.MyMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerPropertyEvents {

    public static PlayerPropertyEvents instance = new PlayerPropertyEvents();

    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof EntityPlayer) {
            if (!event.getObject().getCapability(PlayerProperties.PLAYER_MANA).isPresent()) {
                event.addCapability(new ResourceLocation(MyMod.MODID, "Mana"), new PropertiesDispatcher());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // We need to copyFrom the capabilities
            event.getOriginal().getCapability(PlayerProperties.PLAYER_MANA).ifPresent(oldStore -> {
                event.getEntityPlayer().getCapability(PlayerProperties.PLAYER_MANA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
}
