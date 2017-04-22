package io.github.phantamanta44.mcrail.model;

import io.github.phantamanta44.mcrail.fluid.FluidType;

public interface IFluidProvider {

    int request(FluidType type, int amount);

    boolean contains(FluidType type, int amount);

    int quantity(FluidType type);

}
