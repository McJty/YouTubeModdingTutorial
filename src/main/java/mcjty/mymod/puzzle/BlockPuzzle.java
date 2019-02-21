package mcjty.mymod.puzzle;

import mcjty.mymod.MyMod;
import mcjty.mymod.tools.GenericBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nullable;

public class BlockPuzzle extends GenericBlock implements ITileEntityProvider {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", EnumFacing.values());
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public static final ResourceLocation PUZZLE = new ResourceLocation(MyMod.MODID, "puzzle");

    public BlockPuzzle() {
        super(Properties.create(Material.WOOD));
        setRegistryName(PUZZLE);
        // @todo 1.13
//        setHarvestLevel("axe", 1);

        setDefaultState(getStateContainer().getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Override
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TilePuzzle.class, new PuzzleTESR());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TilePuzzle();
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TilePuzzle && !world.isRemote) {
            ((TilePuzzle) tileEntity).activate(state);
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        int power = world.getRedstonePowerFromNeighbors(pos);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TilePuzzle) {
            ((TilePuzzle) tileEntity).setPower(power);
        }
    }

    @Nullable
    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        // @todo 1.13
        return this.getDefaultState()
                .with(FACING, EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ()))
                .with(OPEN, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING).add(OPEN);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID;
    }
}
