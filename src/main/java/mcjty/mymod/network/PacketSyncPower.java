package mcjty.mymod.network;

import io.netty.buffer.ByteBuf;
import mcjty.mymod.MyMod;
import mcjty.mymod.tools.IEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncPower implements IMessage {

    private int energy;

    @Override
    public void fromBytes(ByteBuf buf) {
        energy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(energy);
    }

    // You need this constructor!
    public PacketSyncPower() {
    }

    public PacketSyncPower(int energy) {
        this.energy = energy;
    }

    public static class Handler implements IMessageHandler<PacketSyncPower, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncPower message, MessageContext ctx) {
            MyMod.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSyncPower message, MessageContext ctx) {
            EntityPlayer player = MyMod.proxy.getClientPlayer();
            if (player.openContainer instanceof IEnergyContainer) {
                ((IEnergyContainer) player.openContainer).syncPower(message.energy);
            }
        }
    }
}
