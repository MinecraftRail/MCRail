package io.github.phantamanta44.mcrail.tile;

import io.github.phantamanta44.mcrail.util.BlockPos;
import io.github.phantamanta44.mcrail.util.Lines;
import io.github.phantamanta44.mcrail.util.SignDir;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sign;

import java.util.Collection;

public abstract class SignEntity {

    private final BlockPos pos;
    private final Lines lines;
    private final SignDir dir;
    private final boolean isWallSign;

    public SignEntity(Block block) {
         this.pos = new BlockPos(block);
         BlockState state = block.getState();
         this.lines = new Lines((org.bukkit.block.Sign)state);
         Sign data = (Sign)state.getData();
         this.dir = new SignDir(data);
         this.isWallSign = data.isWallSign();
    }

    public BlockPos pos() {
        return pos;
    }

    public Block block() {
        return pos.block();
    }

    public Location location() {
        return block().getLocation();
    }

    public Lines lines() {
        return lines;
    }

    public SignDir dir() {
        return dir;
    }

    public boolean isWallSign() {
        return isWallSign;
    }

    public abstract void init();

    public void destroy() {
        // NO-OP
    }

    public void modifyDrops(Collection<ItemStack> drops) {
        // NO-OP
    }

    public void tick() {
        // NO-OP
    }

    public void onInteract(PlayerInteractEvent event) {
        // NO-OP
    }

}
