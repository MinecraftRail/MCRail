package io.github.phantamanta44.mcrail.util;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockPos {

    private final String world;
    private final int x, y, z;

    public BlockPos(String world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPos(World world, int x, int y, int z) {
        this(world.getName(), x, y, z);
    }

    public BlockPos(Block block) {
        this(block.getWorld(), block.getX(), block.getY(), block.getZ());
    }

    public boolean exists() {
        return world() != null;
    }

    public World world() {
        return Bukkit.getServer().getWorld(world);
    }

    public String worldName() {
        return world;
    }

    public Block block() {
        World world = world();
        return world != null ? world.getBlockAt(x, y, z) : null;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public BlockPos add(int dX, int dY, int dZ) {
        return new BlockPos(world, x + dX, y + dY, z + dZ);
    }

    public JsonObject serialize() {
        JsonObject dto = new JsonObject();
        dto.addProperty("world", world);
        dto.addProperty("x", x);
        dto.addProperty("y", y);
        dto.addProperty("z", z);
        return dto;
    }

    public static BlockPos deserialize(JsonObject dto) {
        return new BlockPos(
                dto.get("world").getAsString(),
                dto.get("x").getAsInt(),
                dto.get("y").getAsInt(),
                dto.get("z").getAsInt()
        );
    }

    @Override
    public int hashCode() {
        return world.hashCode() ^ x ^ (y << 10) ^ (z << 20);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlockPos && doesEqual((BlockPos)o);
    }

    private boolean doesEqual(BlockPos o) {
        return x == o.x && y == o.y && z == o.z && world.equals(o.world);
    }

}
