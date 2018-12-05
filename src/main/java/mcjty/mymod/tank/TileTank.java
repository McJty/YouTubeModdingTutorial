package mcjty.mymod.tank;

import mcjty.mymod.tools.IRestorableTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TileTank extends TileEntity implements IRestorableTileEntity {

    public static final int MAX_CONTENTS = 10000;       // 10 buckets

    private FluidTank tank = new FluidTank(MAX_CONTENTS);

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        NBTTagCompound tankNBT = new NBTTagCompound();
        tank.writeToNBT(tankNBT);
        nbtTag.setTag("tank", tankNBT);
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        tank.readFromNBT(packet.getNbtCompound().getCompoundTag("tank"));
    }


    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        readRestorableFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeRestorableToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        tank.readFromNBT(compound.getCompoundTag("tank"));
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        NBTTagCompound tankNBT = new NBTTagCompound();
        tank.writeToNBT(tankNBT);
        compound.setTag("tank", tankNBT);
    }

    public FluidTank getTank() {
        return tank;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }

}
