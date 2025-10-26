package ru.basher.utils.text.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class ChatMessage {

    private TextComponent textComponent;

    public @NotNull ChatMessage append(@NotNull String text, @NotNull ChatAttribute...attributes) {
        TextComponent textComponent = fromLegacy(text);
        for (ChatAttribute attribute : attributes) attribute.apply(textComponent);

        if(this.textComponent == null) this.textComponent = textComponent;
        else this.textComponent.addExtra(textComponent);
        return this;
    }

    public @NotNull ChatMessage set(@NotNull String text, @NotNull ChatAttribute...attributes) {
        TextComponent textComponent = fromLegacy(text);
        for (ChatAttribute attribute : attributes) attribute.apply(textComponent);

        this.textComponent = textComponent;
        return this;
    }

    public @NotNull TextComponent toComponent() {
        return textComponent;
    }

    public static @NotNull TextComponent fromLegacy(@NotNull String message) {
        TextComponent comp = new TextComponent();
        BaseComponent[] comps = TextComponent.fromLegacyText(message);
        BaseComponent current = comp;
        for (BaseComponent base : comps) {
            current.addExtra(base);
            current = base;
        }
        return comp;
    }
}
