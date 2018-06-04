package io.github.phantamanta44.mcrail.module;

public interface IRailModule {

    default void phasePreConference() {
        // NO-OP
    }

    default void phaseSetup() {
        // NO-OP
    }

    default void phaseRegistration() {
        // NO-OP
    }

    default void phasePostRegistration() {
        // NO-OP
    }

    default void phasePostConference() {
        // NO-OP
    }

}
