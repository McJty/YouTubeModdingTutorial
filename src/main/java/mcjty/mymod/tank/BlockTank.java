package mcjty.mymod.tank;

import mcjty.mymod.MyMod;
import mcjty.mymod.tools.GenericBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class BlockTank extends GenericBlock implements ITileEntityProvider {

    public static final ResourceLocation TANK = new ResourceLocation(MyMod.MODID, "tank");

    public BlockTank() {
        super(Properties.create(Material.GLASS)
            .hardnessAndResistance(1.0f)
            .sound(SoundType.GLASS));
        setRegistryName(TANK);
        // @todo 1.13
//        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        NBTTagCompound tagCompound = stack.getTag();
        if (tagCompound != null) {
            NBTTagCompound nbt = tagCompound.getCompound("tank");
            FluidStack fluidStack = null;
            if (!nbt.hasKey("Empty")) {
                fluidStack = FluidStack.loadFluidStackFromNBT(nbt);
            }
            if (fluidStack == null) {
                addInformationLocalized(tooltip, "message.mymod.tank", "empty");
            } else {
                String name = fluidStack.getLocalizedName();
                addInformationLocalized(tooltip, "message.mymod.tank", name + " (" + fluidStack.amount + ")");
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileTank();
    }

    @Override
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new TankTESR());
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            FluidUtil.interactWithFluidHandler(player, hand, world, pos, side);
        }
        return true;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState p_149721_1_) {
        return false;
    }


    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
