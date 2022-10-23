package fr.teamunc.libcosmetics;

import org.bukkit.plugin.java.JavaPlugin;

public final class cosmetics_UNCLib extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CosmeticsLib.initGameListeners(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
