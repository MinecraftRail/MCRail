package io.github.phantamanta44.mcrail.util;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.sign.SignEntity;
import org.bukkit.block.Block;

public class SignUtils {

    public static SignEntity getAt(BlockPos pos) {
        return Rail.signManager().getAt(pos);
    }

    public static SignEntity getAt(Block block) {
        return getAt(new BlockPos(block));
    }

    public static boolean existsAt(BlockPos pos) {
        return Rail.signManager().existsAt(pos);
    }

    public static boolean existsAt(Block block) {
        return existsAt(new BlockPos(block));
    }

}
