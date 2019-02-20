package mcjty.mymod.jei;

class CustomRecipeWrapper {} /*implements ICraftingRecipeWrapper {

    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public CustomRecipeWrapper(CustomRecipe recipe) {
        this.inputs = Collections.singletonList(Collections.singletonList(recipe.getInput().copy()));
        this.output = recipe.getOutput().copy();
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, output);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }
}
*/