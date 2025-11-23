package ru.basher.utils.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum TextColor {

    BLACK("0"),
    DARK_BLUE("1"),
    DARK_GREEN("2"),
    DARK_AQUA("3"),
    DARK_RED("4"),
    DARK_PURPLE("5"),
    GOLD("6"),
    GRAY("7"),
    DARK_GRAY("8"),
    BLUE("9"),
    GREEN("a"),
    AQUA("b"),
    RED("c"),
    LIGHT_PURPLE("d"),
    YELLOW("e"),
    WHITE("f"),
    HEX("f");

    private final String charId;

    private static final Map<String, TextColor> BY_NAME = new HashMap<>();
    static {
        for(TextColor value : values()) {
            BY_NAME.put(value.name(), value);
        }
    }

    public static @Nullable TextColor getByName(@Nullable String name) {
        return getByName(name, null);
    }

    public static TextColor getByName(@Nullable String name, TextColor def) {
        if (name == null) {
            return def;
        } else {
            TextColor founded = BY_NAME.get(name.toUpperCase());
            return founded == null ? def : founded;
        }
    }
}
