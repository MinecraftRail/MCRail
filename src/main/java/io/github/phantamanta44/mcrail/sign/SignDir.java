package io.github.phantamanta44.mcrail.sign;

import org.bukkit.block.BlockFace;
import org.bukkit.material.Sign;

public class SignDir {

    private final BlockFace facing, attached;

    public SignDir(Sign sign) {
        this.facing = sign.getFacing();
        this.attached = sign.getAttachedFace();
    }

    public BlockFace facing() {
        return facing;
    }

    public BlockFace attachedTo() {
        return attached;
    }

}
