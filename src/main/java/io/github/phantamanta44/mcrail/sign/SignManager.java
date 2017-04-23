package io.github.phantamanta44.mcrail.sign;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.BlockPos;
import io.github.phantamanta44.mcrail.util.JsonUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongConsumer;

public class SignManager implements LongConsumer {

    private final Map<BlockPos, SignEntity> entities;

    public SignManager() {
        this.entities = new HashMap<>();
    }

    @Override
    public void accept(long tick) {
        entities.entrySet().stream()
                .filter(e -> e.getKey().exists())
                .map(Map.Entry::getValue)
                .forEach(SignEntity::tick);
    }

    public void onSignClick(PlayerInteractEvent event) {
        SignEntity se = entities.get(new BlockPos(event.getClickedBlock()));
        if (se != null)
            se.onInteract(event);
    }

    public boolean breakCheck(Block block) {
        SignEntity se = entities.remove(new BlockPos(block));
        if (se != null) {
            block.setType(Material.AIR);
            se.destroy();
            Collection<ItemStack> drops = block.getDrops();
            se.modifyDrops(drops);
            Location loc = block.getLocation().add(0.5D, 0.5D, 0.5D);
            drops.forEach(d -> block.getWorld().dropItemNaturally(loc, d));
            return true;
        }
        return false;
    }

    public void register(String id, String name, Block block) {
        SignEntity se = Rail.signRegistry().createEntity(id, name, block);
        se.init();
        entities.put(new BlockPos(block), se);
    }

    public void destroy(Block block) {
        SignEntity se = entities.remove(new BlockPos(block));
        if (se != null)
            se.destroy();
    }

    public void save(World world) {
        File file = new File(Rail.INSTANCE.getDataFolder(), "world_" + world.getName() + ".json");
        file.getParentFile().mkdirs();
        JsonArray dto = new JsonArray();
        entities.entrySet().stream()
                .filter(e -> e.getKey().world().equals(world))
                .map(e -> {
                    JsonObject dto2 = new JsonObject();
                    dto2.add("pos", e.getKey().serialize());
                    dto2.add("entity", e.getValue().serialize());
                    String header = e.getValue().id();
                    dto2.addProperty("id", header.substring(1, header.length() - 1));
                    return dto2;
                })
                .forEach(dto::add);
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            out.println(JsonUtils.GSON.toJson(dto));
        } catch (IOException e) {
            Rail.INSTANCE.getLogger().severe("Failed to save sign entity data in world: " + world.getName());
            e.printStackTrace();
        }
    }

    public void load(World world) {
        entities.entrySet().removeIf(e -> e.getKey().world().equals(world));
        File file = new File(Rail.INSTANCE.getDataFolder(), "world_" + world.getName() + ".json");
        if (file.exists()) {
            try (Reader in = new BufferedReader(new FileReader(file))) {
                JsonArray dto = JsonUtils.JSONP.parse(in).getAsJsonArray();
                dto.forEach(e -> {
                    JsonObject dto2 = e.getAsJsonObject();
                    BlockPos pos = BlockPos.deserialize(dto2.get("pos").getAsJsonObject());
                    SignEntity se = Rail.signRegistry().providerFor(dto2.get("id").getAsString()).apply(pos.block());
                    entities.put(pos, se);
                });
            } catch (IOException e) {
                Rail.INSTANCE.getLogger().severe("Failed to load sign entity data in world: " + world.getName());
                e.printStackTrace();
            }
        }
    }

    public SignEntity getAt(BlockPos pos) {
        return entities.get(pos);
    }

    public boolean existsAt(BlockPos pos) {
        return entities.containsKey(pos);
    }

}
