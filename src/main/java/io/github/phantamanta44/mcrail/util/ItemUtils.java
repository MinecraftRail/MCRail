package io.github.phantamanta44.mcrail.util;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.IItemBehaviour;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemUtils {

    public static boolean isNully(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() < 1;
    }

    public static boolean isNotNully(ItemStack stack) {
        return stack != null && stack.getType() != Material.AIR && stack.getAmount() > 0;
    }

    public static boolean isFull(ItemStack stack) {
        return stack.getAmount() >= stack.getMaxStackSize();
    }

    public static boolean instOf(String id, ItemStack stack) {
        IItemBehaviour item = Rail.itemRegistry().get(id);
        return item != null
                && stack.getType() == item.material()
                && item.characteristics().stream().allMatch(c -> c.matches(stack));
    }

    public static boolean isRailItem(ItemStack stack) {
        return Rail.itemRegistry().get(stack) != null;
    }

    public static String lastLoreLine(ItemStack stack) {
        List<String> lore = stack.getItemMeta().getLore();
        return lore != null ? lore.get(lore.size() - 1) : null;
    }

}
