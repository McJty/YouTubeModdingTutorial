package mcjty.mymod.fload;


public class BlockFload {} /*extends BlockFlowingFluid {

    public static final ResourceLocation FLOAD = new ResourceLocation(MyMod.MODID, "fload");

    public BlockFload() {
        super(ModLiquids.fload, Properties.create(Material.WATER));
        setCreativeTab(MyMod.creativeTab);
        setTranslationKey(MyMod.MODID + ".fload");
        setRegistryName(FLOAD);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelResourceLocation fluidLocation = new ModelResourceLocation(FLOAD, "fluid");

        StateMapperBase customState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return fluidLocation;
            }
        };
        ModelLoader.setCustomStateMapper(this, customState);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(FLOAD, "inventory"));
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 60, 1));
        }
    }
}
*/