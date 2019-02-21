package mcjty.mymod.furnace;

import mcjty.mymod.MyMod;
import mcjty.mymod.config.FastFurnaceConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;

public class GuiFastFurnace extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace.png");
    private TileFastFurnace furnace;

    public GuiFastFurnace(TileFastFurnace tileEntity, ContainerFastFurnace container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        furnace = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int energy = furnace.getClientEnergy();
        drawEnergyBar(energy);

        if (furnace.getClientProgress() > 0) {
            int percentage = furnace.getClientProgress();
            drawString(mc.fontRenderer, "Progress: " + percentage + "%", guiLeft + 10, guiTop + 50, 0xffffff);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 10 && mouseX < guiLeft + 112 && mouseY > guiTop + 5 && mouseY < guiTop + 15) {
            drawHoveringText(Collections.singletonList("Energy: " + furnace.getClientEnergy()), mouseX, mouseY, fontRenderer);
        }
    }

    private void drawEnergyBar(int energy) {
        drawRect(guiLeft + 10, guiTop + 5, guiLeft + 112, guiTop + 15, 0xff555555);
        int percentage = energy * 100 / FastFurnaceConfig.MAX_POWER;
        for (int i = 0 ; i < percentage ; i++) {
            drawVerticalLine(guiLeft + 10 + 1 + i, guiTop + 5, guiTop + 14, i % 2 == 0 ? 0xffff0000 : 0xff000000);
        }
    }

}
