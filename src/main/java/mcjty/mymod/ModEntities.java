package mcjty.mymod;

import mcjty.mymod.guard.EntityGuard;
import mcjty.mymod.guard.RenderGuard;
import mcjty.mymod.laser.EntitySphere;
import mcjty.mymod.laser.RenderSphere;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModEntities {

    public static EntityType<EntityGuard> TYPE_GUARD;
    public static EntityType<EntitySphere> TYPE_SPHERE;

    public static void register(IForgeRegistry<EntityType<?>> registry) {
        int id = 1;

        registry.register(TYPE_GUARD = EntityType.Builder.create(EntityGuard.class, EntityGuard::new).build("mymod_guard"));
        registry.register(TYPE_SPHERE = EntityType.Builder.create(EntitySphere.class, EntitySphere::new).build("mymod_sphere"));
//        EntityRegistry.registerModEntity(new ResourceLocation(MyMod.MODID, "mymod_guard"), EntityGuard.class, "mymod_guard", id++,
//                MyMod.instance, 64, 3, true, 0x222222, 0x555555);
//        EntityRegistry.registerModEntity(new ResourceLocation(MyMod.MODID, "mymod_sphere"), EntitySphere.class, "mymod_sphere", id++,
//                MyMod.instance, 64, 1, false);
    }

    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGuard.class, RenderGuard.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySphere.class, RenderSphere.FACTORY);
    }
}
