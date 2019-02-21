package mcjty.mymod.generator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class DamageTracker {

    public static final DamageTracker instance = new DamageTracker();

    private Map<Integer, Map<BlockPos, Set<UUID>>> tracking = new HashMap<>();

    public void reset() {
        tracking.clear();
    }

    public void register(DimensionType dimension, BlockPos pos, UUID entity) {
        if (!tracking.containsKey(dimension.getId())) {
            tracking.put(dimension.getId(), new HashMap<>());
        }
        Map<BlockPos, Set<UUID>> dimensionTracking = tracking.get(dimension.getId());
        if (!dimensionTracking.containsKey(pos)) {
            dimensionTracking.put(pos, new HashSet<>());
        }
        dimensionTracking.get(pos).add(entity);
    }

    public void remove(DimensionType dimension, BlockPos pos) {
        if (tracking.containsKey(dimension.getId())) {
            tracking.get(dimension.getId()).remove(pos);
        }
    }

    public void clear(DimensionType dimension, BlockPos pos) {
        if (tracking.containsKey(dimension.getId())) {
            Map<BlockPos, Set<UUID>> dimensionTracking = tracking.get(dimension.getId());
            if (dimensionTracking.containsKey(pos)) {
                dimensionTracking.get(pos).clear();
            }
        }
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        float amount = event.getAmount();
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.world;
        int dimension = world.getDimension().getType().getId();
        if (amount > 0 && tracking.containsKey(dimension)) {
            Map<BlockPos, Set<UUID>> dimensionTracking = tracking.get(dimension);
            for (Map.Entry<BlockPos, Set<UUID>> entry : dimensionTracking.entrySet()) {
                if (entry.getValue().contains(entity.getUniqueID())) {
                    if (world.isBlockLoaded(entry.getKey())) {
                        TileEntity tileEntity = world.getTileEntity(entry.getKey());
                        if (tileEntity instanceof TileGenerator) {
                            ((TileGenerator) tileEntity).senseDamage(entity, amount);
                        }
                    }
                }
            }
        }

    }
}
