package mcjty.mymod.mana;

import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ManaTickHandler {

    public static ManaTickHandler instance = new ManaTickHandler();

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent evt) {
        // @todo 1.13
//        if (evt.phase == TickEvent.Phase.START) {
//            return;
//        }
//        World world = evt.world;
//        WorldMana.get(world).tick(world);
    }
}
