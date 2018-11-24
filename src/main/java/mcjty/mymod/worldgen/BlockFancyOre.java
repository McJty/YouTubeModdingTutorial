package mcjty.mymod.worldgen;

import mcjty.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFancyOre extends Block {

    public static final PropertyEnum<OreType> ORETYPE = PropertyEnum.create("oretype", OreType.class);
    public static final ResourceLocation FANCY_ORE = new ResourceLocation(MyMod.MODID, "fancy_ore");

    public BlockFancyOre() {
        super(Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setHarvestLevel("pickaxe", 2);
        setTranslationKey(MyMod.MODID + ".fancy_ore");
        setRegistryName(FANCY_ORE);
        setCreativeTab(MyMod.creativeTab);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(ORETYPE).ordinal();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "oretype=overworld"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 1, new ModelResourceLocation(getRegistryName(), "oretype=nether"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 2, new ModelResourceLocation(getRegistryName(), "oretype=end"));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        subItems.add(new ItemStack(this, 1, 0));
        subItems.add(new ItemStack(this, 1, 1));
        subItems.add(new ItemStack(this, 1, 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ORETYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ORETYPE, OreType.values()[meta]);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ORETYPE);
    }
}
