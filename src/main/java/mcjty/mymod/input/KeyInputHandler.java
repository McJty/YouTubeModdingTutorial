package mcjty.mymod.input;

import mcjty.mymod.network.Messages;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.wandMode.isPressed()) {
            Messages.INSTANCE.sendToServer(new PacketToggleMode());
        }
    }
}
