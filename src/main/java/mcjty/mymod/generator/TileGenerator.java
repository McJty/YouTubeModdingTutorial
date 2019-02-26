package mcjty.mymod.generator;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.config.GeneratorConfig;
import mcjty.mymod.tools.IGuiTile;
import mcjty.mymod.tools.IRestorableTileEntity;
import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.List;

public class TileGenerator extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile {

    private int trackCounter = 0;
    private AxisAlignedBB trackingBox;
    private int clientEnergy = -1;


//    @Nullable
//    private final IAnimationStateMachine asm;

    public TileGenerator() {
        super(ModBlocks.TYPE_GENERATOR);
        // @todo 1.13
//        asm = MyMod.proxy.load(new ResourceLocation(MyMod.MODID, "asms/block/generator.json"), ImmutableMap.of());
    }

    // ----------------------------------------------------------------------------------------

    private MyEnergyStorage energyStorage = new MyEnergyStorage(GeneratorConfig.MAX_POWER.get(), 0);

    // ----------------------------------------------------------------------------------------

    @Override
    public void tick() {
        if (!world.isRemote) {
            trackCounter--;
            if (trackCounter <= 0) {
                trackCounter = 20;
                findEntities();
            }

            sendEnergy();
        }
    }

    @Override
    public void remove() {
        super.remove();
        DamageTracker.instance.remove(world.getDimension().getType(), pos);
    }

    private void sendEnergy() {
        if (energyStorage.getEnergyStored() > 0) {
            for (EnumFacing facing : EnumFacing.values()) {
                TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                if (tileEntity != null) {
                    if (
                            tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite())
                                    .map(handler -> {
                                        if (handler.canReceive()) {
                                            int accepted = handler.receiveEnergy(energyStorage.getEnergyStored(), false);
                                            energyStorage.consumePower(accepted);
                                            if (energyStorage.getEnergyStored() <= 0) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    }).orElse(false)) {
                        break;
                    }
                }
            }
            markDirty();
        }
    }

    public int getClientEnergy() {
        return clientEnergy;
    }

    public void setClientEnergy(int clientEnergy) {
        this.clientEnergy = clientEnergy;
    }

    public int getEnergy() {
        return energyStorage.getEnergyStored();
    }

    private void findEntities() {
        DamageTracker.instance.clear(world.getDimension().getType(), pos);

        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, getTrackingBox());
        for (EntityLivingBase entity : entities) {
            DamageTracker.instance.register(world.getDimension().getType(), pos, entity.getUniqueID());
        }
    }


    private AxisAlignedBB getTrackingBox() {
        if (trackingBox == null) {
            trackingBox = new AxisAlignedBB(pos.add(-5, -3, -5), pos.add(5, 3, 5));
        }
        return trackingBox;
    }

    public void senseDamage(EntityLivingBase entity, float amount) {
        if (getTrackingBox().contains(entity.getPositionVector())) {
            energyStorage.generatePower((int) (amount * GeneratorConfig.POWER_DAMAGE_FACTOR.get()));
        }
    }


    // ----------------------------------------------------------------------------------------

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }

    public void readRestorableFromNBT(NBTTagCompound compound) {
        energyStorage.setEnergy(compound.getInt("energy"));
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setInt("energy", energyStorage.getEnergyStored());
    }

//    @Override
//    public Container createContainer(EntityPlayer player) {
//        return new ContainerGenerator(player.inventory, this);
//    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiGenerator(this, new ContainerGenerator(player.inventory, this));
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isRemoved() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }


    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return LazyOptional.of(() -> (T) energyStorage);
        }
//        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
//            return LazyOptional.of(() -> (T) asm);
//        }
        return super.getCapability(capability, facing);
    }


}
