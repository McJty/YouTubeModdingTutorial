package mcjty.mymod.superchest;

import net.minecraft.util.IStringSerializable;

public enum SuperchestPartIndex implements IStringSerializable {
    UNFORMED("unformed", 0, 0, 0),
    P000("p000", 0, 0, 0),
    P001("p001", 0, 0, 1),
    P010("p010", 0, 1, 0),
    P011("p011", 0, 1, 1),
    P100("p100", 1, 0, 0),
    P101("p101", 1, 0, 1),
    P110("p110", 1, 1, 0),
    P111("p111", 1, 1, 1);

    // Optimization
    public static final SuperchestPartIndex[] VALUES = SuperchestPartIndex.values();

    private final String name;
    private final int dx;
    private final int dy;
    private final int dz;

    SuperchestPartIndex(String name, int dx, int dy, int dz) {
        this.name = name;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public static SuperchestPartIndex getIndex(int dx, int dy, int dz) {
        return VALUES[dx*4 + dy*2 + dz + 1];
    }

    @Override
    public String getName() {
        return name;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDz() {
        return dz;
    }
}
