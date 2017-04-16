package io.github.phantamanta44.mcrail.fluid;

import org.bukkit.Material;

public class FluidType {

    private final String name;
    private final Material bucketType, icon, blockType;

    public FluidType(String name, Material icon, Material bucket, Material block) {
        this.name = name;
        this.icon = icon;
        this.bucketType = bucket;
        this.blockType = block;
    }

    public FluidType(String name, Material icon, Material bucket) {
        this(name, icon, bucket, null);
    }

    public FluidType(String name, Material icon) {
        this(name, icon, null);
    }

    public String name() {
        return name;
    }

    public Material icon() {
        return icon;
    }

    public Material blockType() {
        return blockType;
    }

    public boolean hasBlock() {
        return blockType != null;
    }

    public Material bucketType() {
        return bucketType;
    }

    public boolean hasBucket() {
        return bucketType != null;
    }

}
