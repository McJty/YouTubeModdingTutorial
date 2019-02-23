package mcjty.mymod.tools;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public interface IGuiTile {

    GuiContainer createGui(EntityPlayer player);
}
