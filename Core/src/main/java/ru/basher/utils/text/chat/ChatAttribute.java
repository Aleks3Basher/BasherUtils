package ru.basher.utils.text.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public abstract class ChatAttribute {

    public abstract void apply(@NotNull BaseComponent component);

}
