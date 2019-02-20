package mcjty.mymod.input;

import io.netty.buffer.ByteBuf;
import mcjty.mymod.items.ItemWand;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketToggleMode  {

    public PacketToggleMode(ByteBuf buf) {
    }

    public void toBytes(ByteBuf buf) {
    }

    public PacketToggleMode() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EntityPlayerMP playerEntity = ctx.get().getSender();
            ItemStack heldItem = playerEntity.getHeldItem(EnumHand.MAIN_HAND);
            if (!heldItem.isEmpty() && heldItem.getItem() instanceof ItemWand) {
                ItemWand wand = (ItemWand) (heldItem.getItem());
                wand.toggleMode(playerEntity, heldItem);
            }

        });
    }
}
