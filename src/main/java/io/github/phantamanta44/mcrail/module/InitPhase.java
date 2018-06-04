package io.github.phantamanta44.mcrail.module;

import java.util.function.Consumer;

public enum InitPhase {

    NOT_INITIALIZED,
    PRE_CONFERENCE(IRailModule::phasePreConference),
    SETUP(IRailModule::phaseSetup),
    REGISTRATION(IRailModule::phaseRegistration),
    POST_REGISTRATION(IRailModule::phasePostRegistration),
    POST_CONFERENCE(IRailModule::phasePostConference),
    LOADING_DATA,
    INITIALIZED;

    private final Consumer<IRailModule> action;

    InitPhase(Consumer<IRailModule> action) {
        this.action = action;
    }

    InitPhase() {
        this(null);
    }

    public void act(IRailModule module) {
        action.accept(module);
    }

}
