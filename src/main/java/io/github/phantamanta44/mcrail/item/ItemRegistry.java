package io.github.phantamanta44.mcrail.item;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ItemRegistry {

    private final Map<String, IItemBehaviour> registry;

    public ItemRegistry() {
        this.registry = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public void register(String id, IItemBehaviour item) {
        registry.put(id, item);
    }

    public IItemBehaviour get(String id) {
        return registry.get(id);
    }

    public IItemBehaviour get(ItemStack stack) {
        return registry.values().stream()
                .filter(i -> i.material() == stack.getType())
                .filter(i -> i.characteristics().stream().allMatch(c -> c.matches(stack)))
                .findAny()
                .orElse(null);
    }

    public boolean exists(String id) {
        return registry.containsKey(id);
    }

    public ItemStack create(String id, int size) {
        IItemBehaviour item = get(id);
        if (item != null) {
            ItemStack stack = new ItemStack(item.material());
            item.characteristics().forEach(c -> c.apply(stack));
            stack.setAmount(size);
            return stack;
        }
        return null;
    }

    public ItemStack create(String id) {
        return create(id, 1);
    }

    public Stream<Map.Entry<String, IItemBehaviour>> stream() {
        return registry.entrySet().stream();
    }

}
