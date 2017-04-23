package io.github.phantamanta44.mcrail.item.characteristic;

import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class CharLastLore implements IItemCharacteristic {

    private final String line, pattern;

    public CharLastLore(String line, String pattern) {
        this.line = line;
        this.pattern = pattern;
    }

    public CharLastLore(String line) {
        this(line, Pattern.quote(line));
    }

    @Override
    public void apply(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = meta.getLore();
        lore = lore != null ? new LinkedList<>(lore) : new ArrayList<>(1);
        lore.add(line);
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    @Override
    public boolean matches(ItemStack stack) {
        String line = ItemUtils.lastLoreLine(stack);
        return line != null && line.matches(pattern);
    }

}
