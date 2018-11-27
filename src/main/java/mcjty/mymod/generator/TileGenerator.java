package mcjty.mymod.generator;

import com.google.common.collect.ImmutableMap;
import mcjty.mymod.MyMod;
import mcjty.mymod.config.GeneratorConfig;
import mcjty.mymod.tools.IGuiTile;
import mcjty.mymod.tools.IRestorableTileEntity;
import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileGenerator extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile {

    @Override
    public void update() {
        if (!world.isRemote) {
        }
    }

    @Nullable
    private final IAnimationStateMachine asm;

    public TileGenerator() {
        asm = MyMod.proxy.load(new ResourceLocation(MyMod.MODID, "asms/block/generator.json"), ImmutableMap.of());
    }

    // ----------------------------------------------------------------------------------------

    private MyEnergyStorage energyStorage = new MyEnergyStorage(GeneratorConfig.MAX_POWER, 0);

    // ----------------------------------------------------------------------------------------

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
    }

    public void readRestorableFromNBT(NBTTagCompound compound) {
        energyStorage.setEnergy(compound.getInteger("energy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", energyStorage.getEnergyStored());
    }

    @Override
    public Container createContainer(EntityPlayer player) {
        return new ContainerGenerator(player.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiGenerator(this, new ContainerGenerator(player.inventory, this));
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
            return CapabilityAnimation.ANIMATION_CAPABILITY.cast(asm);
        }
        return super.getCapability(capability, facing);
    }


}
