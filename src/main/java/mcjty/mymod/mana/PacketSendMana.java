package mcjty.mymod.mana;

import io.netty.buffer.ByteBuf;
import mcjty.mymod.rendering.OverlayRenderer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSendMana {

    private float mana;
    private float influence;
    private float playerMana;

    public PacketSendMana(ByteBuf buf) {
        mana = buf.readFloat();
        influence = buf.readFloat();
        playerMana = buf.readFloat();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeFloat(mana);
        buf.writeFloat(influence);
        buf.writeFloat(playerMana);
    }

    public PacketSendMana(float mana, float influence, float playerMana) {
        this.mana = mana;
        this.influence = influence;
        this.playerMana = playerMana;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> OverlayRenderer.instance.setMana(mana, influence, playerMana));
    }
}
