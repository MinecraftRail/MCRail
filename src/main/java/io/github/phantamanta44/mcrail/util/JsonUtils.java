package io.github.phantamanta44.mcrail.util;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class JsonUtils {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final JsonParser JSONP = new JsonParser();

    public static JsonElement serItemStack(ItemStack stack) {
        return ItemUtils.isNotNully(stack)
                ? GSON.toJsonTree(stack.serialize(), Map.class)
                : JsonNull.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public static ItemStack deserItemStack(JsonElement dto) {
        return dto.isJsonNull() ? null : ItemStack.deserialize(GSON.fromJson(dto, Map.class));
    }

}
