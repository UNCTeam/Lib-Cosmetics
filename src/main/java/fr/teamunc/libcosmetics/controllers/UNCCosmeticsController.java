package fr.teamunc.libcosmetics.controllers;

import fr.teamunc.libcosmetics.CosmeticsLib;
import fr.teamunc.libcosmetics.models.PlayerCosmeticsContainer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Map;

public class UNCCosmeticsController {
    @Getter
    private PlayerCosmeticsContainer playerCosmeticsContainer;

    public UNCCosmeticsController(PlayerCosmeticsContainer playerCosmeticsContainer) {
        this.playerCosmeticsContainer = playerCosmeticsContainer;
        // Handle la rotation des armors stand sur les joueurs
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CosmeticsLib.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Map.Entry mapentry : playerCosmeticsContainer.getArmorStands().entrySet()) {
                    ArmorStand stand = (ArmorStand) mapentry.getValue();
                    Player player = (Player) mapentry.getKey();
                    stand.setRotation(player.getLocation().getYaw(),player.getLocation().getPitch());
                }
            }
        }, 0, 0);
    }

    public void equipCosmetic(Player player) {

    }


}
