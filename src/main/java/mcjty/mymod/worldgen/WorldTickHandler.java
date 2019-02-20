package mcjty.mymod.worldgen;

import io.netty.util.collection.IntObjectHashMap;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayDeque;
import java.util.Random;

public class WorldTickHandler {

    public static WorldTickHandler instance = new WorldTickHandler();

    public static IntObjectHashMap<ArrayDeque<ChunkPos>> chunksToGen = new IntObjectHashMap<>();

    @SubscribeEvent
    public void tickEnd(TickEvent.WorldTickEvent event) {
        if (event.side != LogicalSide.SERVER) {
            return;
        }

        if (event.phase == TickEvent.Phase.END) {

            World world = event.world;
            int dim = world.getDimension().getType().getId();

            ArrayDeque<ChunkPos> chunks = chunksToGen.get(dim);

            if (chunks != null && !chunks.isEmpty()) {
                ChunkPos c = chunks.pollFirst();
                long worldSeed = world.getSeed();
                Random rand = new Random(worldSeed);
                long xSeed = rand.nextLong() >> 2 + 1L;
                long zSeed = rand.nextLong() >> 2 + 1L;
                rand.setSeed(xSeed * c.x + zSeed * c.z ^ worldSeed);
                OreGenerator.instance.generateWorld(rand, c.x, c.z, world, false);
                chunksToGen.put(dim, chunks);
            } else if (chunks != null) {
                chunksToGen.remove(dim);
            }
        }
    }

}