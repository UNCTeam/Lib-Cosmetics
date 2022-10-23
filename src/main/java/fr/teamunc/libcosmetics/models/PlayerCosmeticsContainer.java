package fr.teamunc.libcosmetics.models;

import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.libcosmetics.models.cosmetic.Cosmetic;
import lombok.Getter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerCosmeticsContainer extends UNCEntitiesContainer {

    /**
    Contient la liste de cosmétiques qu'un joueur a équipé sur lui
     */
    @Getter
    private HashMap<UUID, String> hatCosmetics = new HashMap<>();
    @Getter
    private HashMap<UUID, String> backCosmetics = new HashMap<>();
    @Getter
    private HashMap<UUID, String> handCosmetics = new HashMap<>();

    @Getter
    private HashMap<UUID, List<String>> ownedPlayerCosmetics = new HashMap<>();

}
