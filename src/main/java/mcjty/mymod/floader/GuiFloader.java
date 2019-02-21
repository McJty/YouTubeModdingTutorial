package mcjty.mymod.floader;

import mcjty.mymod.ModLiquids;
import mcjty.mymod.MyMod;
import mcjty.mymod.tools.FluidStackRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GuiFloader extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/floader.png");

    public GuiFloader(TileFloader tileEntity, ContainerFloader container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        FluidStackRenderer.renderFluidStack(new FluidStack(ModLiquids.fload, 1000), guiLeft + 30, guiTop + 26);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
