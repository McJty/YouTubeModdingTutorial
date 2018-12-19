package mcjty.mymod.laser;

import mcjty.mymod.MyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSphere extends Render<EntitySphere> {

    private static ResourceLocation sphere = new ResourceLocation(MyMod.MODID, "textures/effects/sphere.png");

    public RenderSphere(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntitySphere entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.depthMask(false);

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        rotateToPlayer();

        // ----------------------------------------

        this.bindTexture(sphere);

        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0f, 1.0f, 1.0f);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

        long t = System.currentTimeMillis() % 6;
        renderBillboardQuad(0.3f, t * (1.0f / 6.0f), (1.0f / 6.0f));

        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(true);

        GlStateManager.popMatrix();
    }

    private void renderBillboardQuad(double scale, float vAdd1, float vAdd2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0 + vAdd1).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, +scale, 0).tex(1, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, -scale, 0).tex(1, 0 + vAdd1).endVertex();
        tessellator.draw();
    }

    private void rotateToPlayer() {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    }


    public static final Factory FACTORY = new Factory();

    @Override
    protected ResourceLocation getEntityTexture(EntitySphere entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntitySphere> {

        @Override
        public Render<? super EntitySphere> createRenderFor(RenderManager manager) {
            return new RenderSphere(manager);
        }

    }
}
