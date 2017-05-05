package io.github.phantamanta44.mcrail.oredict;

import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.function.Predicate;

public class OreDictionary { // TODO OREDICT CRAFTING!!!!!!!!

    private static OreDictionary INSTANCE;

    public static void init() {
        INSTANCE = new OreDictionary();
    }

    public static void register(String id, Predicate<ItemStack> test) {
        // TODO Implement
    }

    public static void register(String id, ItemStack stack) {
        // TODO Implement
    }

    public static void register(String id, Material material) {
        // TODO Implement
    }

    public static void register(String id, MaterialData data) {
        // TODO Implement
    }

    public static void register(String id, String itemId) {
        // TODO Implement
    }

    public static boolean matches(String id, ItemStack stack) {
        // TODO Implement
    }

    // Adapted from the MinecraftForge project, licensed under GPLv2
    private OreDictionary() {
        // tree- and wood-related things
        register("logWood", Material.LOG);
        register("logWood", Material.LOG_2);
        register("plankWood", Material.WOOD);
        register("slabWood", Material.WOOD_STEP);
        register("stairWood", Material.WOOD_STAIRS);
        register("stairWood", Material.SPRUCE_WOOD_STAIRS);
        register("stairWood", Material.BIRCH_WOOD_STAIRS);
        register("stairWood", Material.JUNGLE_WOOD_STAIRS);
        register("stairWood", Material.ACACIA_STAIRS);
        register("stairWood", Material.DARK_OAK_STAIRS);
        register("stickWood", Material.STICK);
        register("treeSapling", Material.SAPLING);
        register("treeLeaves", Material.LEAVES);
        register("treeLeaves", Material.LEAVES_2);
        register("vine", Material.VINE);

        // Ores
        register("oreGold", Material.GOLD_ORE);
        register("oreIron", Material.IRON_ORE);
        register("oreLapis", Material.LAPIS_ORE);
        register("oreDiamond", Material.DIAMOND_ORE);
        register("oreRedstone", Material.REDSTONE_ORE);
        register("oreEmerald", Material.EMERALD_ORE);
        register("oreQuartz", Material.QUARTZ_ORE);
        register("oreCoal", Material.COAL_ORE);

        // ingots/nuggets
        register("ingotIron", Material.IRON_INGOT);
        register("ingotGold", Material.GOLD_INGOT);
        register("ingotBrick", Material.CLAY_BRICK);
        register("ingotBrickNether", Material.NETHER_BRICK_ITEM);
        register("nuggetGold", Material.GOLD_NUGGET);

        // gems and dusts
        register("gemDiamond", Material.DIAMOND);
        register("gemEmerald", Material.EMERALD);
        register("gemQuartz", Material.QUARTZ);
        register("gemPrismarine", Material.PRISMARINE_SHARD);
        register("dustPrismarine", Material.PRISMARINE_CRYSTALS);
        register("dustRedstone", Material.REDSTONE);
        register("dustGlowstone", Material.GLOWSTONE_DUST);
        register("gemLapis", new MaterialData(Material.INK_SACK, (byte)4));

        // storage Material
        register("blockGold", Material.GOLD_BLOCK);
        register("blockIron", Material.IRON_BLOCK);
        register("blockLapis", Material.LAPIS_BLOCK);
        register("blockDiamond", Material.DIAMOND_BLOCK);
        register("blockRedstone", Material.REDSTONE_BLOCK);
        register("blockEmerald", Material.EMERALD_BLOCK);
        register("blockQuartz", Material.QUARTZ_BLOCK);
        register("blockCoal", Material.COAL_BLOCK);

        // crops
        register("cropWheat", Material.WHEAT);
        register("cropPotato", Material.POTATO);
        register("cropCarrot", Material.CARROT);
        register("cropNetherWart", Material.NETHER_STALK);
        register("sugarcane", Material.SUGAR_CANE);
        register("blockCactus", Material.CACTUS);

        // misc materials
        register("dye", Material.INK_SACK);
        register("paper", new ItemStack(Material.PAPER));

        // mob drops
        register("slimeball", Material.SLIME_BALL);
        register("enderpearl", Material.ENDER_PEARL);
        register("bone", Material.BONE);
        register("gunpowder", Material.SULPHUR);
        register("string", Material.STRING);
        register("netherStar", Material.NETHER_STAR);
        register("leather", Material.LEATHER);
        register("feather", Material.FEATHER);
        register("egg", Material.EGG);

        // records
        register("record", Material.GOLD_RECORD);
        register("record", Material.GREEN_RECORD);
        register("record", Material.RECORD_3);
        register("record", Material.RECORD_4);
        register("record", Material.RECORD_5);
        register("record", Material.RECORD_6);
        register("record", Material.RECORD_7);
        register("record", Material.RECORD_8);
        register("record", Material.RECORD_9);
        register("record", Material.RECORD_10);
        register("record", Material.RECORD_11);
        register("record", Material.RECORD_12);

        // Material
        register("dirt", Material.DIRT);
        register("grass", Material.GRASS);
        register("stone", Material.STONE);
        register("cobblestone", Material.COBBLESTONE);
        register("gravel", Material.GRAVEL);
        register("sand", Material.SAND);
        register("sandstone", Material.SANDSTONE);
        register("sandstone", Material.RED_SANDSTONE);
        register("netherrack", Material.NETHERRACK);
        register("obsidian", Material.OBSIDIAN);
        register("glowstone", Material.GLOWSTONE);
        register("endstone", Material.ENDER_STONE);
        register("torch", Material.TORCH);
        register("workbench", Material.WORKBENCH);
        register("Materiallime", Material.SLIME_BLOCK);
        register("blockPrismarine", new MaterialData(Material.PRISMARINE, (byte)0));
        register("blockPrismarineBrick", new MaterialData(Material.PRISMARINE, (byte)1));
        register("blockPrismarineDark", new MaterialData(Material.PRISMARINE, (byte)2));
        register("stoneGranite", new MaterialData(Material.STONE, (byte)1));
        register("stoneGranitePolished", new MaterialData(Material.STONE, (byte)2));
        register("stoneDiorite", new MaterialData(Material.STONE, (byte)3));
        register("stoneDioritePolished", new MaterialData(Material.STONE, (byte)4));
        register("stoneAndesite", new MaterialData(Material.STONE, (byte)5));
        register("stoneAndesitePolished", new MaterialData(Material.STONE, (byte)6));
        register("blockGlassColorless", Material.GLASS);
        register("blockGlass", Material.GLASS);
        register("blockGlass", Material.STAINED_GLASS);
        //blockGlass{Color} is added below with dyes
        register("paneGlassColorless", Material.THIN_GLASS);
        register("paneGlass", Material.THIN_GLASS);
        register("paneGlass", Material.STAINED_GLASS_PANE);
        //paneGlass{Color} is added below with dyes

        // chests
        register("chest", Material.CHEST);
        register("chest", Material.ENDER_CHEST);
        register("chest", Material.TRAPPED_CHEST);
        register("chestWood", Material.CHEST);
        register("chestEnder", Material.ENDER_CHEST);
        register("chestTrapped", Material.TRAPPED_CHEST);

        // Build our list of items to replace with ore tags
        Map<Predicate<ItemStack>, String> replacements = new HashMap<>();

        // wood-related things
        replacements.put(ItemUtils.matching(Material.STICK), "stickWood");
        replacements.put(ItemUtils.matching(Material.WOOD), "plankWood");
        replacements.put(ItemUtils.matching(Material.WOOD_STEP), "slabWood");

        // ingots/nuggets
        replacements.put(ItemUtils.matching(Material.GOLD_INGOT), "ingotGold");
        replacements.put(ItemUtils.matching(Material.IRON_INGOT), "ingotIron");

        // gems and dusts
        replacements.put(ItemUtils.matching(Material.DIAMOND), "gemDiamond");
        replacements.put(ItemUtils.matching(Material.EMERALD), "gemEmerald");
        replacements.put(ItemUtils.matching(Material.PRISMARINE_SHARD), "gemPrismarine");
        replacements.put(ItemUtils.matching(Material.PRISMARINE_CRYSTALS), "dustPrismarine");
        replacements.put(ItemUtils.matching(Material.REDSTONE), "dustRedstone");
        replacements.put(ItemUtils.matching(Material.GLOWSTONE_DUST), "dustGlowstone");

        // crops
        replacements.put(ItemUtils.matching(Material.SUGAR_CANE), "sugarcane");
        replacements.put(ItemUtils.matching(Material.CACTUS), "blockCactus");

        // misc materials
        replacements.put(ItemUtils.matching(Material.PAPER), "paper");

        // mob drops
        replacements.put(ItemUtils.matching(Material.SLIME_BALL), "slimeball");
        replacements.put(ItemUtils.matching(Material.STRING), "string");
        replacements.put(ItemUtils.matching(Material.LEATHER), "leather");
        replacements.put(ItemUtils.matching(Material.ENDER_PEARL), "enderpearl");
        replacements.put(ItemUtils.matching(Material.SULPHUR), "gunpowder");
        replacements.put(ItemUtils.matching(Material.NETHER_STAR), "netherStar");
        replacements.put(ItemUtils.matching(Material.FEATHER), "feather");
        replacements.put(ItemUtils.matching(Material.BONE), "bone");
        replacements.put(ItemUtils.matching(Material.EGG), "egg");

        // blocks
        replacements.put(ItemUtils.matching(Material.STONE), "stone");
        replacements.put(ItemUtils.matching(Material.COBBLESTONE), "cobblestone");
        replacements.put(ItemUtils.matching(Material.GLOWSTONE), "glowstone");
        replacements.put(ItemUtils.matching(Material.GLASS), "blockGlassColorless");
        replacements.put(ItemUtils.matching(Material.PRISMARINE), "prismarine");
        replacements.put(ItemUtils.matching(new MaterialData(Material.STONE, (byte)1)), "stoneGranite");
        replacements.put(ItemUtils.matching(new MaterialData(Material.STONE, (byte)2)), "stoneGranitePolished");
        replacements.put(ItemUtils.matching(new MaterialData(Material.STONE, (byte)3)), "stoneDiorite");
        replacements.put(ItemUtils.matching(new MaterialData(Material.STONE, (byte)4)), "stoneDioritePolished");
        replacements.put(ItemUtils.matching(new MaterialData(Material.STONE, (byte)5)), "stoneAndesite");
        replacements.put(ItemUtils.matching(new MaterialData(Material.STONE, (byte)6)), "stoneAndesitePolished");

        // chests
        replacements.put(ItemUtils.matching(Material.CHEST), "chestWood");
        replacements.put(ItemUtils.matching(Material.ENDER_CHEST), "chestEnder");
        replacements.put(ItemUtils.matching(Material.TRAPPED_CHEST), "chestTrapped");

        // Register dyes
        String[] dyes = {
                "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray",
                "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"
        };

        for (int i = 0; i < 16; i++) {
            Predicate<ItemStack> dye = ItemUtils.matching(new MaterialData(Material.INK_SACK, (byte)i));
            Predicate<ItemStack> block = ItemUtils.matching(new MaterialData(Material.STAINED_GLASS, (byte)(15 - i)));
            Predicate<ItemStack> pane = ItemUtils.matching(new MaterialData(Material.STAINED_GLASS_PANE, (byte)(15 - i)));
            register("dye" + dyes[i], dye);
            register("blockGlass" + dyes[i], block);
            register("paneGlass" + dyes[i], pane);
            replacements.put(dye, "dye" + dyes[i]);
            replacements.put(block, "blockGlass" + dyes[i]);
            replacements.put(pane, "paneGlass" + dyes[i]);
        }

        Collection<Predicate<ItemStack>> replaceStacks = replacements.keySet();

        // Ignore recipes for the following items
        Collection<Predicate<ItemStack>> exclusions = new ArrayList<>(); // TODO Populate
        /*
        new ItemStack(Material.LAPIS_BLOCK),
        new ItemStack(Material.COOKIE),
        new ItemStack(Material.SMOOTH_BRICK),
        new ItemStack(Material.STEP, 1, (short)-1),
        new ItemStack(Material.COBBLESTONE_STAIRS),
        new ItemStack(Material.COBBLE_WALL),
        new ItemStack(Material.FENCE),
        new ItemStack(Material.FENCE_GATE),
        new ItemStack(Material.WOOD_STAIRS),
        new ItemStack(Material.SPRUCE_FENCE),
        new ItemStack(Material.SPRUCE_FENCE_GATE),
        new ItemStack(Material.SPRUCE_WOOD_STAIRS),
        new ItemStack(Material.BIRCH_WOOD_STAIRS),
        new ItemStack(Material.BIRCH_FENCE_GATE),
        new ItemStack(Material.BIRCH_WOOD_STAIRS),
        new ItemStack(Material.JUNGLE_FENCE),
        new ItemStack(Material.JUNGLE_FENCE_GATE),
        new ItemStack(Material.JUNGLE_WOOD_STAIRS),
        new ItemStack(Material.ACACIA_FENCE),
        new ItemStack(Material.ACACIA_FENCE_GATE),
        new ItemStack(Material.ACACIA_STAIRS),
        new ItemStack(Material.DARK_OAK_FENCE),
        new ItemStack(Material.DARK_OAK_FENCE_GATE),
        new ItemStack(Material.DARK_OAK_STAIRS),
        new ItemStack(Material.WOOD_STEP),
        new ItemStack(Material.THIN_GLASS),
        new ItemStack(Material.BOAT),
        new ItemStack(Material.WOOD_DOOR);
        */

        Iterator<Recipe> recipes = Bukkit.getServer().recipeIterator();
        List<Recipe> recipesToAdd = new ArrayList<>();

        // Search vanilla recipes for recipes to replace
        while (recipes.hasNext()) {
            Recipe obj = recipes.next();
            if (obj instanceof ShapedRecipe) {
                ShapedRecipe recipe = (ShapedRecipe)obj;
                ItemStack output = recipe.getResult();
                if (ItemUtils.isNotNully(output) && exclusions.stream().anyMatch(e -> e.test(output)))
                    continue;
                if (containsMatch(true, recipe.recipeItems, replaceStacks)) {
                    recipes.remove();
                    recipesToAdd.add(new ShapedOreRecipe(recipe, replacements));
                }
            } else if (obj instanceof ShapelessRecipe) {
                ShapelessRecipe recipe = (ShapelessRecipe) obj;
                ItemStack output = recipe.getResult();
                if (ItemUtils.isNotNully(output) && exclusions.stream().anyMatch(e -> e.test(output)))
                    continue;
                if (containsMatch(true, recipe.recipeMaterial.toArray(new ItemStack[recipe.recipeMaterial.size()]), replaceStacks)) {
                    recipes.remove();
                    recipesToAdd.add(new ShapelessOreRecipe(recipe, replacements));
                }
            }
        }
        recipesToAdd.forEach(Bukkit.getServer()::addRecipe);
    }

}
