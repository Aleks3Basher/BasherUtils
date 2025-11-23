package ru.basher.utils.text.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class CommandAttribute extends ChatAttribute {

    private final String command;

    @Override
    public void apply(@NotNull BaseComponent component) {
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command);
        for(BaseComponent comp : component.getExtra())
            comp.setClickEvent(clickEvent);
    }
}
