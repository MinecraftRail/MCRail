package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.oredict.OreDictionary;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.function.Predicate;

public class RecipeManager {

    private final Collection<IGridRecipe> recipeReg;
    private final Collection<RailSmeltRecipe> smeltingReg;
    private final Map<Predicate<ItemStack>, Integer> fuelMap;

    public RecipeManager() {
        this.recipeReg = new HashSet<>();
        this.smeltingReg = new HashSet<>();
        this.fuelMap = new HashMap<>();
    }

    public void register(IGridRecipe recipe) {
        recipeReg.add(recipe);
    }

    public void register(RailSmeltRecipe recipe) {
        smeltingReg.add(recipe);
        recipe.registerBukkit();
    }

    public void registerFurnaceFuel(Predicate<ItemStack> fuel, int ticks) {
        fuelMap.put(fuel, ticks);
    }

    public void registerFurnaceFuel(Material fuel, int ticks) {
        registerFurnaceFuel(ItemUtils.matching(fuel), ticks);
    }

    public void registerFurnaceFuel(MaterialData fuel, int ticks) {
        registerFurnaceFuel(ItemUtils.matching(fuel), ticks);
    }

    public void registerFurnaceFuel(ItemStack fuel, int ticks) {
        registerFurnaceFuel(ItemUtils.matching(fuel), ticks);
    }

    public void registerFurnaceFuel(String id, int ticks) {
        registerFurnaceFuel(ItemUtils.matching(id), ticks);
    }

    public void registerFurnaceFuelOreDict(String id, int ticks) {
        registerFurnaceFuel(OreDictionary.predicate(id), ticks);
    }

    public ItemStack recipeCheck(ItemStack[] mat) {
        return recipeReg.stream()
                .map(r -> {
                    ItemStack[] match = r.matches(mat);
                    return match == null ? null : r.mapToResult(match);
                })
                .filter(Objects::nonNull)
                .findAny().orElse(null);
    }

    public RailSmeltRecipe getSmelting(ItemStack input) {
        return smeltingReg.stream()
                .filter(r -> r.matches(input))
                .findAny().orElse(null);
    }

    public int getBurnTime(ItemStack stack) {
        return fuelMap.entrySet().stream()
                .filter(e -> e.getKey().test(stack))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0);
    }

}
