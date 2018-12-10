package mcjty.mymod.mana;

import io.netty.buffer.ByteBuf;
import mcjty.mymod.MyMod;
import mcjty.mymod.rendering.OverlayRenderer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendMana implements IMessage {

    private float mana;
    private float influence;
    private float playerMana;

    @Override
    public void fromBytes(ByteBuf buf) {
        mana = buf.readFloat();
        influence = buf.readFloat();
        playerMana = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(mana);
        buf.writeFloat(influence);
        buf.writeFloat(playerMana);
    }

    // You need this constructor!
    public PacketSendMana() {
    }

    public PacketSendMana(float mana, float influence, float playerMana) {
        this.mana = mana;
        this.influence = influence;
        this.playerMana = playerMana;
    }

    public static class Handler implements IMessageHandler<PacketSendMana, IMessage> {
        @Override
        public IMessage onMessage(PacketSendMana message, MessageContext ctx) {
            MyMod.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSendMana message, MessageContext ctx) {
            OverlayRenderer.instance.setMana(message.mana, message.influence, message.playerMana);
        }
    }
}
