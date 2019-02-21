package mcjty.mymod.superchest;

import mcjty.mymod.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileSuperchestPart extends TileEntity {

    public TileSuperchestPart() {
        super(ModBlocks.TYPE_SUPERCHEST_PART);
    }

    // @todo 1.13
//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
//        return oldState.getBlock() != newState.getBlock();
//    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != ModBlocks.blockSuperchestPart || state.get(BlockSuperchest.FORMED) == SuperchestPartIndex.UNFORMED) {
            return null;
        }

        BlockPos controllerPos = BlockSuperchest.getControllerPos(world, pos);
        if (controllerPos != null) {
            TileEntity te = world.getTileEntity(controllerPos);
            if (te instanceof TileSuperchest) {
                return te.getCapability(capability, facing);
            }
        }
        return super.getCapability(capability, facing);
    }
}
