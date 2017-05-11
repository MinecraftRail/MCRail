package io.github.phantamanta44.mcrail.sign;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public class WorldDataHandler implements Listener {

    private final Collection<Consumer<World>> loadListeners, saveListeners;

    public WorldDataHandler() {
        this.loadListeners = new LinkedList<>();
        this.saveListeners = new LinkedList<>();
    }

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        Rail.signManager().save(event.getWorld());
        saveListeners.forEach(cb -> cb.accept(event.getWorld()));
    }

    @EventHandler
    public void onLoad(WorldInitEvent event) {
        Rail.signManager().load(event.getWorld());
        loadListeners.forEach(cb -> cb.accept(event.getWorld()));
    }

    public void saveAll() {
        Bukkit.getServer().getWorlds().forEach(Rail.signManager()::save);
    }

    public void loadAll() {
        Bukkit.getServer().getWorlds().forEach(Rail.signManager()::load);
    }

    public void registerLoadListener(Consumer<World> callback) {
        loadListeners.add(callback);
    }

    public void registerSaveListener(Consumer<World> callback) {
        saveListeners.add(callback);
    }
    
}
