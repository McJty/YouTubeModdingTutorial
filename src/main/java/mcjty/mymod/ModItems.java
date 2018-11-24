package mcjty.mymod;

import mcjty.mymod.items.ItemFancyIngot;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("mymod:fancy_ingot")
    public static ItemFancyIngot itemFancyIngot;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemFancyIngot.initModel();
    }

}
