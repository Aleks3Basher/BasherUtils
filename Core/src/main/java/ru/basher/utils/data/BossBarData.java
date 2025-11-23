package ru.basher.utils.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.text.TextUtil;

@Getter
@RequiredArgsConstructor
public class BossBarData {
    private final String title;
    private final BarColor color;
    private final BarStyle style;

    public BossBarData() {
        title = "";
        color = BarColor.WHITE;
        style = BarStyle.SOLID;
    }

    public BossBarData(@NotNull ConfigurationSection section) {
        this.title = TextUtil.toColor(section.getString("name"));
        this.color = BarColor.valueOf(section.getString("color", "BLUE"));
        this.style = BarStyle.valueOf(section.getString("style", "SEGMENTED_6"));
    }

}