package mcjty.mymod.items;

import mcjty.mymod.MyMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFancyIngot extends Item {

    public ItemFancyIngot() {
        setTranslationKey(MyMod.MODID + ".fancy_ingot");
        setRegistryName(new ResourceLocation(MyMod.MODID, "fancy_ingot"));
        setCreativeTab(MyMod.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
