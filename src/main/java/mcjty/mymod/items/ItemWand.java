package mcjty.mymod.items;

import mcjty.mymod.MyMod;
import mcjty.mymod.mana.WorldMana;
import mcjty.mymod.playermana.PlayerMana;
import mcjty.mymod.playermana.PlayerProperties;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWand extends Item {

    public ItemWand() {
        setTranslationKey(MyMod.MODID + ".wand");
        setRegistryName(new ResourceLocation(MyMod.MODID, "wand"));
        setCreativeTab(MyMod.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            if (!world.isRemote) {
                // Charge the players mana
                WorldMana worldMana = WorldMana.get(world);
                float amount = worldMana.extractMana(world, player.getPosition());
                PlayerMana playerMana = PlayerProperties.getPlayerMana(player);
                playerMana.setMana(playerMana.getMana() + amount);
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
        } else {
            if (!world.isRemote) {
                PlayerMana playerMana = PlayerProperties.getPlayerMana(player);
                if (playerMana.getMana() >= 3) {
                    world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                    playerMana.setMana(playerMana.getMana()-3);
                }
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
        }
        return super.onItemRightClick(world, player, hand);
    }
}
