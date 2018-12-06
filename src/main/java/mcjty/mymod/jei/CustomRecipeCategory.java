package mcjty.mymod.jei;

import mcjty.mymod.MyMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class CustomRecipeCategory implements IRecipeCategory<CustomRecipeWrapper> {

    private final IDrawable background;

    public CustomRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(MyMod.MODID, "textures/gui/fast_furnace.png");
        background = guiHelper.createDrawable(location, 3, 18, 170, 30);
    }

    @Nonnull
    @Override
    public String getUid() {
        return JeiPlugin.FASTFURNACE_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Fast Furnace";
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
    public void setRecipe(IRecipeLayout recipeLayout, CustomRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 6, 6);
        guiItemStacks.init(3, false, 114, 6);

        List<ItemStack> inputs = ingredients.getInputs(VanillaTypes.ITEM).get(0);
        List<ItemStack> outputs = ingredients.getOutputs(VanillaTypes.ITEM).get(0);

        guiItemStacks.set(0, inputs);
        guiItemStacks.set(3, outputs);
    }

    @Override
    public String getModName() {
        return MyMod.MODNAME;
    }
}
