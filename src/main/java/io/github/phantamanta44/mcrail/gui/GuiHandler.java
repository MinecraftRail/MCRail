package io.github.phantamanta44.mcrail.gui;

import io.github.phantamanta44.mcrail.RailMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

public class GuiHandler implements Listener {

    private final Collection<Gui> guis;

    public GuiHandler() {
        this.guis = new CopyOnWriteArraySet<>();
        RailMain.INSTANCE.onTick(this::tick);
    }

    public void register(Gui gui) {
        guis.add(gui);
        gui.init();
    }

    private void tick(long tick) {
        guis.forEach(Gui::tick);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        guis.stream().filter(gui -> event.getInventory().equals(gui.inventory()))
                .findAny().ifPresent(gui -> gui.onInteract(event));
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
