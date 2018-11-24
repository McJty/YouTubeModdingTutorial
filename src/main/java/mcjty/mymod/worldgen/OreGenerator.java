package mcjty.mymod.worldgen;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import mcjty.mymod.config.OregenConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.ArrayDeque;
import java.util.Random;

public class OreGenerator implements IWorldGenerator {

    public static final String RETRO_NAME = "MyModOreGen";
    public static OreGenerator instance = new OreGenerator();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateWorld(random, chunkX, chunkZ, world, true);
    }

    public void generateWorld(Random random, int chunkX, int chunkZ, World world, boolean newGen) {
        if (!newGen && !OregenConfig.RETROGEN) {
            return;
        }

        if (world.provider.getDimension() == DimensionType.OVERWORLD.getId()) {
            if (OregenConfig.GENERATE_OVERWORLD) {
                addOreSpawn(ModBlocks.blockFancyOre, (byte) OreType.ORE_OVERWORLD.ordinal(), Blocks.STONE, world, random, chunkX * 16, chunkZ * 16,
                        OregenConfig.MIN_VEIN_SIZE, OregenConfig.MAX_VEIN_SIZE, OregenConfig.CHANCES_TO_SPAWN, OregenConfig.MIN_Y, OregenConfig.MAX_Y);
            }
        } else if (world.provider.getDimension() == DimensionType.NETHER.getId()) {
            if (OregenConfig.GENERATE_NETHER) {
                addOreSpawn(ModBlocks.blockFancyOre, (byte) OreType.ORE_NETHER.ordinal(), Blocks.NETHERRACK, world, random, chunkX * 16, chunkZ * 16,
                        OregenConfig.MIN_VEIN_SIZE, OregenConfig.MAX_VEIN_SIZE, OregenConfig.CHANCES_TO_SPAWN, OregenConfig.MIN_Y, OregenConfig.MAX_Y);
            }
        } else if (world.provider.getDimension() == DimensionType.THE_END.getId()) {
            if (OregenConfig.GENERATE_END) {
                addOreSpawn(ModBlocks.blockFancyOre, (byte) OreType.ORE_END.ordinal(), Blocks.END_STONE, world, random, chunkX * 16, chunkZ * 16,
                        OregenConfig.MIN_VEIN_SIZE, OregenConfig.MAX_VEIN_SIZE, OregenConfig.CHANCES_TO_SPAWN, OregenConfig.MIN_Y, OregenConfig.MAX_Y);
            }
        }

        if (!newGen) {
            world.getChunk(chunkX, chunkZ).markDirty();
        }
    }

    public void addOreSpawn(Block block, byte blockMeta, Block targetBlock, World world, Random random, int blockXPos, int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        WorldGenMinable minable = new WorldGenMinable(block.getStateFromMeta(blockMeta), (minVeinSize + random.nextInt(maxVeinSize - minVeinSize + 1)), BlockMatcher.forBlock(targetBlock));
        for (int i = 0 ; i < chancesToSpawn ; i++) {
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(16);
            minable.generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }

    @SubscribeEvent
    public void handleChunkSaveEvent(ChunkDataEvent.Save event) {
        NBTTagCompound genTag = event.getData().getCompoundTag(RETRO_NAME);
        if (!genTag.hasKey("generated")) {
            // If we did not have this key then this is a new chunk and we will have proper ores generated.
            // Otherwise we are saving a chunk for which ores are not yet generated.
            genTag.setBoolean("generated", true);
        }
        event.getData().setTag(RETRO_NAME, genTag);
    }

    @SubscribeEvent
    public void handleChunkLoadEvent(ChunkDataEvent.Load event) {
        int dim = event.getWorld().provider.getDimension();

        boolean regen = false;
        NBTTagCompound tag = (NBTTagCompound) event.getData().getTag(RETRO_NAME);
        ChunkPos coord = event.getChunk().getPos();

        if (tag != null) {
            boolean generated = OregenConfig.RETROGEN && !tag.hasKey("generated");
            if (generated) {
                if (OregenConfig.VERBOSE) {
                    MyMod.logger.log(Level.DEBUG, "Queuing Retrogen for chunk: " + coord.toString() + ".");
                }
                regen = true;
            }
        } else {
            regen = OregenConfig.RETROGEN;
        }

        if (regen) {
            ArrayDeque<ChunkPos> chunks = WorldTickHandler.chunksToGen.get(dim);

            if (chunks == null) {
                WorldTickHandler.chunksToGen.put(dim, new ArrayDeque<>(128));
                chunks = WorldTickHandler.chunksToGen.get(dim);
            }
            if (chunks != null) {
                chunks.addLast(coord);
                WorldTickHandler.chunksToGen.put(dim, chunks);
            }
        }
    }



}
