package io.github.phantamanta44.mcrail.module;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.HashSet;
import java.util.Set;

public class ModuleManager implements Listener {

    private final Set<IRailModule> modules;

    public ModuleManager() {
        this.modules = new HashSet<>();
    }

    @EventHandler
    public void onLoad(PluginEnableEvent event) {
        if (event.getPlugin() instanceof IRailModule) {
            modules.add((IRailModule)event.getPlugin());
            Rail.INSTANCE.getLogger().info("Found module: " + event.getPlugin().getClass().getName());
        }
    }

    public void act(InitPhase phase) {
        for (IRailModule module : modules) {
            try {
                phase.act(module);
            } catch (Exception e) {
                Rail.INSTANCE.getLogger().warning("Module " + module.getClass().getName() + " raised exception!");
                e.printStackTrace();
            }
        }
    }

}
