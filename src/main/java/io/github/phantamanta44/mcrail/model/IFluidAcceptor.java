package io.github.phantamanta44.mcrail.model;

import io.github.phantamanta44.mcrail.fluid.FluidType;

public interface IFluidAcceptor {

    int offer(FluidType type, int amount);

    boolean hasSpace(FluidType type, int amount);

    int space(FluidType type, int amount);

}
