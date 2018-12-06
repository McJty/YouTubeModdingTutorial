package mcjty.mymod.floader;

import mcjty.mymod.ModLiquids;
import mcjty.mymod.tools.IGuiTile;
import mcjty.mymod.tools.IRestorableTileEntity;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileFloader extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile {

    public static final int INPUT_SLOTS = 1;

    @Override
    public void update() {
        if (!world.isRemote) {
            // Do something is we have a full water block below. We have a feather ready in our inventory
            // and we can find a tank that can accept 100mb of Fload
            IBlockState stateDown = world.getBlockState(pos.down());
            if (stateDown.getBlock() == Blocks.WATER) {
                if (stateDown.getValue(BlockStaticLiquid.LEVEL) == 0) {
                    // Test extracting a feather
                    ItemStack extracted = inputHandler.extractItem(0, 1, true);
                    if (extracted.getItem() == Items.FEATHER) {
                        if (findTankAndFill()) {
                            // All is ok. Really extract the feature and remove the water block
                            world.setBlockToAir(pos.down());
                            inputHandler.extractItem(0, 1, false);
                        }
                    }
                }
            }
        }
    }

    private boolean findTankAndFill() {
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (facing != EnumFacing.DOWN) {
                TileEntity te = world.getTileEntity(pos.offset(facing));
                if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())) {
                    IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
                    // Simulate filling
                    if (handler != null && handler.fill(new FluidStack(ModLiquids.fload, 100), true) != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // ----------------------------------------------------------------------------------------

    // This item handler will hold our input slot
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() == Items.FEATHER;
        }


        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileFloader.this.markDirty();
        }
    };

    // ----------------------------------------------------------------------------------------

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setTag("itemsIn", inputHandler.serializeNBT());
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public Container createContainer(EntityPlayer player) {
        return new ContainerFloader(player.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiFloader(this, new ContainerFloader(player.inventory, this));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
        }
        return super.getCapability(capability, facing);
    }

}
