package fr.teamunc.libcosmetics;

import fr.teamunc.libcosmetics.controllers.UNCCosmeticsController;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmeticsLib {
    @Getter
    private static JavaPlugin plugin;

    private static UNCCosmeticsController uncEntitiesController;

    public static void init(JavaPlugin plugin) {
        CosmeticsLib.plugin = plugin;
    }

}
