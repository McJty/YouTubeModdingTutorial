package mcjty.mymod.rendering;

import mcjty.mymod.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayRenderer {

    public static OverlayRenderer instance = new OverlayRenderer();

    private float mana = 0;
    private float manaInfluence = 0;
    private float playerMana = 0;

    public void setMana(float mana, float manaInfluence, float playerMana) {
        this.mana = mana;
        this.manaInfluence = manaInfluence;
        this.playerMana = playerMana;
    }


    @SubscribeEvent
    public void renderGameOverlayEvent(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        if (Minecraft.getInstance().player.getHeldItem(EnumHand.MAIN_HAND).getItem() != ModItems.itemWand) {
            return;
        }

        GlStateManager.disableLighting();

        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

        int x = 200;
        int y = 10;
        x = fontRenderer.drawString("Mana ", x, y, 0xffffffff);
        x = fontRenderer.drawString("" + mana, x, y, 0xffff0000);
        x = fontRenderer.drawString("  Influence ", x, y, 0xffffffff);
        x = fontRenderer.drawString("" + manaInfluence, x, y, 0xffff0000);
        y += 10;
        x = 200;
        x = fontRenderer.drawString("Player ", x, y, 0xffffffff);
        x = fontRenderer.drawString("" + (playerMana), x, y, 0xffff0000);
    }
}