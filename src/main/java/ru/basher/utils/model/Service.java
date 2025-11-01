package ru.basher.utils.model;

public interface Service {

    default void enable() {}
    default void reload() {}
    default void disable() {}

}
