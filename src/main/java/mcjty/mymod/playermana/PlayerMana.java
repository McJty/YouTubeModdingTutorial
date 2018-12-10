package mcjty.mymod.playermana;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerMana {


    private float mana = 0.0f;

    public PlayerMana() {
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public void copyFrom(PlayerMana source) {
        mana = source.mana;
    }


    public void saveNBTData(NBTTagCompound compound) {
        compound.setFloat("mana", mana);
    }

    public void loadNBTData(NBTTagCompound compound) {
        mana = compound.getFloat("mana");
    }

}
