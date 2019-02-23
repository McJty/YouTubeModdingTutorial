package mcjty.mymod.floader;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.ModLiquids;
import mcjty.mymod.tools.IGuiTile;
import mcjty.mymod.tools.IRestorableTileEntity;
import net.minecraft.block.BlockFlowingFluid;
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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileFloader extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile {

    public static final int INPUT_SLOTS = 1;

    public TileFloader() {
        super(ModBlocks.TYPE_FLOADER);
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            // Do something is we have a full water block below. We have a feather ready in our inventory
            // and we can find a tank that can accept 100mb of Fload
            IBlockState stateDown = world.getBlockState(pos.down());
            if (stateDown.getBlock() == Blocks.WATER) {
                if (stateDown.get(BlockFlowingFluid.LEVEL) == 0) {
                    // Test extracting a feather
                    ItemStack extracted = inputHandler.extractItem(0, 1, true);
                    if (extracted.getItem() == Items.FEATHER) {
                        if (findTankAndFill()) {
                            // All is ok. Really extract the feature and remove the water block
                            world.removeBlock(pos.down());
                            inputHandler.extractItem(0, 1, false);
                        }
                    }
                }
            }
        }
    }

    private boolean findTankAndFill() {
        for (EnumFacing facing : EnumFacing.values()) {
            if (facing != EnumFacing.DOWN) {
                TileEntity te = world.getTileEntity(pos.offset(facing));
                if (te != null) {
                    if (te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())
                            .filter(handler -> handler.fill(new FluidStack(ModLiquids.fload, 100), true) != 0)
                            .isPresent()) {
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
    public void read(NBTTagCompound compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setTag("itemsIn", inputHandler.serializeNBT());
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isRemoved() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

//    @Override
//    public Container createContainer(EntityPlayer player) {
//        return new ContainerFloader(player.inventory, this);
//    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiFloader(this, new ContainerFloader(player.inventory, this));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) inputHandler);
        }
        return super.getCapability(capability);
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) inputHandler);
        }
        return super.getCapability(capability, facing);
    }

}
