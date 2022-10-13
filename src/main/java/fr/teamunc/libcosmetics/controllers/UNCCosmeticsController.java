package fr.teamunc.libcosmetics.controllers;

import fr.teamunc.libcosmetics.CosmeticsLib;
import fr.teamunc.libcosmetics.models.PlayerCosmeticsContainer;
import fr.teamunc.libcosmetics.models.cosmetic.Cosmetic;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UNCCosmeticsController {

    private HashMap<String, Cosmetic> customCosmeticMap;
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

    public void registerCosmetic(Cosmetic... cosmetics) {
        for (Cosmetic cosmetic : cosmetics) {
            if (customCosmeticMap.containsKey(cosmetic.getCosmeticKey())) {
                throw new IllegalArgumentException("Custom item with key " + cosmetic.getCosmeticKey() + " already exists");
            } else {
                customCosmeticMap.put(cosmetic.getCosmeticKey(), cosmetic);
            }
        }
    }

    public void equipCosmetic(String cosmeticKey, Player player) {
        if (customCosmeticMap.containsKey(cosmeticKey)) {
            Cosmetic cosmetic = customCosmeticMap.get(cosmeticKey);
            if (playerCosmeticsContainer.getArmorStands().containsKey(player)) {
                playerCosmeticsContainer.getArmorStands().get(player).remove();
            }
            return equipCosmetic(cosmetic, player);
        }
    }
    public void equipCosmetic(Cosmetic cosmetic, Player player) {
        Location playerLoc = player.getLocation();
        Location armorLoc = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY()-1, playerLoc.getZ());
        ArmorStand stand = (ArmorStand) player.getLocation().getWorld().spawnEntity(armorLoc, EntityType.ARMOR_STAND);

        stand.getEquipment().setHelmet(cosmetic.getItemStack());
        stand.setVisible(false);
        stand.setMarker(true);
        player.addPassenger(stand);

        this.playerCosmeticsContainer.getArmorStands().put(player, stand);
    }


}
