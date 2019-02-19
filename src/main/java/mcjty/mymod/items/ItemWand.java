package mcjty.mymod.items;

import mcjty.mymod.MyMod;
import mcjty.mymod.laser.EntitySphere;
import mcjty.mymod.mana.WorldMana;
import mcjty.mymod.playermana.PlayerProperties;
import mcjty.mymod.tools.RayTraceTools;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemWand extends Item {

    public ItemWand() {
        super(new Properties().group(MyMod.creativeTab));
        setRegistryName(new ResourceLocation(MyMod.MODID, "wand"));
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, player, list, flag);
        WandMode mode = getMode(stack);
        list.add(new TextComponentString("Mode: " + mode.name()));
    }

    private WandMode getMode(ItemStack stack) {
        if (!stack.hasTag()) {
            return WandMode.SPHERE;
        }
        return WandMode.values()[stack.getTag().getInt("mode")];
    }

    public void toggleMode(EntityPlayer player, ItemStack stack) {
        WandMode mode = getMode(stack);
        if (mode == WandMode.SPHERE) {
            mode = WandMode.LEVITATE;
        } else {
            mode = WandMode.SPHERE;
        }
        player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Switched to " + mode.name()), false);
        if (!stack.hasTag()) {
            stack.setTag(new NBTTagCompound());
        }
        stack.getTag().setInt("mode", mode.ordinal());
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            if (!world.isRemote) {
                chargeMana(world, player);
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
        } else {
            if (!world.isRemote) {
                switch (getMode(player.getHeldItem(hand))) {
                    case SPHERE:
                        fireSphere(world, player);
                        break;
                    case LEVITATE:
                        levitateEntity(world, player);
                        break;
                }
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    private void levitateEntity(World world, EntityPlayer player) {
        player.getCapability(PlayerProperties.PLAYER_MANA).ifPresent(playerMana -> {
            if (playerMana.getMana() >= .1f) {
                world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                playerMana.setMana(playerMana.getMana() - .1f);
                RayTraceTools.Beam beam = new RayTraceTools.Beam(world, player, 20);
                RayTraceTools.rayTrace(beam, entity -> {
                    if (entity instanceof EntityLivingBase) {
                        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 60, 1));
                        return true;
                    } else {
                        return false;
                    }
                });
            }
        });
    }

    private void fireSphere(World world, EntityPlayer player) {
        player.getCapability(PlayerProperties.PLAYER_MANA).ifPresent(playerMana -> {
            if (playerMana.getMana() >= .5f) {
                world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                playerMana.setMana(playerMana.getMana() - .5f);
                RayTraceTools.Beam beam = new RayTraceTools.Beam(world, player, 20);
                spawnSphere(player, beam);
            }
        });
    }

    private void spawnSphere(EntityPlayer player, RayTraceTools.Beam beam) {
        Vec3d start = beam.getStart();
        Vec3d lookVec = beam.getLookVec();
        double accelX = lookVec.x * 1.0D;
        double accelY = lookVec.y * 1.0D;
        double accelZ = lookVec.z * 1.0D;

        EntitySphere laser = new EntitySphere(player.getEntityWorld(), player, accelX, accelY, accelZ);
        laser.posX = start.x;
        laser.posY = start.y;
        laser.posZ = start.z;

        player.getEntityWorld().spawnEntity(laser);
    }

    private void chargeMana(World world, EntityPlayer player) {
        WorldMana worldMana = WorldMana.get(world);
        float amount = worldMana.extractMana(world, player.getPosition());
        player.getCapability(PlayerProperties.PLAYER_MANA).ifPresent(playerMana -> {
            playerMana.setMana(playerMana.getMana() + amount);
        });
    }
}
