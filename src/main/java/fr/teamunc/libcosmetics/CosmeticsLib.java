package fr.teamunc.libcosmetics;

import fr.teamunc.libcosmetics.controllers.UNCCosmeticsController;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmeticsLib {
    private static JavaPlugin plugin;

    private static UNCCosmeticsController uncEntitiesController;

    public static void init(JavaPlugin plugin) {
        CosmeticsLib.plugin = plugin;
    }

}
