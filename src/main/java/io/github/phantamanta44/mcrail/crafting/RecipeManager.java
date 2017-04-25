package io.github.phantamanta44.mcrail.crafting;

import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class RecipeManager {

    private final Collection<IGridRecipe> recipeReg;
    private final Collection<RailSmeltRecipe> smeltingReg;

    public RecipeManager() {
        this.recipeReg = new HashSet<>();
        this.smeltingReg = new HashSet<>();
    }

    public void register(IGridRecipe recipe) {
        recipeReg.add(recipe);
    }

    public void register(RailSmeltRecipe recipe) {
        smeltingReg.add(recipe);
        recipe.registerBukkit();
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

}
