package mcjty.mymod.furnace;

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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class BlockFastFurnace extends GenericBlock implements ITileEntityProvider {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", EnumFacing.values());
    public static final EnumProperty<FurnaceState> STATE = EnumProperty.<FurnaceState>create("state", FurnaceState.class);


    public static final ResourceLocation FAST_FURNACE = new ResourceLocation(MyMod.MODID, "fast_furnace");

    public BlockFastFurnace() {
        super(Properties
                .create(Material.IRON)
        );
        // mymod:furnace
        setRegistryName(FAST_FURNACE);
        // @todo 1.13
//        setHarvestLevel("pickaxe", 1);

        setDefaultState(stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader blockReader, List<ITextComponent> tooltip, ITooltipFlag flags) {
        NBTTagCompound tagCompound = stack.getTag();
        if (tagCompound != null) {
            int energy = tagCompound.getInt("energy");
            int sizeIn = getItemCount(tagCompound, "itemsIn");
            int sizeOut = getItemCount(tagCompound, "itemsOut");
            addInformationLocalized(tooltip, "message.mymod.fast_furnace", energy, sizeIn, sizeOut);
        }
    }


    private int getItemCount(NBTTagCompound tagCompound, String itemsIn2) {
        int sizeIn = 0;
        NBTTagCompound compoundIn = (NBTTagCompound) tagCompound.getTag(itemsIn2);
        NBTTagList itemsIn = compoundIn.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsIn.size(); i++) {
            NBTTagCompound itemTags = itemsIn.getCompound(i);

            if (!ItemStack.read(itemTags).isEmpty()) {
                sizeIn++;
            }
        }
        return sizeIn;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileFastFurnace();
    }

    @Nullable
    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        // @todo 1.13
//        return this.getDefaultState().with(FACING, EnumFacing.getFacingFromVector(pos, placer));
        BlockPos pos = context.getPos();
        return this.getDefaultState().with(FACING, EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ()));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING).add(STATE);
    }
}
