package mcjty.mymod.playermana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerProperties {

    @CapabilityInject(PlayerMana.class)
    public static Capability<PlayerMana> PLAYER_MANA;

    public static PlayerMana getPlayerMana(EntityPlayer player) {
        return player.getCapability(PLAYER_MANA, null);
    }

}
