package io.github.phantamanta44.mcrail.item.characteristic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CharLore implements IItemCharacteristic {

    private final int index;
    private final String line, pattern;

    public CharLore(int index, String line, String pattern) {
        this.index = index;
        this.line = line;
        this.pattern = pattern;
    }

    public CharLore(int index, String line) {
        this(index, line, Pattern.quote(line));
    }

    @Override
    public void apply(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = meta.getLore();
        lore = lore != null ? new ArrayList<>(lore) : new ArrayList<>();
        while (index >= lore.size())
            lore.add("");
        lore.set(index, line);
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (!stack.hasItemMeta())
            return false;
        List<String> lore = stack.getItemMeta().getLore();
        return lore != null && lore.size() > index && lore.get(index).matches(pattern);
    }

}
