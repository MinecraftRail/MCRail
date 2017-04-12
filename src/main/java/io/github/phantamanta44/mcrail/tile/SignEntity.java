package io.github.phantamanta44.mcrail.tile;

import io.github.phantamanta44.mcrail.util.Lines;
import io.github.phantamanta44.mcrail.util.SignDir;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.Sign;

public abstract class SignEntity {

    private final Block block;
    private final Lines lines;
    private final SignDir dir;
    private final boolean isWallSign;

    public SignEntity(Block block) {
         this.block = block;
         BlockState state = block.getState();
         this.lines = new Lines((org.bukkit.block.Sign)state);
         Sign data = (Sign)state.getData();
         this.dir = new SignDir(data);
         this.isWallSign = data.isWallSign();
    }

    public Block block() {
        return block;
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

    public abstract void destroy();

    public abstract void tick();

}
