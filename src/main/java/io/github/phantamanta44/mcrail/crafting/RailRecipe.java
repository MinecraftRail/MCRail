package io.github.phantamanta44.mcrail.crafting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class RailRecipe {

    final String[] lines;
    final Map<Character, Predicate<ItemStack>> ingredients;
    final ItemStack result;
    int lineIndex;

    public RailRecipe(ItemStack result) {
        this.lines = new String[3];
        this.ingredients = new HashMap<>();
        this.ingredients.put(' ', s -> s.getType().equals(Material.AIR));
        this.result = result;
        this.lineIndex = 0;
    }

    public RailRecipe line(String line) {
        lines[lineIndex] = line;
        lineIndex++;
        return this;
    }

    public RailRecipe ingredient(char c, Predicate<ItemStack> ing) {
        ingredients.put(c, ing);
        return this;
    }

    public RailRecipe ingredient(char c, Material ing) {
        return ingredient(c, s -> s.getType().equals(ing));
    }

    public RailRecipe ingredient(char c, MaterialData ing) {
        return ingredient(c, s -> s.getData().equals(ing));
    }

    public RailRecipe ingredient(char c, ItemStack ing) {
        return ingredient(c, s -> s.isSimilar(ing));
    }

    public RailRecipe ingredient(char c, String ing) {
        return ingredient(c, s -> {
            ItemMeta meta = s.getItemMeta();
            if (!meta.hasLore())
                return false;
            List<String> lore = meta.getLore();
            return lore.get(lore.size() - 1).startsWith(ChatColor.GRAY + "ID: ");
        });
    }

    public void register() {
        Bukkit.getServer().addRecipe(new ShapedRecipeDelegate(this));
    }

}
