package mcjty.mymod.floader;

import mcjty.mymod.MyMod;
import mcjty.mymod.tools.GenericBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockFloader extends GenericBlock implements ITileEntityProvider {

    public static final DirectionProperty FACING_HORIZ = DirectionProperty.create("facing", EnumFacing.Plane.HORIZONTAL);

    public static final ResourceLocation FLOADER = new ResourceLocation(MyMod.MODID, "floader");

    public BlockFloader() {
        super(Properties.create(Material.IRON));
        setRegistryName(FLOADER);
//        setHarvestLevel("pickaxe", 1);

        setDefaultState(getStateContainer().getBaseState().with(FACING_HORIZ, EnumFacing.NORTH));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileFloader();
    }

    @Nullable
    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING_HORIZ, context.getPlayer().getHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING_HORIZ);
    }
}
