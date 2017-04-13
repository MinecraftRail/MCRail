package io.github.phantamanta44.mcrail;

import io.github.phantamanta44.mcrail.gui.GuiHandler;
import io.github.phantamanta44.mcrail.sign.SignBlockHandler;
import io.github.phantamanta44.mcrail.tile.SignManager;
import io.github.phantamanta44.mcrail.tile.SignRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;
import java.util.function.LongConsumer;

public class RailMain extends JavaPlugin {

    public static RailMain INSTANCE;

    private SignRegistry signReg;
    private SignManager signMan;
    private GuiHandler guiHandler;

    private BukkitTask tickTask;
    private long tick;
    private List<LongConsumer> tickHandlers;

    @Override
    public void onEnable() {
        INSTANCE = this;
        tickHandlers = new LinkedList<>();
        signReg = new SignRegistry();
        signMan = new SignManager();
        Bukkit.getServer().getPluginManager().registerEvents(new SignBlockHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(guiHandler = new GuiHandler(), this);
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

    public SignRegistry registry() {
        return signReg;
    }

    public SignManager signManager() {
        return signMan;
    }

    public GuiHandler guiHandler() {
        return guiHandler;
    }

}
