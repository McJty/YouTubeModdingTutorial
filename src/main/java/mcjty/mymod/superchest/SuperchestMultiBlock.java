package mcjty.mymod.superchest;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.tools.IMultiBlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SuperchestMultiBlock implements IMultiBlockType {

    public static SuperchestMultiBlock INSTANCE = new SuperchestMultiBlock();

    private boolean isBlockPart(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == ModBlocks.blockSuperchest || state.getBlock() == ModBlocks.blockSuperchestPart;
    }

    private boolean isValidFormedBlockPart(World world, BlockPos pos, int dx, int dy, int dz) {
        BlockPos p = pos;
        if (isFormedSuperchestController(world, p)) {
            SuperchestPartIndex index = world.getBlockState(p).getValue(BlockSuperchest.FORMED);
            return index == SuperchestPartIndex.getIndex(dx, dy, dz);
        } else if (isFormedSuperchestPart(world, p)) {
            SuperchestPartIndex index = world.getBlockState(p).getValue(BlockSuperchest.FORMED);
            return index == SuperchestPartIndex.getIndex(dx, dy, dz);
        } else {
            // We can already stop here
            return false;
        }
    }

    private boolean isValidUnformedBlockPart(World world, BlockPos pos, int dx, int dy, int dz) {
        if (isUnformedSuperchestController(world, pos)) {
            return true;
        } else if (isUnformedSuperchestPart(world, pos)) {
            // We can already stop here
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockPos getBottomLowerLeft(World world, BlockPos pos) {
        if (isBlockPart(world, pos)) {
            IBlockState state = world.getBlockState(pos);
            SuperchestPartIndex index = state.getValue(BlockSuperchest.FORMED);
            return pos.add(-index.getDx(), -index.getDy(), -index.getDz());
        } else {
            return null;
        }
    }

    @Override
    public void unformBlock(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.withProperty(BlockSuperchest.FORMED, SuperchestPartIndex.UNFORMED), 3);
    }

    @Override
    public void formBlock(World world, BlockPos pos, int dx, int dy, int dz) {
        IBlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.withProperty(BlockSuperchest.FORMED, SuperchestPartIndex.getIndex(dx, dy, dz)), 3);
    }

    @Override
    public boolean isValidUnformedMultiBlock(World world, BlockPos pos) {
        int cntSuper = 0;
        for (int dx = 0 ; dx < getWidth() ; dx++) {
            for (int dy = 0 ; dy < getHeight() ; dy++) {
                for (int dz = 0 ; dz < getDepth() ; dz++) {
                    BlockPos p = pos.add(dx, dy, dz);
                    if (!isValidUnformedBlockPart(world, p, dx, dy, dz)) {
                        return false;
                    }
                    if (world.getBlockState(p).getBlock() == ModBlocks.blockSuperchest) {
                        cntSuper++;
                    }
                }
            }
        }
        return cntSuper == 1;
    }

    @Override
    public boolean isValidFormedMultiBlock(World world, BlockPos pos) {
        int cntSuper = 0;
        for (int dx = 0; dx < getWidth(); dx++) {
            for (int dy = 0; dy < getHeight(); dy++) {
                for (int dz = 0; dz < getDepth(); dz++) {
                    BlockPos p = pos.add(dx, dy, dz);
                    if (!isValidFormedBlockPart(world, p, dx, dy, dz)) {
                        return false;
                    }
                    if (world.getBlockState(p).getBlock() == ModBlocks.blockSuperchest) {
                        cntSuper++;
                    }
                }
            }
        }
        return cntSuper == 1;
    }

    @Override
    public int getWidth() {
        return 2;
    }

    @Override
    public int getHeight() {
        return 2;
    }

    @Override
    public int getDepth() {
        return 2;
    }


    private static boolean isUnformedSuperchestController(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == ModBlocks.blockSuperchest && state.getValue(BlockSuperchest.FORMED) == SuperchestPartIndex.UNFORMED;
    }

    public static boolean isFormedSuperchestController(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == ModBlocks.blockSuperchest && state.getValue(BlockSuperchest.FORMED) != SuperchestPartIndex.UNFORMED;
    }

    private static boolean isUnformedSuperchestPart(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == ModBlocks.blockSuperchestPart && state.getValue(BlockSuperchest.FORMED) == SuperchestPartIndex.UNFORMED;
    }

    private static boolean isFormedSuperchestPart(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == ModBlocks.blockSuperchestPart && state.getValue(BlockSuperchest.FORMED) != SuperchestPartIndex.UNFORMED;
    }

}
