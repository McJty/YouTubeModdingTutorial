package mcjty.mymod;

import mcjty.mymod.fload.BlockFload;
import mcjty.mymod.furnace.BlockFastFurnace;
import mcjty.mymod.furnace.TileFastFurnace;
import mcjty.mymod.generator.BlockGenerator;
import mcjty.mymod.generator.TileGenerator;
import mcjty.mymod.puzzle.BlockPuzzle;
import mcjty.mymod.puzzle.TilePuzzle;
import mcjty.mymod.tank.BlockTank;
import mcjty.mymod.tank.TileTank;
import mcjty.mymod.worldgen.BlockFancyOre;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    @GameRegistry.ObjectHolder("mymod:fast_furnace")
    public static BlockFastFurnace blockFastFurnace;

    @GameRegistry.ObjectHolder("mymod:generator")
    public static BlockGenerator blockGenerator;

    @GameRegistry.ObjectHolder("mymod:fancy_ore")
    public static BlockFancyOre blockFancyOre;

    @GameRegistry.ObjectHolder("mymod:puzzle")
    public static BlockPuzzle blockPuzzle;

    @GameRegistry.ObjectHolder("mymod:fload")
    public static BlockFload blockFload;

    @GameRegistry.ObjectHolder("mymod:tank")
    public static BlockTank blockTank;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFastFurnace.initModel();
        blockGenerator.initModel();
        blockFancyOre.initModel();
        blockPuzzle.initModel();
        blockFload.initModel();
        blockTank.initModel();
    }

    public static void register(IForgeRegistry<Block> registry) {
        registry.register(new BlockFastFurnace());
        GameRegistry.registerTileEntity(TileFastFurnace.class, MyMod.MODID + "_fast_furnace");

        registry.register(new BlockGenerator());
        GameRegistry.registerTileEntity(TileGenerator.class, MyMod.MODID + "_generator");

        registry.register(new BlockPuzzle());
        GameRegistry.registerTileEntity(TilePuzzle.class, MyMod.MODID + "_puzzle");

        registry.register(new BlockTank());
        GameRegistry.registerTileEntity(TileTank.class, MyMod.MODID + "_tank");

        registry.register(new BlockFancyOre());
        registry.register(new BlockFload());
    }

}

