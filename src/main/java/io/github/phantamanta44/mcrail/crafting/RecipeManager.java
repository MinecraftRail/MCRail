package io.github.phantamanta44.mcrail.crafting;

import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class RecipeManager {

    private final Collection<RailRecipe> registry;

    public RecipeManager() {
        this.registry = new HashSet<>();
    }

    public void register(RailRecipe recipe) {
        registry.add(recipe);
    }

    public ItemStack recipeCheck(ItemStack[] mat) {
        return registry.stream()
                .map(r -> {
                    ItemStack[] match = r.matches(mat);
                    return match == null ? null : r.result.apply(match);
                })
                .filter(Objects::nonNull)
                .findAny().orElse(null);
    }

}
