package fr.teamunc.libcosmetics;

import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.libcosmetics.minecraft.commandsExecutor.CosmeticsCommands;
import fr.teamunc.libcosmetics.controllers.UNCCosmeticsController;
import fr.teamunc.libcosmetics.minecraft.eventListeners.InventoryListener;
import fr.teamunc.libcosmetics.minecraft.eventListeners.PlayerJoinQuitListener;
import fr.teamunc.libcosmetics.models.PlayerCosmeticsContainer;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;


public class CosmeticsLib {
    @Getter
    private static JavaPlugin plugin;

    @Getter
    private static UNCCosmeticsController uncCosmeticsController;

    private static Material cosmeticMaterialMapper;

    public static void init(JavaPlugin plugin, Material cosmeticItem) {
        CosmeticsLib.plugin = plugin;
        CosmeticsLib.cosmeticMaterialMapper = cosmeticItem;
        uncCosmeticsController = new UNCCosmeticsController(CosmeticsLib.initCosmeticContainer());
        initCommands();
        initCosmeticContainer();
    }

    private static void initCommands() {
        PluginCommand cosmeticCommand = plugin.getCommand("cosmetic");
        if (cosmeticCommand != null) {
            cosmeticCommand.setExecutor(new CosmeticsCommands());
            cosmeticCommand.setTabCompleter(new CosmeticsCommands());
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
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), plugin);
    }

    public static void reload() {
        // TODO : reload le fichier de cosmetics ?
    }

    public static boolean isInit() {
        return plugin != null;
    }
}
