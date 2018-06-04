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
import io.github.phantamanta44.mcrail.item.VanillaContainerItemAdapter;
import io.github.phantamanta44.mcrail.model.IContainerItem;
import io.github.phantamanta44.mcrail.module.InitPhase;
import io.github.phantamanta44.mcrail.module.ModuleManager;
import io.github.phantamanta44.mcrail.oredict.OreDictionary;
import io.github.phantamanta44.mcrail.tile.RailTileHandler;
import io.github.phantamanta44.mcrail.tile.RailTileManager;
import io.github.phantamanta44.mcrail.tile.RailTileRegistry;
import io.github.phantamanta44.mcrail.tile.WorldDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

public class Rail extends JavaPlugin {

    public static Rail INSTANCE;

    private RailTileRegistry tileReg;
    private ItemRegistry itemReg;
    private FluidRegistry fluidReg;
    private AdapterRegistry<Block> blockAdapterReg;
    private AdapterRegistry<ItemStack> itemAdapterReg;

    private RailTileManager tileMan;
    private RecipeManager recipeMan;
    private WorldDataHandler wdh;
    private GuiHandler guiHandler;

    private BukkitTask tickTask;
    private long tick;
    private List<LongConsumer> tickHandlers;

    private ModuleManager modMan;
    private InitPhase initPhase;

    @Override
    public void onEnable() {
        getLogger().info("Rail pre-initializing!");
        INSTANCE = this;
        initPhase = InitPhase.NOT_INITIALIZED;
        getLogger().info("Creating registries...");
        tickHandlers = new LinkedList<>();
        tileReg = new RailTileRegistry();
        itemReg = new ItemRegistry();
        recipeMan = new RecipeManager();
        fluidReg = new FluidRegistry();
        blockAdapterReg = new AdapterRegistry<>();
        itemAdapterReg = new AdapterRegistry<>();
        itemAdapterReg.register(IFluidContainer.class, new FluidBucketAdapter());
        itemAdapterReg.register(IContainerItem.class, new VanillaContainerItemAdapter());
        getLogger().info("Registring hooks...");
        onTick(tileMan = new RailTileManager());
        Bukkit.getServer().getPluginManager().registerEvents(new RailTileHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(wdh = new WorldDataHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(guiHandler = new GuiHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CraftingHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SmeltingHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(modMan = new ModuleManager(), this);
        Bukkit.getServer().getPluginCommand("ritems").setExecutor(new CommandItems());
        Bukkit.getServer().getPluginCommand("ritem").setExecutor(new CommandItem());
        getLogger().info("Initializing tick handler...");
        tick = 0L;
        tickTask = Bukkit.getServer().getScheduler().runTaskTimer(this, this::tick, 1L, 1L);
        getLogger().info("Finished pre-initialization!");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, this::init);
    }

    private void init() {
        getLogger().info("Rail initializing!");
        for (int i = 1; i <= 5; i++) {
            phase(InitPhase.values()[i]);
            switch (initPhase) {
                case REGISTRATION:
                    getLogger().info("Initializing ore dictionary...");
                    OreDictionary.init();
                    break;
            }
            getLogger().info("Passing phase to modules...");
            modMan.act(initPhase);
        }
        phase(InitPhase.LOADING_DATA);
        getLogger().info("Loading world data...");
        wdh.loadAll();
        phase(InitPhase.INITIALIZED);
    }

    private void phase(InitPhase phase) {
        initPhase = phase;
        getLogger().info("Entered phase: " + initPhase.name());
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

    public static RailTileRegistry tileRegistry() {
        phaseCheck(InitPhase.REGISTRATION);
        return INSTANCE.tileReg;
    }

    public static ItemRegistry itemRegistry() {
        phaseCheck(InitPhase.REGISTRATION);
        return INSTANCE.itemReg;
    }

    public static FluidRegistry fluidRegistry() {
        phaseCheck(InitPhase.REGISTRATION);
        return INSTANCE.fluidReg;
    }

    public static AdapterRegistry<Block> blockAdapters() {
        phaseCheck(InitPhase.SETUP);
        return INSTANCE.blockAdapterReg;
    }

    public static AdapterRegistry<ItemStack> itemAdapters() {
        phaseCheck(InitPhase.SETUP);
        return INSTANCE.itemAdapterReg;
    }

    public static RailTileManager tileManager() {
        phaseCheck(InitPhase.LOADING_DATA);
        return INSTANCE.tileMan;
    }

    public static RecipeManager recipes() {
        phaseCheck(InitPhase.POST_REGISTRATION);
        return INSTANCE.recipeMan;
    }

    public static GuiHandler guiHandler() {
        phaseCheck(InitPhase.INITIALIZED);
        return INSTANCE.guiHandler;
    }

    public static void onLoad(Consumer<World> callback) {
        phaseCheck(InitPhase.POST_REGISTRATION);
        INSTANCE.wdh.registerLoadListener(callback);
    }

    public static void onSave(Consumer<World> callback) {
        phaseCheck(InitPhase.POST_REGISTRATION);
        INSTANCE.wdh.registerSaveListener(callback);
    }

    public static long currentTick() {
        phaseCheck(InitPhase.INITIALIZED);
        return INSTANCE.tick;
    }

    private static void phaseCheck(InitPhase phase) {
        if (INSTANCE.initPhase.ordinal() < phase.ordinal())
            throw new IllegalStateException("Cannot perform this action before " + phase.name() + " phase!");
    }

}
