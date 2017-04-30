package io.github.phantamanta44.mcrail.util;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.sign.SignEntity;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Objects;
import java.util.stream.Stream;

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

    public static SignEntity getAdj(BlockPos pos, BlockFace face) {
        return getAt(pos.add(face.getModX(), face.getModY(), face.getModZ()));
    }

    public static Stream<SignEntity> adjacent(BlockPos pos) {
        return Stream.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN)
                .map(f -> getAdj(pos, f))
                .filter(Objects::nonNull);
    }

    public static String formatName(String name) {
        if (name.length() < 1)
            return name;
        int[] charArr = name.chars().map(c -> c == '_' ? ' ' : Character.toLowerCase(c)).toArray();
        charArr[0] = Character.toUpperCase(charArr[0]);
        for (int i = 0; i < charArr.length - 1; i++) {
            if (!Character.isAlphabetic(charArr[i]))
                charArr[i + 1] = Character.toUpperCase(charArr[i + 1]);
        }
        return new String(charArr, 0, charArr.length);
    }

}
