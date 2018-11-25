package mcjty.mymod.furnace;

import mcjty.mymod.MyMod;
import mcjty.mymod.tools.GenericBlock;
import mcjty.mymod.tools.IRestorableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class BlockFastFurnace extends GenericBlock implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<FurnaceState> STATE = PropertyEnum.<FurnaceState>create("state", FurnaceState.class);


    public static final ResourceLocation FAST_FURNACE = new ResourceLocation(MyMod.MODID, "fast_furnace");

    public BlockFastFurnace() {
        super(Material.IRON);
        // mymod:furnace
        setRegistryName(FAST_FURNACE);
        setTranslationKey(MyMod.MODID + ".fast_furnace");
        setHarvestLevel("pickaxe", 1);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flags) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null) {
            int energy = tagCompound.getInteger("energy");
            int sizeIn = getItemCount(tagCompound, "itemsIn");
            int sizeOut = getItemCount(tagCompound, "itemsOut");
            addInformationLocalized(tooltip, "message.mymod.fast_furnace", energy, sizeIn, sizeOut);
        }
    }


    private int getItemCount(NBTTagCompound tagCompound, String itemsIn2) {
        int sizeIn = 0;
        NBTTagCompound compoundIn = (NBTTagCompound) tagCompound.getTag(itemsIn2);
        NBTTagList itemsIn = compoundIn.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsIn.tagCount(); i++) {
            NBTTagCompound itemTags = itemsIn.getCompoundTagAt(i);
            if (!new ItemStack(itemTags).isEmpty()) {
                sizeIn++;
            }
        }
        return sizeIn;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFastFurnace();
    }


    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileFastFurnace) {
            return state.withProperty(STATE, ((TileFastFurnace) te).getState());
        }
        return super.getActualState(state, world, pos);
    }



    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
}
