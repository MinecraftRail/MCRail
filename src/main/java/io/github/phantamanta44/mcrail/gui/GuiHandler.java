package io.github.phantamanta44.mcrail.gui;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

public class GuiHandler implements Listener {

    private final Collection<Gui> guis;

    public GuiHandler() {
        this.guis = new CopyOnWriteArraySet<>();
        Rail.INSTANCE.onTick(this::tick);
    }

    public void register(Gui gui) {
        guis.add(gui);
    }

    private void tick(long tick) {
        guis.forEach(Gui::tick);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isShiftClick()) {
            if (guis.stream().anyMatch(g -> g.inventory().equals(event.getInventory())))
                event.setCancelled(true);
        } else if (event.getClick() != ClickType.WINDOW_BORDER_LEFT
                && event.getClick() != ClickType.WINDOW_BORDER_RIGHT) {
            guis.stream().filter(gui -> event.getClickedInventory().equals(gui.inventory()))
                    .findAny().ifPresent(gui -> gui.onInteract(event));
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (guis.stream().anyMatch(g -> g.inventory().equals(event.getInventory())))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        guis.stream().filter(gui -> event.getInventory().equals(gui.inventory()))
                .findAny().ifPresent(gui -> {
                    guis.remove(gui);
                    gui.destroy();
                });
    }

}
