package ru.basher.utils.v1_20_4;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.api.IEntityUtil;
import ru.basher.utils.api.IItemUtil;
import ru.basher.utils.api.Version;

public class v1_20_4 extends Version {

    private v1_20_4(JavaPlugin plugin, IEntityUtil entityUtil, IItemUtil itemUtil) {
        super(plugin, entityUtil, itemUtil);
    }

    public static @NotNull Version newInstance(@NotNull JavaPlugin plugin) {
        IEntityUtil entityUtil = new rEntityUtil(plugin);
        IItemUtil itemUtil = new rItemUtil(plugin);
        return new v1_20_4(plugin, entityUtil, itemUtil);
    }

}
