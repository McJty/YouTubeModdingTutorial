package mcjty.mymod.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {

    public static KeyBinding wandMode;

    public static void init() {
        wandMode = new KeyBinding("key.wandmode", KeyConflictContext.IN_GAME, Keyboard.KEY_M, "key.categories.mymod");
        ClientRegistry.registerKeyBinding(wandMode);
    }
}
