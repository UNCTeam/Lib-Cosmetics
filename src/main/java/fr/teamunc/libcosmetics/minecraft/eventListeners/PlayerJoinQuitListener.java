package fr.teamunc.libcosmetics.minecraft.eventListeners;

import fr.teamunc.libcosmetics.CosmeticsLib;
import fr.teamunc.libcosmetics.models.PlayerCosmeticsContainer;
import fr.teamunc.libcosmetics.models.cosmetic.Cosmetic;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(CosmeticsLib.getUncCosmeticsController().getPlayerCosmeticsContainer().getOwnedPlayerCosmetics().get(event.getPlayer().getUniqueId()) == null) {
            CosmeticsLib.getUncCosmeticsController().getPlayerCosmeticsContainer().getOwnedPlayerCosmetics().put(event.getPlayer().getUniqueId(), new ArrayList<>());
        }
        if(CosmeticsLib.getUncCosmeticsController().getPlayerCosmeticsContainer().getOwnedPlayerCosmetics().containsKey(event.getPlayer().getUniqueId())) {
            // ajout de l'armorstand dans MC
            PlayerCosmeticsContainer container = CosmeticsLib.getUncCosmeticsController().getPlayerCosmeticsContainer();
            List<String> equipedCosmetics = new ArrayList<>(Arrays.asList(
                    container.getHatCosmetics().get(event.getPlayer().getUniqueId()),
                    container.getBackCosmetics().get(event.getPlayer().getUniqueId()),
                    container.getHandCosmetics().get(event.getPlayer().getUniqueId()))
            );
            for(String cosmetic : equipedCosmetics) {
                CosmeticsLib.getUncCosmeticsController().equipCosmetic(cosmetic, event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPLayerLeave(PlayerQuitEvent event) {
        if(CosmeticsLib.getUncCosmeticsController().getArmorStands().containsKey(event.getPlayer().getUniqueId())) {
            // Supression de l'armorstand dans MC
            ArmorStand stand = CosmeticsLib.getUncCosmeticsController().getArmorStands().get(event.getPlayer().getUniqueId());
            event.getPlayer().removePassenger(stand);
            CosmeticsLib.getUncCosmeticsController().getArmorStands().remove(event.getPlayer().getUniqueId());
            stand.remove();
        }}
}
