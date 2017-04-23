package io.github.phantamanta44.mcrail.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class JsonUtils {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final JsonParser JSONP = new JsonParser();

    public static JsonElement serItemStack(ItemStack stack) {
        if (ItemUtils.isNully(stack))
            return JsonNull.INSTANCE;
        JsonObject dto = new JsonObject();
        dto.addProperty("type", stack.getType().name());
        dto.addProperty("damage", stack.getDurability());
        dto.addProperty("amount", stack.getAmount());
        if (stack.hasItemMeta()) {
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            dto.add("meta", GSON.toJsonTree(stack.getItemMeta().serialize(), mapType));
        }
        return dto;
    }

    @SuppressWarnings("unchecked")
    public static ItemStack deserItemStack(JsonElement json) {
        if (json == null || json.isJsonNull())
            return null;
        JsonObject dto = json.getAsJsonObject();
        ItemStack stack = new ItemStack(
                Material.valueOf(dto.get("type").getAsString()),
                dto.get("amount").getAsInt(),
                dto.get("damage").getAsShort());
        if (dto.has("meta")) {
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            ItemMeta meta = (ItemMeta)ConfigurationSerialization.deserializeObject(
                    dblHack(GSON.fromJson(dto.get("meta"), mapType)), ConfigurationSerialization.getClassByAlias("ItemMeta"));
            stack.setItemMeta(meta);
        }
        return stack;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> dblHack(Map<String, Object> map) {
        map.replaceAll((k, v) -> {
            if (v instanceof Number) {
                Number num = (Number)v;
                if (Math.ceil(num.doubleValue()) == num.doubleValue())
                    return num.intValue();
                else
                    return num.doubleValue();
            } else if (v instanceof Map) {
                return dblHack((Map)v);
            }
            return v;
        });
        return map;
    }

    public static Collector<JsonElement, ?, JsonArray> arrayCollector() {
        return new Collector<JsonElement, JsonArray, JsonArray>() {
            @Override
            public Supplier<JsonArray> supplier() {
                return JsonArray::new;
            }

            @Override
            public BiConsumer<JsonArray, JsonElement> accumulator() {
                return (a, o) -> a.add(o != null ? o : JsonNull.INSTANCE);
            }

            @Override
            public BinaryOperator<JsonArray> combiner() {
                return (a, b) -> {
                    a.addAll(b);
                    return a;
                };
            }

            @Override
            public Function<JsonArray, JsonArray> finisher() {
                return x -> x;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.emptySet();
            }
        };
    }

}