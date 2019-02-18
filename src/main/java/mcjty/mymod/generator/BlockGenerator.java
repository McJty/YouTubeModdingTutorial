package mcjty.mymod.generator;

import mcjty.mymod.MyMod;
import mcjty.mymod.tools.GenericBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.client.model.animation.TileEntityRendererAnimation;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGenerator extends GenericBlock implements ITileEntityProvider {

    public static final DirectionProperty FACING_HORIZ = DirectionProperty.create("facing", EnumFacing.Plane.HORIZONTAL);


    public static final ResourceLocation GENERATOR = new ResourceLocation(MyMod.MODID, "generator");

    public BlockGenerator() {
        super(Properties.create(Material.IRON));
        setRegistryName(GENERATOR);
        // @todo 1.13
//        setHarvestLevel("pickaxe", 1);

        setDefaultState(getStateContainer().getBaseState().with(FACING_HORIZ, EnumFacing.NORTH));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader blockReader, List<ITextComponent> tooltip, ITooltipFlag flags) {
        NBTTagCompound tagCompound = stack.getTag();
        if (tagCompound != null) {
            int energy = tagCompound.getInt("energy");
            addInformationLocalized(tooltip, "message.mymod.generator", energy);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileGenerator();
    }

    @Nullable
    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING_HORIZ, context.getPlayer().getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }

    // @todo 1.13
    //    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileGenerator.class, new TileEntityRendererAnimation<>());
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING_HORIZ);
        // @todo 1.13
//    protected BlockStateContainer createBlockState() {
//        return new ExtendedBlockState(this,
//                new IProperty[] {Properties.StaticProperty, FACING_HORIZ },
//                new IUnlistedProperty[] {Properties.AnimationProperty});
    }
}
