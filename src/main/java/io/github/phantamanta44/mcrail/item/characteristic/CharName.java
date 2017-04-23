package io.github.phantamanta44.mcrail.item.characteristic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.regex.Pattern;

public class CharName implements IItemCharacteristic {

    private final String name, pattern;

    public CharName(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public CharName(String name) {
        this(name, Pattern.quote(name));
    }

    @Override
    public void apply(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
    }

    @Override
    public boolean matches(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().matches(pattern);
    }

}
