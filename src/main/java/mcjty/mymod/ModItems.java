package mcjty.mymod;

import mcjty.mymod.floader.BlockFloader;
import mcjty.mymod.furnace.BlockFastFurnace;
import mcjty.mymod.generator.BlockGenerator;
import mcjty.mymod.items.ItemFancyIngot;
import mcjty.mymod.items.ItemWand;
import mcjty.mymod.puzzle.BlockPuzzle;
import mcjty.mymod.superchest.BlockSuperchest;
import mcjty.mymod.superchest.BlockSuperchestPart;
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

    @GameRegistry.ObjectHolder("mymod:wand")
    public static ItemWand itemWand;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemFancyIngot.initModel();
        itemWand.initModel();
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.blockFastFurnace).setRegistryName(BlockFastFurnace.FAST_FURNACE));
        registry.register(new ItemBlock(ModBlocks.blockGenerator).setRegistryName(BlockGenerator.GENERATOR));
        registry.register(new ItemBlock(ModBlocks.blockPuzzle).setRegistryName(BlockPuzzle.PUZZLE));
        registry.register(new ItemBlock(ModBlocks.blockTank).setRegistryName(BlockTank.TANK));
        registry.register(new ItemBlock(ModBlocks.blockFloader).setRegistryName(BlockFloader.FLOADER));
        registry.register(new ItemBlock(ModBlocks.blockSuperchest).setRegistryName(BlockSuperchest.SUPERCHEST));
        registry.register(new ItemBlock(ModBlocks.blockSuperchestPart).setRegistryName(BlockSuperchestPart.SUPERCHEST_PART));
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
        registry.register(new ItemWand());
    }

}
