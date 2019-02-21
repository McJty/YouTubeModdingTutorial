package mcjty.mymod.network;

import io.netty.buffer.ByteBuf;
import mcjty.mymod.MyMod;
import mcjty.mymod.tools.IMachineStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncMachineState {

    private int energy;
    private int progress;       // Store from 0 to 100

    public PacketSyncMachineState(ByteBuf buf) {
        energy = buf.readInt();
        progress = buf.readByte();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(energy);
        buf.writeByte(progress);
    }

    public PacketSyncMachineState(int energy, int progress) {
        this.energy = energy;
        this.progress = progress;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EntityPlayer player = MyMod.proxy.getClientPlayer();
            if (player.openContainer instanceof IMachineStateContainer) {
                ((IMachineStateContainer) player.openContainer).sync(energy, progress);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
