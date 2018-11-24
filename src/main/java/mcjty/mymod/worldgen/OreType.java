package mcjty.mymod.worldgen;

import net.minecraft.util.IStringSerializable;

public enum OreType implements IStringSerializable {
    ORE_OVERWORLD("overworld"),
    ORE_NETHER("nether"),
    ORE_END("end");

    private final String name;

    OreType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
