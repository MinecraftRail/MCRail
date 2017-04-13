package io.github.phantamanta44.mcrail.gui;

import io.github.phantamanta44.mcrail.RailMain;
import io.github.phantamanta44.mcrail.gui.slot.GuiSlot;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;

import java.util.stream.IntStream;

public abstract class Gui {

    protected static final ItemStack FILLER_STACK;

    static {
        FILLER_STACK = new ItemStack(Material.STAINED_GLASS_PANE);
        Colorable data = (Colorable)FILLER_STACK.getData();
        data.setColor(DyeColor.GRAY);
        FILLER_STACK.setData((MaterialData)data);
    }

    private final Inventory cont;
    private final GuiSlot[] slots;
    
    public Gui(InventoryType type, String title, Player player) {
        this.cont = Bukkit.getServer().createInventory(null, type, title);
        this.slots = new GuiSlot[this.cont.getSize()];
        RailMain.INSTANCE.guiHandler().register(this);
        Bukkit.getServer().getScheduler().runTaskLater(RailMain.INSTANCE, () -> player.openInventory(cont), 1L);
    }

    public Inventory inventory() {
        return cont;
    }

    public void slot(int index, GuiSlot slot) {
        slots[index] = slot;
    }

    public GuiSlot slot(int index) {
        return slots[index];
    }

    public void tick() {
        IntStream.range(0, slots.length)
                .forEach(i -> cont.setItem(i, slots[i] != null ? slots[i].stack() : FILLER_STACK));
    }

    public void onInteract(InventoryClickEvent event) {
        if (!slots[event.getSlot()].click((Player)event.getWhoClicked(), event.getCursor()))
            event.setCancelled(true);
    }
    
    public abstract void init();

    public void destroy() {
        // NO-OP
    }

}
