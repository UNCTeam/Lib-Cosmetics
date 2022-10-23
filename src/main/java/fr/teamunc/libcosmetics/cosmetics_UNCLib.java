package fr.teamunc.libcosmetics;

import com.google.gson.GsonBuilder;
import fr.teamunc.libcosmetics.models.PlayerCosmeticsContainer;
import org.bukkit.plugin.java.JavaPlugin;

public final class cosmetics_UNCLib extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CosmeticsLib.initGameListeners(this);
    }

    @Override
    public void onDisable() {
        CosmeticsLib.getUncCosmeticsController().getPlayerCosmeticsContainer().save("cosmetics");
    }
}
