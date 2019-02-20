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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("mymod:fancy_ingot")
    public static ItemFancyIngot itemFancyIngot;

    @ObjectHolder("mymod:wand")
    public static ItemWand itemWand;


    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.blockFastFurnace, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockFastFurnace.FAST_FURNACE));
        registry.register(new ItemBlock(ModBlocks.blockGenerator, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockGenerator.GENERATOR));
        registry.register(new ItemBlock(ModBlocks.blockPuzzle, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockPuzzle.PUZZLE));
        registry.register(new ItemBlock(ModBlocks.blockTank, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockTank.TANK));
        registry.register(new ItemBlock(ModBlocks.blockFloader, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockFloader.FLOADER));
        registry.register(new ItemBlock(ModBlocks.blockSuperchest, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockSuperchest.SUPERCHEST));
        registry.register(new ItemBlock(ModBlocks.blockSuperchestPart, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockSuperchestPart.SUPERCHEST_PART));
        registry.register(new ItemBlock(ModBlocks.blockFancyOreOverworld, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockFancyOre.FANCY_ORE_OVERWORLD));
        registry.register(new ItemBlock(ModBlocks.blockFancyOreNether, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockFancyOre.FANCY_ORE_NETHER));
        registry.register(new ItemBlock(ModBlocks.blockFancyOreEnd, new Item.Properties().group(MyMod.creativeTab)).setRegistryName(BlockFancyOre.FANCY_ORE_END));
        registry.register(new ItemFancyIngot());
        registry.register(new ItemWand());
    }

}
