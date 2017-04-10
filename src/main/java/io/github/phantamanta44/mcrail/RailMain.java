package io.github.phantamanta44.mcrail;

import io.github.phantamanta44.mcrail.sign.SignPlacementHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;
import java.util.function.LongConsumer;

public class RailMain extends JavaPlugin {

    public static RailMain INSTANCE;

    private SignPlacementHandler handlerSign;

    private BukkitTask tickTask;
    private long tick;
    private List<LongConsumer> tickHandlers;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getServer().getPluginManager().registerEvents(handlerSign = new SignPlacementHandler(), this);
        tickHandlers = new LinkedList<>();
        tick = 0L;
        tickTask = Bukkit.getServer().getScheduler().runTaskTimer(this, this::tick, 1L, 1L);
    }

    @Override
    public void onDisable() {
        tickTask.cancel();
        HandlerList.unregisterAll(this);
    }

    public void onTick(LongConsumer handler) {
        tickHandlers.add(handler);
    }

    private void tick() {
        tickHandlers.forEach(h -> h.accept(tick));
        tick++;
    }

}
