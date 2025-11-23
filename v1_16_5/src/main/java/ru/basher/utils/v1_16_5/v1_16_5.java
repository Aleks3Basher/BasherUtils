package ru.basher.utils.v1_16_5;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.api.IEntityUtil;
import ru.basher.utils.api.IItemUtil;
import ru.basher.utils.api.Version;

public class v1_16_5 extends Version {

    private v1_16_5(JavaPlugin plugin, IEntityUtil entityUtil, IItemUtil itemUtil) {
        super(plugin, entityUtil, itemUtil);
    }

    public static @NotNull Version newInstance(@NotNull JavaPlugin plugin) {
        IEntityUtil entityUtil = new rEntityUtil(plugin);
        IItemUtil itemUtil = new rItemUtil(plugin);
        return new v1_16_5(plugin, entityUtil, itemUtil);
    }

}
