package io.github.phantamanta44.mcrail.gui;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.gui.slot.GuiSlot;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.stream.IntStream;

public abstract class Gui {

    protected static final ItemStack FILLER_STACK;

    static {
        FILLER_STACK = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta meta = FILLER_STACK.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "...");
        FILLER_STACK.setItemMeta(meta);
    }

    private final Inventory cont;
    private final GuiSlot[] slots;
    private final Player player;
    
    public Gui(int rows, String title, Player player) {
        this.cont = Bukkit.getServer().createInventory(null, rows * 9, title);
        this.slots = new GuiSlot[this.cont.getSize()];
        this.player = player;
        Rail.guiHandler().register(this);
        Bukkit.getServer().getScheduler().runTaskLater(Rail.INSTANCE, () -> {
            init();
            player.openInventory(cont);
        }, 1L);
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

    public int indexOf(GuiSlot slot) {
        return ArrayUtils.indexOf(slots, slot);
    }

    public void tick() {
        IntStream.range(0, slots.length)
                .forEach(i -> {
                    ItemStack currentDisplay = cont.getItem(i);
                    if (slots[i] != null) {
                        ItemStack stack = slots[i].stack();
                        if (ItemUtils.isNully(stack)) {
                            if (ItemUtils.isNotNully(currentDisplay))
                                cont.setItem(i, null);
                        } else if (ItemUtils.isNully(currentDisplay) || !currentDisplay.equals(stack)) {
                            cont.setItem(i, stack);
                        }
                    } else if (ItemUtils.isNully(currentDisplay) || !currentDisplay.equals(FILLER_STACK)) {
                        cont.setItem(i, FILLER_STACK);
                    }
                });
        player.updateInventory();
    }

    public void onInteract(InventoryClickEvent event) {
        if (slots[event.getSlot()] == null || !slots[event.getSlot()].onInteract((Player)event.getWhoClicked(), event))
            event.setCancelled(true);
    }

    public abstract void init();

    public void destroy() {
        // NO-OP
    }

}
