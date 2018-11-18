package mcjty.mymod;

import mcjty.mymod.furnace.BlockFastFurnace;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("mymod:fast_furnace")
    public static BlockFastFurnace blockFastFurnace;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFastFurnace.initModel();
    }

}
