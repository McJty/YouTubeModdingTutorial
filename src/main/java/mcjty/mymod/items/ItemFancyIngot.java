package mcjty.mymod.items;

import mcjty.mymod.MyMod;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemFancyIngot extends Item {

    public ItemFancyIngot() {
        super(new Properties().group(MyMod.creativeTab));
        setRegistryName(new ResourceLocation(MyMod.MODID, "fancy_ingot"));
    }
}
