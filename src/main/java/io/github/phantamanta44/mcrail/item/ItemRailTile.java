package io.github.phantamanta44.mcrail.item;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.characteristic.CharLastLore;
import io.github.phantamanta44.mcrail.item.characteristic.CharMeta;
import io.github.phantamanta44.mcrail.item.characteristic.CharName;
import io.github.phantamanta44.mcrail.item.characteristic.IItemCharacteristic;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;

public class ItemRailTile implements IItemBehaviour {

    private final String id;
    private final String name;
    private final Material material;
    private final Collection<IItemCharacteristic> characteristics;

    public ItemRailTile(String id, String name, Material material, Collection<IItemCharacteristic> characteristics) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.characteristics = characteristics;
    }

    public ItemRailTile(String id, String name, Material material, byte meta) {
        this(id, name, material,
                Arrays.asList(new CharLastLore(ChatColor.GRAY + "Rail Tile: " + id), new CharName(name), new CharMeta(meta)));
    }

    public ItemRailTile(String id, String name, Material material) {
        this(id, name, material,
                Arrays.asList(new CharLastLore(ChatColor.GRAY + "Rail Tile: " + id), new CharName(name)));
    }

    @Override
    public Material material() {
        return material;
    }

    @Override
    public Collection<IItemCharacteristic> characteristics() {
        return characteristics;
    }

    @Override
    public boolean onPlace(BlockPlaceEvent event, ItemStack stack) {
        Rail.tileManager().register(id, name, event.getBlock());
        return true;
    }

    public String getTileId() {
        return id;
    }

}
