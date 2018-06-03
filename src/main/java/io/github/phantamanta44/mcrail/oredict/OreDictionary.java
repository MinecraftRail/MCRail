package io.github.phantamanta44.mcrail.oredict;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.crafting.RailRecipe;
import io.github.phantamanta44.mcrail.crafting.RailShapelessRecipe;
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

public class OreDictionary {

    private static OreDictionary INSTANCE;

    public static void init() {
        INSTANCE = new OreDictionary();
        INSTANCE.doInit();
    }

    public static void register(String id, Predicate<ItemStack> test) {
        INSTANCE.dictionary.computeIfAbsent(id, k -> new LinkedList<>()).add(test);
    }

    public static void register(String id, ItemStack stack) {
        register(id, ItemUtils.matching(stack));
    }

    public static void register(String id, Material material) {
        register(id, ItemUtils.matching(material));
    }

    public static void register(String id, MaterialData data) {
        register(id, ItemUtils.matching(data));
    }

    public static void register(String id, String itemId) {
        register(id, ItemUtils.matching(itemId));
    }

    public static boolean matches(String id, ItemStack stack) {
        Collection<Predicate<ItemStack>> tests = INSTANCE.dictionary.get(id);
        return tests != null && tests.stream().anyMatch(t -> t.test(stack));
    }

    private final Map<String, Collection<Predicate<ItemStack>>> dictionary;

    private OreDictionary() {
        this.dictionary = new HashMap<>();
    }

    // Adapted from the MinecraftForge project, licensed under GPLv2
    private void doInit() {
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
        Collection<Predicate<ItemStack>> exclusions = new ArrayList<>();
        exclusions.add(ItemUtils.matching(Material.LAPIS_BLOCK));
        exclusions.add(ItemUtils.matching(Material.COOKIE));
        exclusions.add(ItemUtils.matching(Material.SMOOTH_BRICK));
        exclusions.add(ItemUtils.matching(Material.STEP));
        exclusions.add(ItemUtils.matching(Material.COBBLESTONE_STAIRS));
        exclusions.add(ItemUtils.matching(Material.COBBLE_WALL));
        exclusions.add(ItemUtils.matching(Material.FENCE));
        exclusions.add(ItemUtils.matching(Material.FENCE_GATE));
        exclusions.add(ItemUtils.matching(Material.WOOD_STAIRS));
        exclusions.add(ItemUtils.matching(Material.SPRUCE_FENCE));
        exclusions.add(ItemUtils.matching(Material.SPRUCE_FENCE_GATE));
        exclusions.add(ItemUtils.matching(Material.SPRUCE_WOOD_STAIRS));
        exclusions.add(ItemUtils.matching(Material.BIRCH_WOOD_STAIRS));
        exclusions.add(ItemUtils.matching(Material.BIRCH_FENCE_GATE));
        exclusions.add(ItemUtils.matching(Material.BIRCH_WOOD_STAIRS));
        exclusions.add(ItemUtils.matching(Material.JUNGLE_FENCE));
        exclusions.add(ItemUtils.matching(Material.JUNGLE_FENCE_GATE));
        exclusions.add(ItemUtils.matching(Material.JUNGLE_WOOD_STAIRS));
        exclusions.add(ItemUtils.matching(Material.ACACIA_FENCE));
        exclusions.add(ItemUtils.matching(Material.ACACIA_FENCE_GATE));
        exclusions.add(ItemUtils.matching(Material.ACACIA_STAIRS));
        exclusions.add(ItemUtils.matching(Material.DARK_OAK_FENCE));
        exclusions.add(ItemUtils.matching(Material.DARK_OAK_FENCE_GATE));
        exclusions.add(ItemUtils.matching(Material.DARK_OAK_STAIRS));
        exclusions.add(ItemUtils.matching(Material.WOOD_STEP));
        exclusions.add(ItemUtils.matching(Material.THIN_GLASS));
        exclusions.add(ItemUtils.matching(Material.BOAT));
        exclusions.add(ItemUtils.matching(Material.WOOD_DOOR));

        Iterator<Recipe> recipes = Bukkit.getServer().recipeIterator();

        // Search vanilla recipes for recipes to replace
        while (recipes.hasNext()) {
            Recipe obj = recipes.next();
            if (obj instanceof ShapedRecipe) {
                ShapedRecipe recipe = (ShapedRecipe)obj;
                ItemStack output = recipe.getResult();
                if (ItemUtils.isNotNully(output) && exclusions.stream().anyMatch(e -> e.test(output)))
                    continue;
                if (recipe.getIngredientMap().values().stream().anyMatch(
                        s -> replaceStacks.stream().anyMatch(p -> p.test(s)))) {
                    recipes.remove();
                    RailRecipe newRecipe = new RailRecipe();
                    for (String line : recipe.getShape())
                        newRecipe.line(line);
                    recipe.getIngredientMap().forEach((k, v) -> {
                        Optional<String> str = replacements.entrySet().stream()
                                .filter(r -> r.getKey().test(v))
                                .map(Map.Entry::getValue)
                                .findAny();
                        if (str.isPresent())
                            newRecipe.ingredient(k, str.get());
                        else
                            newRecipe.ingredient(k, v);
                    });
                    Rail.recipes().register(newRecipe.withResult(recipe.getResult()));
                }
            } else if (obj instanceof ShapelessRecipe) {
                ShapelessRecipe recipe = (ShapelessRecipe) obj;
                ItemStack output = recipe.getResult();
                if (ItemUtils.isNotNully(output) && exclusions.stream().anyMatch(e -> e.test(output)))
                    continue;
                if (recipe.getIngredientList().stream().anyMatch(
                        s -> replaceStacks.stream().anyMatch(p -> p.test(s)))) {
                    recipes.remove();
                    RailShapelessRecipe newRecipe = new RailShapelessRecipe();
                    recipe.getIngredientList().forEach(i -> {
                        Optional<String> str = replacements.entrySet().stream()
                                .filter(r -> r.getKey().test(i))
                                .map(Map.Entry::getValue)
                                .findAny();
                        if (str.isPresent())
                            newRecipe.ingredient(str.get());
                        else
                            newRecipe.ingredient(i);
                    });
                    Rail.recipes().register(newRecipe.withOutput(recipe.getResult()));
                }
            }
        }
    }

}
