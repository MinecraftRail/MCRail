package io.github.phantamanta44.mcrail.tile;

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
import java.util.LinkedList;
import java.util.Map;
import java.util.function.LongConsumer;

public class RailTileManager implements LongConsumer {

    private final Map<BlockPos, RailTile> entities;

    public RailTileManager() {
        this.entities = new HashMap<>();
    }

    @Override
    public void accept(long tick) {
        entities.entrySet().stream()
                .filter(e -> e.getKey().exists())
                .map(Map.Entry::getValue)
                .forEach(RailTile::tick);
    }

    public void onTileClick(PlayerInteractEvent event) {
        RailTile tile = entities.get(new BlockPos(event.getClickedBlock()));
        if (tile != null)
            tile.onInteract(event);
    }

    public boolean breakCheck(Block block) {
        RailTile tile = entities.remove(new BlockPos(block));
        if (tile != null) {
            tile.destroy();
            Collection<ItemStack> drops = new LinkedList<>();
            drops.add(Rail.itemRegistry().create(tile.id()));
            tile.modifyDrops(drops);
            Location loc = block.getLocation().add(0.5D, 0.5D, 0.5D);
            block.setType(Material.AIR);
            drops.forEach(d -> block.getWorld().dropItemNaturally(loc, d));
            return true;
        }
        return false;
    }

    public void register(String id, String name, Block block) {
        RailTile tile = Rail.tileRegistry().createEntity(id, name, block);
        tile.init();
        entities.put(new BlockPos(block), tile);
    }

    public void destroy(Block block) {
        RailTile tile = entities.remove(new BlockPos(block));
        if (tile != null)
            tile.destroy();
    }

    public void save(World world) {
        Rail.INSTANCE.getLogger().info("Saving world data: " + world.getName());
        File file = new File(Rail.INSTANCE.getDataFolder(), "world_" + world.getName() + ".json");
        file.getParentFile().mkdirs();
        JsonArray dto = new JsonArray();
        entities.entrySet().stream()
                .filter(e -> e.getKey().world().equals(world))
                .map(e -> {
                    JsonObject dto2 = new JsonObject();
                    dto2.add("pos", e.getKey().serialize());
                    dto2.add("entity", e.getValue().serialize());
                    dto2.addProperty("id", e.getValue().id());
                    return dto2;
                })
                .forEach(dto::add);
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            out.println(JsonUtils.GSON.toJson(dto));
        } catch (IOException e) {
            Rail.INSTANCE.getLogger().severe("Failed to save Rail tile data in world: " + world.getName());
            e.printStackTrace();
        }
    }

    public void load(World world) {
        Rail.INSTANCE.getLogger().info("Loading world data: " + world.getName());
        entities.entrySet().removeIf(e -> e.getKey().world().equals(world));
        File file = new File(Rail.INSTANCE.getDataFolder(), "world_" + world.getName() + ".json");
        if (file.exists()) {
            try (Reader in = new BufferedReader(new FileReader(file))) {
                JsonArray dto = JsonUtils.JSONP.parse(in).getAsJsonArray();
                dto.forEach(e -> {
                    try {
                        JsonObject dto2 = e.getAsJsonObject();
                        BlockPos pos = BlockPos.deserialize(dto2.get("pos").getAsJsonObject());
                        RailTile tile = Rail.tileRegistry().providerFor(dto2.get("id").getAsString()).apply(pos.block());
                        tile.deserialize(dto2.get("entity").getAsJsonObject());
                        entities.put(pos, tile);
                    } catch (Exception e2) {
                        Rail.INSTANCE.getLogger().severe("Rail tile failed to load");
                        e2.printStackTrace();
                    }
                });
            } catch (IOException e) {
                Rail.INSTANCE.getLogger().severe("Failed to load Rail tile data in world: " + world.getName());
                e.printStackTrace();
            }
        }
    }

    public RailTile getAt(BlockPos pos) {
        return entities.get(pos);
    }

    public boolean existsAt(BlockPos pos) {
        return entities.containsKey(pos);
    }

}
