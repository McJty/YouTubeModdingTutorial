package mcjty.mymod.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyBinding wandMode;

    public static void init() {
        wandMode = new KeyBinding("key.wandmode", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM.getOrMakeInput(GLFW.GLFW_KEY_M), "key.categories.mymod");
        ClientRegistry.registerKeyBinding(wandMode);
    }
}
