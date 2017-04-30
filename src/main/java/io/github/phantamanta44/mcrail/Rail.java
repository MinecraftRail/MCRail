package io.github.phantamanta44.mcrail;

import io.github.phantamanta44.mcrail.adapter.AdapterRegistry;
import io.github.phantamanta44.mcrail.command.CommandItem;
import io.github.phantamanta44.mcrail.command.CommandItems;
import io.github.phantamanta44.mcrail.crafting.CraftingHandler;
import io.github.phantamanta44.mcrail.crafting.RecipeManager;
import io.github.phantamanta44.mcrail.crafting.SmeltingHandler;
import io.github.phantamanta44.mcrail.fluid.FluidBucketAdapter;
import io.github.phantamanta44.mcrail.fluid.FluidRegistry;
import io.github.phantamanta44.mcrail.fluid.IFluidContainer;
import io.github.phantamanta44.mcrail.gui.GuiHandler;
import io.github.phantamanta44.mcrail.item.ItemHandler;
import io.github.phantamanta44.mcrail.item.ItemRegistry;
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
    private ItemRegistry itemReg;
    private FluidRegistry fluidReg;
    private AdapterRegistry<Block> blockAdapterReg;
    private AdapterRegistry<ItemStack> itemAdapterReg;

    private SignManager signMan;
    private RecipeManager recipeMan;
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
        itemReg = new ItemRegistry();
        recipeMan = new RecipeManager();
        fluidReg = new FluidRegistry();
        blockAdapterReg = new AdapterRegistry<>();
        itemAdapterReg = new AdapterRegistry<>();
        itemAdapterReg.register(IFluidContainer.class, new FluidBucketAdapter());
        onTick(signMan = new SignManager());
        Bukkit.getServer().getPluginManager().registerEvents(new SignBlockHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(wdh = new WorldDataHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(guiHandler = new GuiHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CraftingHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SmeltingHandler(), this);
        Bukkit.getServer().getPluginCommand("ritems").setExecutor(new CommandItems());
        Bukkit.getServer().getPluginCommand("ritem").setExecutor(new CommandItem());
        tick = 0L;
        tickTask = Bukkit.getServer().getScheduler().runTaskTimer(this, this::tick, 1L, 1L);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, wdh::loadAll);
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

    public static ItemRegistry itemRegistry() {
        return INSTANCE.itemReg;
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

    public static RecipeManager recipes() {
        return INSTANCE.recipeMan;
    }

    public static GuiHandler guiHandler() {
        return INSTANCE.guiHandler;
    }

    public static long currentTick() {
        return INSTANCE.tick;
    }

}
