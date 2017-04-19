package io.github.phantamanta44.mcrail.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static boolean isNully(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() < 1;
    }

    public static boolean isNotNully(ItemStack stack) {
        return stack != null && stack.getType() != Material.AIR && stack.getAmount() > 0;
    }

}
