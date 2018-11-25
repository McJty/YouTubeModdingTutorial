package mcjty.mymod.generator;

import mcjty.mymod.tools.IMachineStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerGenerator extends Container implements IMachineStateContainer {

    private TileGenerator te;

    public ContainerGenerator(IInventory playerInventory, TileGenerator te) {
        this.te = te;

        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

//        if (te.getProgress() != te.getClientProgress() || te.getEnergy() != te.getClientEnergy()) {
//            te.setClientEnergy(te.getEnergy());
//            te.setClientProgress(te.getProgress());
//
//            for (IContainerListener listener : listeners) {
//                if (listener instanceof EntityPlayerMP) {
//                    EntityPlayerMP player = (EntityPlayerMP) listener;
//                    int pct = 100 - te.getProgress() * 100 / FastFurnaceConfig.MAX_PROGRESS;
//                    Messages.INSTANCE.sendTo(new PacketSyncMachineState(te.getEnergy(), pct), player);
//                }
//            }
//        }
    }

    @Override
    public void sync(int energy, int progress) {
//        te.setClientEnergy(energy);
//        te.setClientProgress(progress);
    }
}
