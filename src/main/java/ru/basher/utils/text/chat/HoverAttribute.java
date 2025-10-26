package ru.basher.utils.text.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class HoverAttribute extends ChatAttribute {

    private final String hover;

    @Override
    public void apply(@NotNull BaseComponent component) {
        TextComponent hoverComp = ChatMessage.fromLegacy(hover);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new BaseComponent[]{hoverComp}));
        for(BaseComponent comp : component.getExtra())
            comp.setHoverEvent(hoverEvent);
    }

}
