package mcjty.mymod;

import mcjty.mymod.floader.BlockFloader;
import mcjty.mymod.furnace.BlockFastFurnace;
import mcjty.mymod.generator.BlockGenerator;
import mcjty.mymod.items.ItemFancyIngot;
import mcjty.mymod.puzzle.BlockPuzzle;
import mcjty.mymod.tank.BlockTank;
import mcjty.mymod.worldgen.BlockFancyOre;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    @GameRegistry.ObjectHolder("mymod:fancy_ingot")
    public static ItemFancyIngot itemFancyIngot;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemFancyIngot.initModel();
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.blockFastFurnace).setRegistryName(BlockFastFurnace.FAST_FURNACE));
        registry.register(new ItemBlock(ModBlocks.blockGenerator).setRegistryName(BlockGenerator.GENERATOR));
        registry.register(new ItemBlock(ModBlocks.blockPuzzle).setRegistryName(BlockPuzzle.PUZZLE));
        registry.register(new ItemBlock(ModBlocks.blockTank).setRegistryName(BlockTank.TANK));
        registry.register(new ItemBlock(ModBlocks.blockFloader).setRegistryName(BlockFloader.FLOADER));
        registry.register(
                new ItemBlock(ModBlocks.blockFancyOre) {
                    @Override
                    public int getMetadata(int damage) {
                        return damage;
                    }
                }
                        .setHasSubtypes(true)
                        .setRegistryName(BlockFancyOre.FANCY_ORE));
        registry.register(new ItemFancyIngot());
    }

}
