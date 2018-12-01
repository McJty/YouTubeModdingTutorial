package mcjty.mymod.guard;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGuard extends RenderLiving<EntityGuard> {

    private ResourceLocation mobTexture = new ResourceLocation("mymod:textures/entity/guard.png");

    public RenderGuard(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelGuard(), 0.8F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityGuard entity) {
        return mobTexture;
    }

    public static final RenderGuard.Factory FACTORY = new RenderGuard.Factory();

    public static class Factory implements IRenderFactory<EntityGuard> {

        @Override
        public Render<? super EntityGuard> createRenderFor(RenderManager manager) {
            return new RenderGuard(manager);
        }

    }

}