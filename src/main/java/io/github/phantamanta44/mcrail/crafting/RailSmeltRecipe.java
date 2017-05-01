package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.function.Function;
import java.util.function.Predicate;

public class RailSmeltRecipe {
    
    private Predicate<ItemStack> input;
    private Object inputType;
    private Function<ItemStack, ItemStack> output;
    private int xp;
    
    public RailSmeltRecipe() {
        this.input = null;
        this.inputType = null;
        this.output = null;
        this.xp = 0;
    }

    public RailSmeltRecipe withXp(int xp) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        this.xp = xp;
        return this;
    }

    public RailSmeltRecipe withInput(Predicate<ItemStack> ing, Material type) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        input = ing;
        inputType = type;
        return this;
    }

    public RailSmeltRecipe withInput(Predicate<ItemStack> ing, MaterialData type) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        input = ing;
        inputType = type;
        return this;
    }

    public RailSmeltRecipe withInput(Material ing) {
        return ing == Material.AIR
                ? withInput(ItemUtils::isNully, ing)
                : withInput(ItemUtils.matching(ing), ing);
    }

    public RailSmeltRecipe withInput(MaterialData ing) {
        return ing.getItemType() == Material.AIR
                ? withInput(ItemUtils::isNully, ing)
                : withInput(ItemUtils.matching(ing), ing);
    }

    public RailSmeltRecipe withInput(ItemStack ing) {
        return withInput(ItemUtils.matching(ing), ing.getType());
    }

    public RailSmeltRecipe withInput(String ing) {
        return withInput(ItemUtils.matching(ing), Rail.itemRegistry().get(ing).material());
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

    public boolean matches(ItemStack input) {
        return input.getType().equals(inputType) && this.input.test(input);
    }

    public ItemStack mapToResult(ItemStack input) {
        return output.apply(input);
    }

    public int xp() {
        return xp;
    }

    void registerBukkit() {
        if (inputType instanceof Material)
            Bukkit.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.STONE), (Material)inputType));
        else
            Bukkit.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.STONE), (MaterialData)inputType));
    }

}
