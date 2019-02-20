package mcjty.mymod.network;

import mcjty.mymod.MyMod;
import mcjty.mymod.input.PacketToggleMode;
import mcjty.mymod.mana.PacketSendMana;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Messages {

    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MyMod.MODID, channelName), () -> "1.0", s -> true, s -> true);

        // Server side
        INSTANCE.registerMessage(nextID(), PacketToggleMode.class,
                PacketToggleMode::toBytes,
                PacketToggleMode::new,
                PacketToggleMode::handle);

        // Client side
        INSTANCE.registerMessage(nextID(), PacketSyncMachineState.class,
                PacketSyncMachineState::toBytes,
                PacketSyncMachineState::new,
                PacketSyncMachineState::handle);
        INSTANCE.registerMessage(nextID(), PacketSendMana.class,
                PacketSendMana::toBytes,
                PacketSendMana::new,
                PacketSendMana::handle);
    }
}
