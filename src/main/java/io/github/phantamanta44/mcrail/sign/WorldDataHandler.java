package io.github.phantamanta44.mcrail.sign;

import io.github.phantamanta44.mcrail.RailMain;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldDataHandler implements Listener {

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        RailMain.INSTANCE.signManager().save(event.getWorld());
    }

    @EventHandler
    public void onLoad(WorldInitEvent event) {
        RailMain.INSTANCE.signManager().load(event.getWorld());
    }

    public void saveAll() {
        Bukkit.getServer().getWorlds().forEach(RailMain.INSTANCE.signManager()::load);
    }

}
