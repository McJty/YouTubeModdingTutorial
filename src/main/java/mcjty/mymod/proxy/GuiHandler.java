package mcjty.mymod.proxy;

import mcjty.mymod.MyMod;
import mcjty.mymod.tools.IGuiTile;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;

public class GuiHandler {

    @Nullable
    public static GuiScreen getClientGuiElement(FMLPlayMessages.OpenContainer container) {
        PacketBuffer buf = container.getAdditionalData();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        World world = MyMod.proxy.getClientWorld();
        EntityPlayer player = MyMod.proxy.getClientPlayer();
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IGuiTile) {
            return ((IGuiTile) te).createGui(player);
        }
        return null;
    }
}
