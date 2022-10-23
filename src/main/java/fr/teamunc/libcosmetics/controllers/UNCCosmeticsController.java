package fr.teamunc.libcosmetics.controllers;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.libcosmetics.CosmeticsLib;
import fr.teamunc.libcosmetics.models.PlayerCosmeticsContainer;
import fr.teamunc.libcosmetics.models.cosmetic.Cosmetic;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class UNCCosmeticsController {

    private HashMap<String, Cosmetic> customCosmeticMap = new HashMap<>();
    @Getter
    private PlayerCosmeticsContainer playerCosmeticsContainer;

    public UNCCosmeticsController(PlayerCosmeticsContainer playerCosmeticsContainer) {
        this.playerCosmeticsContainer = playerCosmeticsContainer;
        // Handle la rotation des armors stand sur les joueurs
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CosmeticsLib.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID, ArmorStand> mapentry : playerCosmeticsContainer.getArmorStands().entrySet()) {
                    ArmorStand stand = mapentry.getValue();
                    Player player = Bukkit.getPlayer(mapentry.getKey());
                    stand.setRotation(player.getLocation().getYaw(),player.getLocation().getPitch());
                }
            }
        }, 0, 0);
    }

    public void addCosmeticToPlayer(String cosmeticName, Player player) {
        if (customCosmeticMap.containsKey(cosmeticName)) {
            playerCosmeticsContainer.getOwnedPlayerCosmetics().get(player.getUniqueId()).add(customCosmeticMap.get(cosmeticName));
        }
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

    public void removeCosmetic(String cosmeticKey, Player player) {
        if (customCosmeticMap.containsKey(cosmeticKey) && playerCosmeticsContainer.getArmorStands().containsKey(player.getUniqueId())) {
            Cosmetic cosmetic = customCosmeticMap.get(cosmeticKey);
            ArmorStand stand = playerCosmeticsContainer.getArmorStands().get(player.getUniqueId());
            switch (cosmetic.getCosmeticType()) {
                case Back:
                    playerCosmeticsContainer.getBackCosmetics().remove(player.getUniqueId());
                    stand.getEquipment().setChestplate(null);
                    break;
                case Hat:
                    playerCosmeticsContainer.getHatCosmetics().remove(player.getUniqueId());
                    stand.getEquipment().setHelmet(null);
                    break;
                case Hand:
                    playerCosmeticsContainer.getHandCosmetics().remove(player.getUniqueId());
                    stand.getEquipment().setItemInMainHand(null);
                    break;
            }
        }
    }

    public void equipCosmetic(String cosmeticKey, Player player) {
        if (customCosmeticMap.containsKey(cosmeticKey)) {
            Cosmetic cosmetic = customCosmeticMap.get(cosmeticKey);
            if((playerCosmeticsContainer.getOwnedPlayerCosmetics().get(player.getUniqueId()) != null
                    && playerCosmeticsContainer.getOwnedPlayerCosmetics().get(player.getUniqueId()).contains(cosmetic)) || player.hasPermission("unc.admin")) {
                equipCosmetic(cosmetic, player);
            } else {
                Message.Get().sendMessage("You don't have this cosmetic yet", player, true);
            }
        }
    }
    public void equipCosmetic(Cosmetic cosmetic, Player player) {
        Location playerLoc = player.getLocation();
        Location armorLoc = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY()-1, playerLoc.getZ());
        ArmorStand stand;
        if (playerCosmeticsContainer.getArmorStands().containsKey(player.getUniqueId())) {
            stand = playerCosmeticsContainer.getArmorStands().get(player.getUniqueId());
        } else {
            stand = (ArmorStand) player.getLocation().getWorld().spawnEntity(armorLoc, EntityType.ARMOR_STAND);
        }

        switch (cosmetic.getCosmeticType()) {
            case Back:
                stand.getEquipment().setHelmet(cosmetic.getItemStack());
                this.playerCosmeticsContainer.getBackCosmetics().put(player.getUniqueId(), cosmetic);
                break;
            case Hat:
                stand.getEquipment().setChestplate(cosmetic.getItemStack());
                this.playerCosmeticsContainer.getHatCosmetics().put(player.getUniqueId(), cosmetic);
                break;
            case Hand:
                stand.getEquipment().setItemInMainHand(cosmetic.getItemStack());
                this.playerCosmeticsContainer.getHandCosmetics().put(player.getUniqueId(), cosmetic);
                break;
        }
        stand.setVisible(false);
        stand.setGlowing(true);
        player.addPassenger(stand);

        this.playerCosmeticsContainer.getArmorStands().put(player.getUniqueId(), stand);
    }


    public Collection<Cosmetic> getRegisteredCosmetics() {
        return this.customCosmeticMap.values();
    }
}
