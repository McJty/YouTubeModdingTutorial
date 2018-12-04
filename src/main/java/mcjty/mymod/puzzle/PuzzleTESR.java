package mcjty.mymod.puzzle;

import mcjty.mymod.tools.GenericBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PuzzleTESR extends TileEntitySpecialRenderer<TilePuzzle> {

    @Override
    public void render(TilePuzzle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        IBlockState state = te.getWorld().getBlockState(te.getPos());
        Block block = state.getBlock();
        if (!(block instanceof GenericBlock)) {
            return;
        }

        Minecraft.getMinecraft().entityRenderer.disableLightmap();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        setupRotation(state);
        GlStateManager.translate(0F, 0F, 0.9F);

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();

        if (te.isSolved()) {
            renderSlotHilight();
        }
        renderSlot(te);

        GlStateManager.popMatrix();
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }

    private void setupRotation(IBlockState state) {
        EnumFacing facing = state.getValue(BlockPuzzle.FACING);
        if (facing == EnumFacing.UP) {
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, -0.68F);
        } else if (facing == EnumFacing.DOWN) {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, -.184F);
        } else {
            float rotY = 0.0F;
            if (facing == EnumFacing.NORTH) {
                rotY = 180.0F;
            } else if (facing == EnumFacing.WEST) {
                rotY = 90.0F;
            } else if (facing == EnumFacing.EAST) {
                rotY = -90.0F;
            }
            GlStateManager.rotate(-rotY, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.2500F, -0.4375F);
        }
    }

    private void renderSlot(TilePuzzle te) {
        ItemStack stack = te.getItem();
        if (stack.isEmpty()) {
            return;
        }

        RenderHelper.enableGUIStandardItemLighting();
        float factor = 4.0f;
        float f3 = 0.0075F;
        GlStateManager.translate(-0.5F, 0.5F, -0.15F);
        GlStateManager.scale(f3 * factor, -f3 * factor, 0.0001f);

        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        itemRender.renderItemAndEffectIntoGUI(stack, 8, 8);

        RenderHelper.enableStandardItemLighting();
    }

    private void renderSlotHilight() {
        GlStateManager.pushMatrix();

        float factor = 4.0f;
        float f3 = 0.0075F;
        GlStateManager.translate(-0.5F, 0.5F, -0.155F);
        GlStateManager.scale(f3 * factor, -f3 * factor, f3);
        GlStateManager.disableLighting();

        Gui.drawRect(8-3, 8-3, 8 + 21, 8 + 21, 0x5566ff66);

        GlStateManager.popMatrix();
    }

}
