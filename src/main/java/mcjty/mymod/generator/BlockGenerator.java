package mcjty.mymod.generator;

import mcjty.mymod.MyMod;
import mcjty.mymod.tools.GenericBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGenerator extends GenericBlock implements ITileEntityProvider {

    public static final PropertyDirection FACING_HORIZ = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);


    public static final ResourceLocation GENERATOR = new ResourceLocation(MyMod.MODID, "generator");

    public BlockGenerator() {
        super(Material.IRON);
        setRegistryName(GENERATOR);
        setTranslationKey(MyMod.MODID + ".generator");
        setHarvestLevel("pickaxe", 1);

        setDefaultState(blockState.getBaseState().withProperty(FACING_HORIZ, EnumFacing.NORTH));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flags) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null) {
            int energy = tagCompound.getInteger("energy");
            addInformationLocalized(tooltip, "message.mymod.generator", energy);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileGenerator();
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING_HORIZ, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return false; }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING_HORIZ);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING_HORIZ, EnumFacing.byIndex((meta & 3) + 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING_HORIZ).getIndex() - 2;
    }
}
