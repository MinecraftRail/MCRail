package io.github.phantamanta44.mcrail.item.characteristic;

import org.bukkit.inventory.ItemStack;

public class CharDamage implements IItemCharacteristic {

    private final short value;

    public CharDamage(short value) {
        this.value = value;
    }

    @Override
    public void apply(ItemStack stack) {
        stack.setDurability(value);
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.getDurability() == value;
    }

    public short value() {
        return value;
    }

}
