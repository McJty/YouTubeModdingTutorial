package mcjty.mymod.tools;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiBlockTools {


    // Return true on success;
    public static boolean breakMultiblock(IMultiBlockType type, World world, BlockPos pos) {
        // First find the bottom left position of our multiblock
        BlockPos bottomLeft = type.getBottomLowerLeft(world, pos);
        if (bottomLeft != null) {
            if (!type.isValidFormedMultiBlock(world, bottomLeft)) {
                // Not a valid multiblock!
                return false;
            }

            for (int dx = 0 ; dx < type.getWidth() ; dx++) {
                for (int dy = 0 ; dy < type.getHeight() ; dy++) {
                    for (int dz = 0 ; dz < type.getDepth() ; dz++) {
                        BlockPos p = bottomLeft.add(dx, dy, dz);
                        type.unformBlock(world, p);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean formMultiblock(IMultiBlockType type, World world, BlockPos pos) {
        for (int dx = -type.getWidth()+1 ; dx <= 0 ; dx++) {
            for (int dy = -type.getHeight()+1 ; dy <= 0 ; dy++) {
                for (int dz = -type.getDepth()+1 ; dz <= 0 ; dz++) {
                    BlockPos p = pos.add(dx, dy, dz);
                    if (type.isValidUnformedMultiBlock(world, p)) {
                        createMultiblock(type, world, p);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // pos is lower bottom coordinate of an unformed multiblock
    private static void createMultiblock(IMultiBlockType type, World world, BlockPos pos) {
        for (int dx = 0 ; dx < type.getWidth() ; dx++) {
            for (int dy = 0 ; dy < type.getHeight() ; dy++) {
                for (int dz = 0 ; dz < type.getDepth() ; dz++) {
                    type.formBlock(world, pos.add(dx, dy, dz), dx, dy, dz);
                }
            }
        }
    }

}
