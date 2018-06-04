package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.IItemBehaviour;
import io.github.phantamanta44.mcrail.item.characteristic.CharDamage;
import io.github.phantamanta44.mcrail.oredict.OreDictionary;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Predicate;

public class RailSmeltRecipe {
    
    private Predicate<ItemStack> input;
    private Collection<Object> inputTypes;
    private Function<ItemStack, ItemStack> output;
    private int xp;
    
    public RailSmeltRecipe() {
        this.input = null;
        this.inputTypes = new LinkedList<>();
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
        inputTypes.add(type);
        return this;
    }

    public RailSmeltRecipe withInput(Predicate<ItemStack> ing, MaterialData type) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        input = ing;
        inputTypes.add(type);
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
        IItemBehaviour item = Rail.itemRegistry().get(ing);
        return item.characteristics().stream()
                .filter(c -> c instanceof CharDamage)
                .findAny()
                .map(c -> withInput(
                        ItemUtils.matching(ing),
                        new MaterialData(item.material(), (byte)((CharDamage)c).value())))
                .orElseGet(() -> withInput(ItemUtils.matching(ing), item.material()));
    }

    public RailSmeltRecipe withInputOreDict(String id) {
        if (output != null)
            throw new IllegalStateException("This recipe is already finished!");
        input = OreDictionary.predicate(id);
        Rail.itemRegistry().stream()
                .map(e -> Rail.itemRegistry().create(e.getKey()))
                .filter(is -> OreDictionary.matches(id, is))
                .forEach(is -> inputTypes.add(is.getData()));
        for (Material mat : Material.values()) {
            ItemStack stack = new ItemStack(mat);
            for (byte i = 0; i < 16; i++) {
                stack.getData().setData(i);
                if (OreDictionary.matches(id, stack))
                    inputTypes.add(stack.getData());
            }
        }
        return this;
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
        return this.input.test(input);
    }

    public ItemStack mapToResult(ItemStack input) {
        return output.apply(input);
    }

    public int xp() {
        return xp;
    }

    void registerBukkit() {
        for (Object inputType : inputTypes) {
            if (inputType instanceof Material)
                Bukkit.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.STONE), (Material)inputType));
            else
                Bukkit.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.STONE), (MaterialData)inputType));
        }
    }

}
