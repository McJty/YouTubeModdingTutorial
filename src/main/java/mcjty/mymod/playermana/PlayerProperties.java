package mcjty.mymod.playermana;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerProperties {

    @CapabilityInject(PlayerMana.class)
    public static Capability<PlayerMana> PLAYER_MANA;
}
