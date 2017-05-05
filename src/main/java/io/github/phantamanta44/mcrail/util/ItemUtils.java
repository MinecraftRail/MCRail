package io.github.phantamanta44.mcrail.util;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.IItemBehaviour;
import io.github.phantamanta44.mcrail.oredict.OreDictionary;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.function.Predicate;

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
                ? stack.getType() == item.material() && item.characteristics().stream().allMatch(c -> c.matches(stack))
                : OreDictionary.matches(id, stack);
    }

    public static boolean isRailItem(ItemStack stack) {
        return Rail.itemRegistry().get(stack) != null;
    }

    public static String lastLoreLine(ItemStack stack) {
        List<String> lore = stack.getItemMeta().getLore();
        return lore != null ? lore.get(lore.size() - 1) : null;
    }
    
    public static Predicate<ItemStack> matching(Material material) {
        return s -> isNotNully(s) && s.getType().equals(material) && !isRailItem(s);
    }
    
    public static Predicate<ItemStack> matching(MaterialData data) {
        return s -> isNotNully(s) && s.getData().equals(data) && !isRailItem(s);
    }
    
    public static Predicate<ItemStack> matching(ItemStack stack) {
        return s -> isNotNully(s) && stack.isSimilar(s);
    }
    
    public static Predicate<ItemStack> matching(String id) {
        return s -> isNotNully(s) && instOf(id, s);
    }

}
