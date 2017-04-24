package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.function.Function;
import java.util.function.Predicate;

public class RailSmeltRecipe {
    
    private Predicate<ItemStack> input;
    private Function<ItemStack, ItemStack> output;
    private int xp, cooktime;
    
    public RailSmeltRecipe() {
        this.input = null;
        this.output = null;
        this.xp = 0;
        this.cooktime = 200;
    }

    public RailSmeltRecipe withXp(int xp) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        this.xp = xp;
        return this;
    }

    public RailSmeltRecipe withCookTime(int time) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        this.cooktime = time;
        return this;
    }

    public RailSmeltRecipe withInput(Predicate<ItemStack> ing) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        input = ing;
        return this;
    }

    public RailSmeltRecipe withInput(Material ing) {
        return ing == Material.AIR
                ? withInput(ItemUtils::isNully)
                : withInput(s -> ItemUtils.isNotNully(s) && s.getType().equals(ing) && !ItemUtils.isRailItem(s));
    }

    public RailSmeltRecipe withInput(MaterialData ing) {
        return ing.getItemType() == Material.AIR
                ? withInput(ItemUtils::isNully)
                : withInput(s -> ItemUtils.isNotNully(s) && s.getData().equals(ing) && !ItemUtils.isRailItem(s));
    }

    public RailSmeltRecipe withInput(ItemStack ing) {
        return withInput(s -> ItemUtils.isNotNully(s) && ing.isSimilar(s));
    }

    public RailSmeltRecipe withInput(String ing) {
        return withInput(s -> ItemUtils.isNotNully(s) && ItemUtils.instOf(ing, s));
    }

    public RailSmeltRecipe withOutput(Function<ItemStack, ItemStack> mapper) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        output = mapper;
        return this;
    }

    public RailSmeltRecipe withOutput(ItemStack stack) {
        return withOutput(in -> stack.clone());
    }

    public RailSmeltRecipe withOutput(String id, int amount) {
        return withOutput(in -> Rail.itemRegistry().create(id, amount));
    }

    public RailSmeltRecipe withOutput(String id) {
        return withOutput(id, 1);
    }

    // TODO some kind of matches(ItemStack input) function
    
}
