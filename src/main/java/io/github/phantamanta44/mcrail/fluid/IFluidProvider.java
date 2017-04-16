package io.github.phantamanta44.mcrail.fluid;

public interface IFluidProvider {

    int request(FluidType filter, int amount);

    boolean contains(FluidType filter, int amount);

    int quantity(FluidType filter);

}
