package mcjty.mymod.furnace;

import mcjty.mymod.config.FastFurnaceConfig;
import mcjty.mymod.customrecipes.CustomRecipe;
import mcjty.mymod.customrecipes.CustomRecipeRegistry;
import mcjty.mymod.tools.IGuiTile;
import mcjty.mymod.tools.IRestorableTileEntity;
import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileFastFurnace extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile {

    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 3;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    private int progress = 0;
    private FurnaceState state = FurnaceState.OFF;

    private int clientProgress = -1;
    private int clientEnergy = -1;

    @Override
    public void update() {
        if (!world.isRemote) {

            if (energyStorage.getEnergyStored() < FastFurnaceConfig.RF_PER_TICK) {
                setState(FurnaceState.NOPOWER);
                return;
            }

            if (progress > 0) {
                setState(FurnaceState.WORKING);
                energyStorage.consumePower(FastFurnaceConfig.RF_PER_TICK);
                progress--;
                if (progress <= 0) {
                    attemptSmelt();
                }
                markDirty();
            } else {
                startSmelt();
            }
        }
    }

    private boolean insertOutput(ItemStack output, boolean simulate) {
        for (int i = 0 ; i < OUTPUT_SLOTS ; i++) {
            ItemStack remaining = outputHandler.insertItem(i, output, simulate);
            if (remaining.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    private void startSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; i++) {
            ItemStack result = getResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (insertOutput(result.copy(), true)) {
                    setState(FurnaceState.WORKING);
                    progress = FastFurnaceConfig.MAX_PROGRESS;
                    markDirty();
                    return;
                }
            }
        }
        setState(FurnaceState.OFF);
    }

    private void attemptSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; i++) {
            ItemStack result = getResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {
                    inputHandler.extractItem(i, 1, false);
                    break;
                }
            }
        }
    }

    private ItemStack getResult(ItemStack stackInSlot) {
        // @todo Make a cache for this! Both our own CustomRecipeRegistry.getRecipe() and
        // getSmeltingResult() loop over all recipes. This is very slow!
        CustomRecipe recipe = CustomRecipeRegistry.getRecipe(stackInSlot);
        if (recipe != null) {
            return recipe.getOutput();
        }
        return FurnaceRecipes.instance().getSmeltingResult(stackInSlot);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getClientProgress() {
        return clientProgress;
    }

    public void setClientProgress(int clientProgress) {
        this.clientProgress = clientProgress;
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

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger("state", state.ordinal());
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        int stateIndex = packet.getNbtCompound().getInteger("state");

        if (world.isRemote && stateIndex != state.ordinal()) {
            state = FurnaceState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }


    public void setState(FurnaceState state) {
        if (this.state != state) {
            this.state = state;
            markDirty();
            IBlockState blockState = world.getBlockState(pos);
            getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
        }
    }

    public FurnaceState getState() {
        return state;
    }


    // ----------------------------------------------------------------------------------------

    // This item handler will hold our three input slots
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = getResult(stack);
            return !result.isEmpty();
        }


        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileFastFurnace.this.markDirty();
        }
    };

    // This item handler will hold our three output slots
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileFastFurnace.this.markDirty();
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    // ----------------------------------------------------------------------------------------

    private MyEnergyStorage energyStorage = new MyEnergyStorage(FastFurnaceConfig.MAX_POWER, FastFurnaceConfig.RF_PER_TICK_INPUT);

    // ----------------------------------------------------------------------------------------

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
        state = FurnaceState.VALUES[compound.getInteger("state")];
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        progress = compound.getInteger("progress");
        energyStorage.setEnergy(compound.getInteger("energy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        compound.setInteger("state", state.ordinal());
        return compound;
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("progress", progress);
        compound.setInteger("energy", energyStorage.getEnergyStored());
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public Container createContainer(EntityPlayer player) {
        return new ContainerFastFurnace(player.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiFastFurnace(this, new ContainerFastFurnace(player.inventory, this));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            } else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            } else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }


}
