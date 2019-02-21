package mcjty.mymod.superchest;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.tools.IGuiTile;
import mcjty.mymod.tools.IRestorableTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileSuperchest extends TileEntity implements IRestorableTileEntity, IGuiTile {

    public TileSuperchest() {
        super(ModBlocks.TYPE_SUPERCHEST);
    }

    // @todo 1.13
//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
//        return oldState.getBlock() != newState.getBlock();
//    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        writeRestorableToNBT(compound);
        return super.write(compound);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("items")) {
            itemHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setTag("items", itemHandler.serializeNBT());
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isRemoved() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public Container createContainer(EntityPlayer player) {
        return new ContainerSuperchest(player.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiSuperchest(this, new ContainerSuperchest(player.inventory, this));
    }


    private ItemStackHandler itemHandler = new ItemStackHandler(3*9) {

        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileSuperchest.this.markDirty();
        }
    };

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != ModBlocks.blockSuperchest || state.get(BlockSuperchest.FORMED) == SuperchestPartIndex.UNFORMED) {
            return null;
        }

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) itemHandler);
        }
        return super.getCapability(capability, facing);
    }

}
