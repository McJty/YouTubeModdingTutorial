package mcjty.mymod.customrecipes;

import net.minecraft.item.ItemStack;

public class CustomRecipe {

    private final ItemStack input;
    private final ItemStack output;

    public CustomRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
