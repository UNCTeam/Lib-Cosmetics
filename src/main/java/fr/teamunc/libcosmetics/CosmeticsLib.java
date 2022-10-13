package fr.teamunc.libcosmetics;

import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.libcosmetics.minecraft.commandsExecutor.CosmeticsCommands;
import fr.teamunc.libcosmetics.controllers.UNCCosmeticsController;
import fr.teamunc.libcosmetics.minecraft.eventListeners.InventoryListener;
import fr.teamunc.libcosmetics.models.PlayerCosmeticsContainer;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmeticsLib {
    @Getter
    private static JavaPlugin plugin;

    @Getter
    private static UNCCosmeticsController uncCosmeticsController;

    public static void init(JavaPlugin plugin) {
        CosmeticsLib.plugin = plugin;
        uncCosmeticsController = new UNCCosmeticsController(CosmeticsLib.initCosmeticContainer());
    }

    private static void initCommands() {
        PluginCommand teamCommand = plugin.getCommand("uncitem");
        if (teamCommand != null) {
            teamCommand.setExecutor(new CosmeticsCommands());
        }
    }

    private static PlayerCosmeticsContainer initCosmeticContainer() {
        UNCEntitiesContainer.init(plugin.getDataFolder());
        PlayerCosmeticsContainer res;

        try {
            res = UNCEntitiesContainer.loadContainer("cosmetics", PlayerCosmeticsContainer.class);
        } catch (Exception e) {
            plugin.getLogger().info("Creating new cosmetic container file");
            res = new PlayerCosmeticsContainer();
        }
        return res;
    }

    public static void initGameListeners(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);
    }

    public static void reload() {
        // TODO : reload le fichier de cosmetics ?
    }

    public static boolean isInit() {
        return plugin != null;
    }
}
