package mcjty.mymod.mana;

import mcjty.mymod.network.Messages;
import mcjty.mymod.playermana.PlayerMana;
import mcjty.mymod.playermana.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.HashMap;
import java.util.Map;

public class WorldMana extends WorldSavedData {

    private static final String NAME = "MyModManaData";

    private Map<ChunkPos, ManaSphere> spheres = new HashMap<>();
    private int ticker = 10;

    public WorldMana() {
        super(NAME);
    }

    public WorldMana(String name) {
        super(name);
    }

    public static WorldMana get(World world) {
        // @todo 1.13
//        WorldSavedDataStorage mapStorage = world.getMapStorage();
//        MapStorage storage = world.getPerWorldStorage();
//        WorldMana instance = (WorldMana) storage.getOrLoadData(WorldMana.class, NAME);
//
//        if (instance == null) {
//            instance = new WorldMana();
//            storage.setData(NAME, instance);
//        }
//        return instance;
        return null;
    }

    public float getManaInfluence(World world, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        float mana = 0.0f;
        for (int dx = -4 ; dx <= 4 ; dx++) {
            for (int dz = -4 ; dz <= 4 ; dz++) {
                ChunkPos cp = new ChunkPos(chunkPos.x + dx, chunkPos.z + dz);
                ManaSphere sphere = getOrCreateSphereAt(world, cp);
                if (sphere.getRadius() > 0) {
                    double distanceSq = pos.distanceSq(sphere.getCenter());
                    if (distanceSq < sphere.getRadius() * sphere.getRadius()) {
                        double distance = Math.sqrt(distanceSq);
                        mana += (sphere.getRadius() - distance) / sphere.getRadius();
                    }
                }
            }
        }
        return mana;
    }

    public float getManaStrength(World world, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        float mana = 0.0f;
        for (int dx = -4 ; dx <= 4 ; dx++) {
            for (int dz = -4 ; dz <= 4 ; dz++) {
                ChunkPos cp = new ChunkPos(chunkPos.x + dx, chunkPos.z + dz);
                ManaSphere sphere = getOrCreateSphereAt(world, cp);
                if (sphere.getRadius() > 0) {
                    double distanceSq = pos.distanceSq(sphere.getCenter());
                    if (distanceSq < sphere.getRadius() * sphere.getRadius()) {
                        double distance = Math.sqrt(distanceSq);
                        double influence = (sphere.getRadius() - distance) / sphere.getRadius();
                        mana += influence * sphere.getCurrentMana();
                    }
                }
            }
        }
        return mana;
    }

    public float extractMana(World world, BlockPos pos) {
        float manaInfluence = getManaInfluence(world, pos);
        if (manaInfluence <= 0) {
            return 0;
        }
        ChunkPos chunkPos = new ChunkPos(pos);
        float extracted = 0.0f;
        for (int dx = -4 ; dx <= 4 ; dx++) {
            for (int dz = -4 ; dz <= 4 ; dz++) {
                ChunkPos cp = new ChunkPos(chunkPos.x + dx, chunkPos.z + dz);
                ManaSphere sphere = getOrCreateSphereAt(world, cp);
                if (sphere.getRadius() > 0) {
                    double distanceSq = pos.distanceSq(sphere.getCenter());
                    if (distanceSq < sphere.getRadius() * sphere.getRadius()) {
                        double distance = Math.sqrt(distanceSq);
                        double influence = (sphere.getRadius() - distance) / sphere.getRadius();
                        float currentMana = sphere.getCurrentMana();
                        if (influence > currentMana) {
                            influence = currentMana;
                        }
                        currentMana -= influence;
                        extracted += influence;
                        sphere.setCurrentMana(currentMana);
                        markDirty();
                    }
                }
            }
        }
        return extracted;
    }

    public void tick(World world) {
        ticker--;
        if (ticker > 0) {
            return;
        }
        ticker = 10;
        growMana(world);
        sendMana(world);
    }

    private void growMana(World world) {
        for (Map.Entry<ChunkPos, ManaSphere> entry : spheres.entrySet()) {
            ManaSphere sphere = entry.getValue();
            if (sphere.getRadius() > 0) {
                if (world.isBlockLoaded(sphere.getCenter())) {
                    float currentMana = sphere.getCurrentMana();
                    currentMana += .01f;
                    if (currentMana >= 5) {
                        currentMana = 5;
                    }
                    sphere.setCurrentMana(currentMana);
                    markDirty();
                }
            }
        }
    }

    private void sendMana(World world) {
        for (EntityPlayer player : world.playerEntities) {
            float manaStrength = getManaStrength(world, player.getPosition());
            float maxInfluence = getManaInfluence(world, player.getPosition());
            player.getCapability(PlayerProperties.PLAYER_MANA).ifPresent(playerMana -> Messages.INSTANCE.sendTo(new PacketSendMana(manaStrength, maxInfluence, playerMana.getMana()), ((EntityPlayerMP) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT));
        }
    }

    private ManaSphere getOrCreateSphereAt(World world, ChunkPos cp) {
        ManaSphere sphere = spheres.get(cp);
        if (sphere == null) {
            BlockPos center = cp.getBlock(8, ManaSphere.getRandomYOffset(world.getSeed(), cp.x, cp.z), 8);
            float radius = 0;
            if (ManaSphere.isCenterChunk(world.getSeed(), cp.x, cp.z)) {
                radius = ManaSphere.getRadius(world.getSeed(), cp.x, cp.z);
            }
            sphere = new ManaSphere(center, radius);
            spheres.put(cp, sphere);
            markDirty();
        }
        return sphere;
    }

    @Override
    public void read(NBTTagCompound nbt) {
        NBTTagList list = nbt.getList("spheres", Constants.NBT.TAG_COMPOUND);
        for (int i = 0 ; i < list.size() ; i++) {
            NBTTagCompound sphereNBT = list.getCompound(i);
            ChunkPos pos = new ChunkPos(sphereNBT.getInt("cx"), sphereNBT.getInt("cz"));
            ManaSphere sphere = new ManaSphere(
                    new BlockPos(sphereNBT.getInt("posx"), sphereNBT.getInt("posy"), sphereNBT.getInt("posz")),
                    sphereNBT.getFloat("radius"));
            sphere.setCurrentMana(sphereNBT.getFloat("mana"));
            spheres.put(pos, sphere);
        }
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (Map.Entry<ChunkPos, ManaSphere> entry : spheres.entrySet()) {
            NBTTagCompound sphereNBT = new NBTTagCompound();
            ChunkPos pos = entry.getKey();
            ManaSphere sphere = entry.getValue();
            sphereNBT.setInt("cx", pos.x);
            sphereNBT.setInt("cz", pos.z);
            sphereNBT.setInt("posx", sphere.getCenter().getX());
            sphereNBT.setInt("posy", sphere.getCenter().getY());
            sphereNBT.setInt("posz", sphere.getCenter().getZ());
            sphereNBT.setFloat("radius", sphere.getRadius());
            sphereNBT.setFloat("mana", sphere.getCurrentMana());
            list.add(sphereNBT);
        }
        compound.setTag("spheres", list);

        return compound;
    }
}
