package mcjty.mymod;

import mcjty.mymod.guard.EntityGuard;
import mcjty.mymod.guard.RenderGuard;
import mcjty.mymod.laser.EntitySphere;
import mcjty.mymod.laser.RenderSphere;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    public static void init() {
        int id = 1;

        EntityRegistry.registerModEntity(new ResourceLocation(MyMod.MODID, "mymod_guard"), EntityGuard.class, "mymod_guard", id++,
                MyMod.instance, 64, 3, true, 0x222222, 0x555555);
        EntityRegistry.registerModEntity(new ResourceLocation(MyMod.MODID, "mymod_sphere"), EntitySphere.class, "mymod_sphere", id++,
                MyMod.instance, 64, 1, false);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGuard.class, RenderGuard.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySphere.class, RenderSphere.FACTORY);
    }
}
