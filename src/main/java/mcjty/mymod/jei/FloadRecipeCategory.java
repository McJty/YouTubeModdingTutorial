package mcjty.mymod.jei;

public class FloadRecipeCategory {} /* implements IRecipeCategory<FloadRecipeWrapper> {

    private final IDrawable background;

    public FloadRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(MyMod.MODID, "textures/gui/floader.png");
        background = guiHelper.createDrawable(location, 3, 18, 90, 30);
    }

    @Nonnull
    @Override
    public String getUid() {
        return JeiPlugin.FLOADER_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Floader";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FloadRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiFluidStacks.init(0, false, 65, 6, 18, 18, 100, true, null);
        guiItemStacks.init(0, true, 6, 6);

        List<ItemStack> inputs = ingredients.getInputs(VanillaTypes.ITEM).get(0);
        List<FluidStack> outputs = ingredients.getOutputs(VanillaTypes.FLUID).get(0);

        guiItemStacks.set(0, inputs);
        guiFluidStacks.set(0, outputs);
    }

    @Override
    public String getModName() {
        return MyMod.MODNAME;
    }
}
*/