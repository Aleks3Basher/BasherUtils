package ru.basher.utils;

public interface Service {

    default void enable() {}
    default void reload() {}
    default void disable() {}
}
