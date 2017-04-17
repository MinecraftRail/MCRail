package io.github.phantamanta44.mcrail;

import io.github.phantamanta44.mcrail.adapter.AdapterRegistry;
import io.github.phantamanta44.mcrail.command.CommandSign;
import io.github.phantamanta44.mcrail.command.CommandSigns;
import io.github.phantamanta44.mcrail.fluid.FluidBucketAdapter;
import io.github.phantamanta44.mcrail.fluid.FluidRegistry;
import io.github.phantamanta44.mcrail.fluid.IFluidContainer;
import io.github.phantamanta44.mcrail.gui.GuiHandler;
import io.github.phantamanta44.mcrail.sign.SignBlockHandler;
import io.github.phantamanta44.mcrail.sign.SignManager;
import io.github.phantamanta44.mcrail.sign.SignRegistry;
import io.github.phantamanta44.mcrail.sign.WorldDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;
import java.util.function.LongConsumer;

public class Rail extends JavaPlugin {

    public static Rail INSTANCE;

    private SignRegistry signReg;
    private FluidRegistry fluidReg;
    private AdapterRegistry<Block> blockAdapterReg;
    private AdapterRegistry<ItemStack> itemAdapterReg;

    private SignManager signMan;
    private WorldDataHandler wdh;
    private GuiHandler guiHandler;

    private BukkitTask tickTask;
    private long tick;
    private List<LongConsumer> tickHandlers;

    @Override
    public void onEnable() {
        INSTANCE = this;
        tickHandlers = new LinkedList<>();
        signReg = new SignRegistry();
        fluidReg = new FluidRegistry();
        blockAdapterReg = new AdapterRegistry<>();
        itemAdapterReg = new AdapterRegistry<>();
        itemAdapterReg.register(IFluidContainer.class, new FluidBucketAdapter());
        onTick(signMan = new SignManager());
        Bukkit.getServer().getPluginManager().registerEvents(new SignBlockHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(wdh = new WorldDataHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(guiHandler = new GuiHandler(), this);
        Bukkit.getServer().getPluginCommand("rsigns").setExecutor(new CommandSigns());
        Bukkit.getServer().getPluginCommand("rsign").setExecutor(new CommandSign());
        tick = 0L;
        tickTask = Bukkit.getServer().getScheduler().runTaskTimer(this, this::tick, 1L, 1L);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, wdh::saveAll);
    }

    @Override
    public void onDisable() {
        tickTask.cancel();
        HandlerList.unregisterAll(this);
        wdh.saveAll();
    }

    public void onTick(LongConsumer handler) {
        tickHandlers.add(handler);
    }

    private void tick() {
        tickHandlers.forEach(h -> h.accept(tick));
        tick++;
    }

    public static SignRegistry signRegistry() {
        return INSTANCE.signReg;
    }

    public static FluidRegistry fluidRegistry() {
        return INSTANCE.fluidReg;
    }

    public static AdapterRegistry<Block> blockAdapters() {
        return INSTANCE.blockAdapterReg;
    }

    public static AdapterRegistry<ItemStack> itemAdapters() {
        return INSTANCE.itemAdapterReg;
    }

    public static SignManager signManager() {
        return INSTANCE.signMan;
    }

    public static GuiHandler guiHandler() {
        return INSTANCE.guiHandler;
    }

    public static long currentTick() {
        return INSTANCE.tick;
    }

}
