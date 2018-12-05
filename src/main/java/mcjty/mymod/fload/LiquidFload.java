package mcjty.mymod.fload;

import mcjty.mymod.MyMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidFload extends Fluid {

    public LiquidFload() {
        super("fload",
                new ResourceLocation(MyMod.MODID, "blocks/fload_still"),
                new ResourceLocation(MyMod.MODID, "blocks/fload_flow"));
    }

}
