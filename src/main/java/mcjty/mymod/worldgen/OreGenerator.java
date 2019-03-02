package mcjty.mymod.worldgen;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.config.OregenConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.placement.CountRange;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class OreGenerator {

    private static final Predicate<IBlockState> IS_NETHERACK = state -> state.getBlock() == Blocks.NETHERRACK;
    private static final Predicate<IBlockState> IS_ENDSTONE = state -> state.getBlock() == Blocks.END_STONE;

    public static void setupOregen() {
        for (Biome biome : ForgeRegistries.BIOMES) {

            CountRangeConfig placementConfig = new CountRangeConfig(OregenConfig.CHANCES_TO_SPAWN.get(), OregenConfig.MIN_Y.get(), OregenConfig.MIN_Y.get(), OregenConfig.MAX_Y.get());

            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    new DimensionCompositeFeature<>(Feature.MINABLE,
                            new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.blockFancyOreOverworld.getDefaultState(), OregenConfig.MAX_VEIN_SIZE.get()), new CountRange(),
                            placementConfig,
                            DimensionType.OVERWORLD));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    new DimensionCompositeFeature<>(Feature.MINABLE,
                            new MinableConfig(IS_NETHERACK, ModBlocks.blockFancyOreNether.getDefaultState(), OregenConfig.MAX_VEIN_SIZE.get()), new CountRange(),
                            placementConfig,
                            DimensionType.NETHER));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    new DimensionCompositeFeature<>(Feature.MINABLE,
                            new MinableConfig(IS_ENDSTONE, ModBlocks.blockFancyOreEnd.getDefaultState(), OregenConfig.MAX_VEIN_SIZE.get()), new CountRange(),
                            placementConfig,
                            DimensionType.THE_END));
        }
    }
}
