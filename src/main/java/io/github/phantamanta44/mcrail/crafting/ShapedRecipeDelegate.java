package io.github.phantamanta44.mcrail.crafting;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ShapedRecipeDelegate extends ShapedRecipe {

    private final Predicate[] grid;

    public ShapedRecipeDelegate(RailRecipe recipe) {
        super(recipe.result);
        this.grid = Arrays.stream(recipe.lines)
                .limit(3)
                .filter(Objects::nonNull)
                .flatMap(l -> processLine(l, recipe.ingredients))
                .toArray(Predicate[]::new);
        // TODO Set shape and ingredients
    }

    private Stream<Predicate<ItemStack>> processLine(String line, Map<Character, Predicate<ItemStack>> ingredients) {
        return line.chars().limit(3).mapToObj(c -> ingredients.get((char)c));
    }

}
