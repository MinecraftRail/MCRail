package io.github.phantamanta44.mcrail.item;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.characteristic.CharLastLore;
import io.github.phantamanta44.mcrail.item.characteristic.CharName;
import io.github.phantamanta44.mcrail.item.characteristic.IItemCharacteristic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;

public class SignEntityItem implements IItemBehaviour {

    private final String id;
    private final String name;
    private final Collection<IItemCharacteristic> characteristics;

    public SignEntityItem(String id, String name) {
        this.id = id;
        this.name = name;
        this.characteristics = Arrays.asList(new CharLastLore(ChatColor.GRAY + "ID: " + id), new CharName(name));
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
        Rail.signManager().register(id, name, event.getBlock());
        Bukkit.getServer().getScheduler().runTaskLater(
                Rail.INSTANCE, () -> event.getPlayer().closeInventory(), 1L);
    }

    public String getSignId() {
        return id;
    }

}
