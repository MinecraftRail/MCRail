package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class RailRecipe implements IGridRecipe {

    private final String[] lines;
    private final Map<Character, Predicate<ItemStack>> ingredients;
    private int lineIndex, xDim, yDim;
    private Function<ItemStack[], ItemStack> result;

    public RailRecipe() {
        this.lines = new String[3];
        this.ingredients = new HashMap<>();
        this.ingredients.put(' ', s -> s.getType().equals(Material.AIR));
        this.lineIndex = this.xDim = this.yDim = 0;
        this.result = null;
    }

    public RailRecipe line(String line) {
        if (result != null)
            throw new IllegalStateException("This recipe is already finished!");
        lines[lineIndex] = line;
        lineIndex++;
        return this;
    }

    public RailRecipe ingredient(char c, Predicate<ItemStack> ing) {
        if (result != null)
            throw new IllegalStateException("This recipe is already finished!");
        ingredients.put(c, ing);
        return this;
    }

    public RailRecipe ingredient(char c, Material ing) {
        return ing == Material.AIR
                ? ingredient(c, ItemUtils::isNully)
                : ingredient(c, s -> ItemUtils.isNotNully(s) && s.getType().equals(ing) && !ItemUtils.isRailItem(s));
    }

    public RailRecipe ingredient(char c, MaterialData ing) {
        return ing.getItemType() == Material.AIR
                ? ingredient(c, ItemUtils::isNully)
                : ingredient(c, s -> ItemUtils.isNotNully(s) && s.getData().equals(ing) && !ItemUtils.isRailItem(s));
    }

    public RailRecipe ingredient(char c, ItemStack ing) {
        return ingredient(c, s -> ItemUtils.isNotNully(s) && ing.isSimilar(s));
    }

    public RailRecipe ingredient(char c, String ing) {
        return ingredient(c, s -> ItemUtils.isNotNully(s) && ItemUtils.instOf(ing, s));
    }

    public RailRecipe withResult(Function<ItemStack[], ItemStack> mapper) {
        if (result != null)
            throw new IllegalStateException("This recipe is already finished!");
        xDim = lines[0].length();
        while (yDim < lines.length && lines[yDim] != null)
            yDim++;
        if (xDim == 0 || yDim == 0)
            throw new IllegalStateException("Invalid recipe!");
        result = mapper;
        return this;
    }

    public RailRecipe withResult(ItemStack stack) {
        return withResult(mat -> stack.clone());
    }

    public RailRecipe withResult(String id, int amount) {
        return withResult(mat -> Rail.itemRegistry().create(id, amount));
    }

    public RailRecipe withResult(String id) {
        return withResult(id, 1);
    }

    public Predicate<ItemStack> ingredientAt(int x, int y) {
        if (x < 0 || x >= xDim || y < 0 || y >= yDim)
            return ItemUtils::isNully;
        return ingredients.getOrDefault(lines[y].charAt(x), ItemUtils::isNully);
    }

    @Override
    public ItemStack mapToResult(ItemStack[] mat) {
        return result.apply(mat);
    }

    @Override
    public ItemStack[] matches(ItemStack[] mat) {
        if (result == null)
            throw new IllegalStateException("Recipe is not finished!");
        for (int yOff = 0; yOff < 4 - yDim; yOff++) {
            xLoop:
            for (int xOff = 0; xOff < 4 - xDim; xOff++) {
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        if (!ingredientAt(x - xOff, y - yOff).test(mat[y * 3 + x]))
                            continue xLoop;
                    }
                }
                ItemStack[] match = new ItemStack[xDim * yDim];
                for (int y = 0; y < yDim; y++)
                    System.arraycopy(mat, (y + yOff) * 3 + xOff, match, y * xDim, xDim);
                return match;
            }
        }
        return null;
    }

}
