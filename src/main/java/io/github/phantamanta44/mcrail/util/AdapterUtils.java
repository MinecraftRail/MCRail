package io.github.phantamanta44.mcrail.util;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class AdapterUtils {

    public static <T> T adapt(Class<T> type, ItemStack stack) {
        return Rail.itemAdapters().adapt(type, stack);
    }

    public static <T> T adapt(Class<T> type, Block block) {
        return Rail.blockAdapters().adapt(type, block);
    }

    public static boolean instOf(Class<?> type, ItemStack stack) {
        return adapt(type, stack) != null;
    }

    public static boolean instOf(Class<?> type, Block block) {
        return adapt(type, block) != null;
    }

}
