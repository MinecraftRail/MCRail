package io.github.phantamanta44.mcrail.item;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.characteristic.CharLastLore;
import io.github.phantamanta44.mcrail.item.characteristic.IItemCharacteristic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;

public class SignEntityItem implements IItemBehaviour {

    private final String id;
    private final Collection<IItemCharacteristic> characteristics;

    public SignEntityItem(String id) {
        this.id = id;
        this.characteristics = Collections.singleton(new CharLastLore(ChatColor.GRAY + "ID: " + id));
    }

    @Override
    public Material material() {
        return Material.SIGN;
    }

    @Override
    public Collection<IItemCharacteristic> characteristics() {
        return characteristics;
    }

    @Override
    public void onPlace(BlockPlaceEvent event, ItemStack stack) {
        Rail.signManager().register(id, event.getBlock());
        Bukkit.getServer().getScheduler().runTaskLater(
                Rail.INSTANCE, () -> event.getPlayer().closeInventory(), 1L);
    }

    public String getSignId() {
        return id;
    }

}
