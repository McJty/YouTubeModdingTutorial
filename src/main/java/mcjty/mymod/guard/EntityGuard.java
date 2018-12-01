package mcjty.mymod.guard;

import mcjty.mymod.MyMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityGuard extends EntityCreature implements IAnimals {

    public static final ResourceLocation LOOT = new ResourceLocation(MyMod.MODID, "entities/guard");

    private int attackTimer;

    public EntityGuard(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(3, new GuardAIFollow(this, 1.0D, 6.0F, 9.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 4, false, true,
                entity -> entity != null && IMob.VISIBLE_MOB_SELECTOR.apply(entity)));
    }


    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    public float getEyeHeight() {
        return 1.74F;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 1.0F);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));
    }
}