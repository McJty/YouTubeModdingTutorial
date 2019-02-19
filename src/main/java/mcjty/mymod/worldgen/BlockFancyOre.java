package mcjty.mymod.worldgen;

import mcjty.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockFancyOre extends Block {

//    public static final EnumProperty<OreType> ORETYPE = EnumProperty.create("oretype", OreType.class);
    public static final ResourceLocation FANCY_ORE_OVERWORLD = new ResourceLocation(MyMod.MODID, "fancy_ore_overworld");
    public static final ResourceLocation FANCY_ORE_NETHER = new ResourceLocation(MyMod.MODID, "fancy_ore_nether");
    public static final ResourceLocation FANCY_ORE_END = new ResourceLocation(MyMod.MODID, "fancy_ore_end");

    private final OreType type;

    public BlockFancyOre(OreType type, ResourceLocation oreName) {
        super(Properties.create(Material.ROCK)
            .hardnessAndResistance(3.0f, 5.0f));
        // @todo 1.13
//        setHarvestLevel("pickaxe", 2);
        setRegistryName(oreName);
        // @todo move to ItemBlock
//        setCreativeTab(MyMod.creativeTab);
        this.type = type;
    }

    // @todo 1.13
//    @Override
//    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subItems) {
//        subItems.add(new ItemStack(this, 1, 0));
//        subItems.add(new ItemStack(this, 1, 1));
//        subItems.add(new ItemStack(this, 1, 2));
//    }
}
