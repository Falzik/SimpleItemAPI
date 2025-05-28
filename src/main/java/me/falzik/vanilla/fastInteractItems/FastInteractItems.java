package me.falzik.vanilla.fastInteractItems;

import me.falzik.vanilla.fastInteractItems.listeners.ItemListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FastInteractItems extends JavaPlugin {

    private static FastInteractItems instance;

    public static FastInteractItems getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
