package mcjty.mymod.superchest;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import mcjty.mymod.tools.MultiBlockTools;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static mcjty.mymod.superchest.BlockSuperchest.FORMED;

public class BlockSuperchestPart extends Block implements ITileEntityProvider {

    public static final ResourceLocation SUPERCHEST_PART = new ResourceLocation(MyMod.MODID, "superchest_part");

    public BlockSuperchestPart() {
        super(Material.IRON);
        setRegistryName(SUPERCHEST_PART);
        setTranslationKey(MyMod.MODID + ".superchest_part");
        setHarvestLevel("axe", 1);
        setHardness(2.0f);

        setDefaultState(blockState.getBaseState().withProperty(FORMED, SuperchestPartIndex.UNFORMED));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSuperchestPart();
    }


    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).getItem() == Items.STICK) {
            BlockSuperchest.toggleMultiBlock(world, pos, state, player);
            return true;
        }
        // Only work if the block is formed
        if (state.getBlock() == ModBlocks.blockSuperchestPart && state.getValue(FORMED) != SuperchestPartIndex.UNFORMED) {
            // Find the controller
            BlockPos controllerPos = BlockSuperchest.getControllerPos(world, pos);
            if (controllerPos != null) {
                IBlockState controllerState = world.getBlockState(controllerPos);
                return controllerState.getBlock().onBlockActivated(world, controllerPos, controllerState, player, hand, facing, hitX, hitY, hitZ);
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
        if (state.getValue(FORMED) == SuperchestPartIndex.UNFORMED) {
            return super.isFullCube(state);
        } else {
            return false;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FORMED);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FORMED, SuperchestPartIndex.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FORMED).ordinal());
    }
}
