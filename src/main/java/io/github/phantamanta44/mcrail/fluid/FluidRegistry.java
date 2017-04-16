package io.github.phantamanta44.mcrail.fluid;

import io.github.phantamanta44.mcrail.util.BlockPos;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FluidRegistry {

    public static FluidType WATER = new FluidType("Water", Material.WATER, Material.WATER_BUCKET, Material.WATER);
    public static FluidType LAVA = new FluidType("Lava", Material.LAVA, Material.LAVA_BUCKET, Material.LAVA);
    public static FluidType MILK = new FluidType("Milk", Material.MILK_BUCKET, Material.MILK_BUCKET);

    private final Collection<FluidType> registry;
    private final Map<Material, FluidType> bucketMap, blockMap;

    public FluidRegistry() {
        this.registry = new LinkedList<>();
        this.bucketMap = new HashMap<>();
        this.blockMap = new HashMap<>();
        register(WATER);
        register(LAVA);
        register(MILK);
    }

    public void register(FluidType type) {
        registry.add(type);
        if (type.hasBucket())
            bucketMap.put(type.bucketType(), type);
        if (type.hasBlock())
            blockMap.put(type.blockType(), type);
    }

    public FluidType byBucket(Material material) {
        return bucketMap.get(material);
    }

    public FluidType byBucket(ItemStack stack) {
        return bucketMap.get(stack.getType());
    }

    public FluidType byBlock(Material material) {
        return blockMap.get(material);
    }

    public FluidType byBlock(Block block) {
        return byBlock(block.getType());
    }

    public FluidType byBlock(BlockPos pos) {
        return byBlock(pos.block());
    }

}
