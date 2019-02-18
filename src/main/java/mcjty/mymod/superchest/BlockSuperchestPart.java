package mcjty.mymod.superchest;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import mcjty.mymod.tools.MultiBlockTools;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static mcjty.mymod.superchest.BlockSuperchest.FORMED;

public class BlockSuperchestPart extends Block implements ITileEntityProvider {

    public static final ResourceLocation SUPERCHEST_PART = new ResourceLocation(MyMod.MODID, "superchest_part");

    public BlockSuperchestPart() {
        super(Properties.create(Material.IRON).hardnessAndResistance(2.0f));
        setRegistryName(SUPERCHEST_PART);
        // @todo 1.13
//        setHarvestLevel("axe", 1);

        setDefaultState(getStateContainer().getBaseState().with(FORMED, SuperchestPartIndex.UNFORMED));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileSuperchestPart();
    }


    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).getItem() == Items.STICK) {
            BlockSuperchest.toggleMultiBlock(world, pos, state, player);
            return true;
        }
        // Only work if the block is formed
        if (state.getBlock() == ModBlocks.blockSuperchestPart && state.get(FORMED) != SuperchestPartIndex.UNFORMED) {
            // Find the controller
            BlockPos controllerPos = BlockSuperchest.getControllerPos(world, pos);
            if (controllerPos != null) {
                IBlockState controllerState = world.getBlockState(controllerPos);
                return controllerState.getBlock().onBlockActivated(controllerState, world, controllerPos, player, hand, facing, hitX, hitY, hitZ);
            }
        }
        return false;
    }


    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!world.isRemote) {
            MultiBlockTools.breakMultiblock(SuperchestMultiBlock.INSTANCE, world, pos);
        }
        super.onBlockHarvested(world, pos, state, player);
    }


    @Override
    public boolean isFullCube(IBlockState state) {
        if (state.get(FORMED) == SuperchestPartIndex.UNFORMED) {
            return super.isFullCube(state);
        } else {
            return false;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FORMED);
    }
}
