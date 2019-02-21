package mcjty.mymod.guard;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;

import java.util.List;

public class GuardAIFollow extends EntityAIBase {

    private final EntityLiving entity;
    private EntityPlayer followingPlayer;
    private final double speedModifier;
    private final PathNavigate navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private float oldWaterCost;
    private final float areaSize;

    public GuardAIFollow(final EntityLiving entity, double speedModifier, float stopDistance, float areaSize) {
        this.entity = entity;
        this.speedModifier = speedModifier;
        this.navigation = entity.getNavigator();
        this.stopDistance = stopDistance;
        this.areaSize = areaSize;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        List<EntityPlayer> list = this.entity.world.getEntitiesWithinAABB(EntityPlayer.class, this.entity.getBoundingBox().grow((double) this.areaSize));

        for (EntityPlayer player : list) {
            if (!player.isInvisible()) {
                this.followingPlayer = player;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.followingPlayer != null && !this.navigation.noPath() && this.entity.getDistanceSq(this.followingPlayer) > (double) (this.stopDistance * this.stopDistance);
    }

    @Override
    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
        this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void resetTask() {
        this.followingPlayer = null;
        this.navigation.clearPath();
        this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }


    @Override
    public void tick() {
        if (this.followingPlayer != null && !this.entity.getLeashed()) {
            this.entity.getLookHelper().setLookPositionWithEntity(this.followingPlayer, 10.0F, (float) this.entity.getVerticalFaceSpeed());

            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                double x = this.entity.posX - this.followingPlayer.posX;
                double y = this.entity.posY - this.followingPlayer.posY;
                double z = this.entity.posZ - this.followingPlayer.posZ;
                double squaredDist = x * x + y * y + z * z;

                if (squaredDist > (double) (this.stopDistance * this.stopDistance)) {
                    this.navigation.tryMoveToEntityLiving(this.followingPlayer, this.speedModifier);
                } else {
                    this.navigation.clearPath();
                }
            }
        }
    }
}