package io.github.phantamanta44.mcrail.item.characteristic;

import org.bukkit.inventory.ItemStack;

public class CharMeta implements IItemCharacteristic {

    private final byte meta;

    public CharMeta(byte meta) {
        this.meta = meta;
    }

    @Override
    public void apply(ItemStack stack) {
        stack.getData().setData(meta);
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.getData().getData() == meta;
    }

}
