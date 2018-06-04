package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.oredict.OreDictionary;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RailShapelessRecipe implements IGridRecipe {

    private final List<Predicate<ItemStack>> ingredients;
    private Function<Collection<ItemStack>, ItemStack> result;
    
    public RailShapelessRecipe() {
        this.ingredients = new LinkedList<>();
        this.result = null;
    }
    
    public RailShapelessRecipe ingredient(Predicate<ItemStack> ing) {
        if (result != null)
            throw new IllegalStateException("This recipe is already finished!");
        ingredients.add(ing);
        return this;
    }

    public RailShapelessRecipe ingredient(Material ing) {
        return ing == Material.AIR
                ? ingredient(ItemUtils::isNully)
                : ingredient(ItemUtils.matching(ing));
    }

    public RailShapelessRecipe ingredient(MaterialData ing) {
        return ing.getItemType() == Material.AIR
                ? ingredient(ItemUtils::isNully)
                : ingredient(ItemUtils.matching(ing));
    }

    public RailShapelessRecipe ingredient(ItemStack ing) {
        return ingredient(ItemUtils.matching(ing));
    }

    public RailShapelessRecipe ingredient(String ing) {
        return ingredient(ItemUtils.matching(ing));
    }

    public RailShapelessRecipe ingOreDict(String id) {
        return ingredient(OreDictionary.predicate(id));
    }

    public RailShapelessRecipe withOutput(Function<Collection<ItemStack>, ItemStack> mapper) {
        if (result != null)
            throw new IllegalStateException("This recipe is already finished!");
        result = mapper;
        return this;
    }

    public RailShapelessRecipe withOutput(ItemStack stack) {
        return withOutput(in -> stack.clone());
    }

    public RailShapelessRecipe withOutput(String id, int amount) {
        return withOutput(in -> Rail.itemRegistry().create(id, amount));
    }

    public RailShapelessRecipe withOutput(String id) {
        return withOutput(id, 1);
    }
    
    @Override
    public ItemStack mapToResult(ItemStack[] mat) {
        return result.apply(Arrays.stream(mat).filter(ItemUtils::isNotNully).collect(Collectors.toList()));
    }

    @Override
    public ItemStack[] matches(ItemStack[] mat) {
        Collection<ItemStack> avail = Arrays.stream(mat).filter(ItemUtils::isNotNully).collect(Collectors.toList());
        return matches(avail, 0) ? avail.toArray(new ItemStack[avail.size()]) : null;
    }
    
    private boolean matches(Collection<ItemStack> avail, int index) {
        if (index >= ingredients.size())
            return avail.isEmpty();
        List<ItemStack> matches = avail.stream().filter(ingredients.get(index)).collect(Collectors.toList());
        for (ItemStack match : matches) {
            avail.remove(match);
            boolean restWorks = matches(avail, index + 1);
            avail.add(match);
            if (restWorks)
                return true;
        }
        return false;
    }
    
}