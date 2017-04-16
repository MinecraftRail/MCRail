package io.github.phantamanta44.mcrail.fluid;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class FluidBucketAdapter implements Function<ItemStack, IFluidContainer> {

    @Override
    public IFluidContainer apply(ItemStack stack) {
        FluidType type = Rail.fluidRegistry().byBucket(stack);
        return type == null ? null : new BucketWrapper(type);
    }

    private static class BucketWrapper implements IFluidContainer {

        private final FluidType type;

        public BucketWrapper(FluidType type) {
            this.type = type;
        }

        @Override
        public FluidType type() {
            return type;
        }

        @Override
        public int amount() {
            return 1000;
        }

        @Override
        public int capacity() {
            return 1000;
        }

    }


}
