package ru.basher.utils.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@RequiredArgsConstructor
public abstract class Version {

    private final JavaPlugin plugin;
    private final IEntityUtil entityUtil;
    private final IItemUtil itemUtil;

}
