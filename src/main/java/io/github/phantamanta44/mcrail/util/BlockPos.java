package io.github.phantamanta44.mcrail.util;

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

    public Block block() {
        World world = world();
        return world != null ? world.getBlockAt(x, y, z) : null;
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
