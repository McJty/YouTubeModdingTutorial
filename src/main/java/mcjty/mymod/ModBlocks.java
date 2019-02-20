package mcjty.mymod;

import mcjty.mymod.fload.BlockFload;
import mcjty.mymod.floader.BlockFloader;
import mcjty.mymod.floader.TileFloader;
import mcjty.mymod.furnace.BlockFastFurnace;
import mcjty.mymod.furnace.TileFastFurnace;
import mcjty.mymod.generator.BlockGenerator;
import mcjty.mymod.generator.TileGenerator;
import mcjty.mymod.puzzle.BlockPuzzle;
import mcjty.mymod.puzzle.TilePuzzle;
import mcjty.mymod.superchest.BlockSuperchest;
import mcjty.mymod.superchest.BlockSuperchestPart;
import mcjty.mymod.superchest.TileSuperchest;
import mcjty.mymod.superchest.TileSuperchestPart;
import mcjty.mymod.tank.BlockTank;
import mcjty.mymod.tank.TileTank;
import mcjty.mymod.worldgen.BlockFancyOre;
import mcjty.mymod.worldgen.OreType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("mymod:fast_furnace")
    public static BlockFastFurnace blockFastFurnace;

    @ObjectHolder("mymod:generator")
    public static BlockGenerator blockGenerator;

    @ObjectHolder("mymod:fancy_ore_overworld")
    public static BlockFancyOre blockFancyOreOverworld;

    @ObjectHolder("mymod:fancy_ore_nether")
    public static BlockFancyOre blockFancyOreNether;

    @ObjectHolder("mymod:fancy_ore_end")
    public static BlockFancyOre blockFancyOreEnd;

    @ObjectHolder("mymod:puzzle")
    public static BlockPuzzle blockPuzzle;

    @ObjectHolder("mymod:fload")
    public static BlockFload blockFload;

    @ObjectHolder("mymod:tank")
    public static BlockTank blockTank;

    @ObjectHolder("mymod:floader")
    public static BlockFloader blockFloader;

    @ObjectHolder("mymod:superchest")
    public static BlockSuperchest blockSuperchest;

    @ObjectHolder("mymod:superchest_part")
    public static BlockSuperchestPart blockSuperchestPart;

    public static TileEntityType<?> TYPE_FLOADER;
    public static TileEntityType<?> TYPE_FAST_FURNACE;

    @OnlyIn(Dist.CLIENT)
    public static void initModels() {
//        blockFastFurnace.initModel();
//        blockGenerator.initModel();
//        blockFancyOre.initModel();
//        blockPuzzle.initModel();
//        blockFload.initModel();
//        blockTank.initModel();
//        blockFloader.initModel();
//        blockSuperchest.initModel();
//        blockSuperchestPart.initModel();
    }

    public static void registerTiles(IForgeRegistry<TileEntityType<?>> registry) {
        registry.register(TileEntityType.Builder.create(TileFastFurnace::new).build(null).setRegistryName(new ResourceLocation(MyMod.MODID, "fast_furnace")));
        registry.register(TileEntityType.Builder.create(TileGenerator::new).build(null).setRegistryName(new ResourceLocation(MyMod.MODID, "generator")));
        registry.register(TileEntityType.Builder.create(TilePuzzle::new).build(null).setRegistryName(new ResourceLocation(MyMod.MODID, "puzzle")));
        registry.register(TYPE_FAST_FURNACE = TileEntityType.Builder.create(TileTank::new).build(null).setRegistryName(new ResourceLocation(MyMod.MODID, "tank")));
        registry.register(TYPE_FLOADER = TileEntityType.Builder.create(TileFloader::new).build(null).setRegistryName(new ResourceLocation(MyMod.MODID, "floader")));
        registry.register(TileEntityType.Builder.create(TileSuperchest::new).build(null).setRegistryName(new ResourceLocation(MyMod.MODID, "superchest")));
        registry.register(TileEntityType.Builder.create(TileSuperchestPart::new).build(null).setRegistryName(new ResourceLocation(MyMod.MODID, "superchestpart")));
    }

    public static void register(IForgeRegistry<Block> registry) {
        registry.register(new BlockFastFurnace());
        registry.register(new BlockGenerator());
        registry.register(new BlockPuzzle());
        registry.register(new BlockTank());
        registry.register(new BlockFloader());
        registry.register(new BlockSuperchest());
        registry.register(new BlockSuperchestPart());
        registry.register(new BlockFancyOre(OreType.ORE_OVERWORLD, BlockFancyOre.FANCY_ORE_OVERWORLD));
        registry.register(new BlockFancyOre(OreType.ORE_NETHER, BlockFancyOre.FANCY_ORE_NETHER));
        registry.register(new BlockFancyOre(OreType.ORE_END, BlockFancyOre.FANCY_ORE_END));
        registry.register(new BlockFload());
    }

}

