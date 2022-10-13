package fr.teamunc.libcosmetics.models;

import fr.teamunc.base_unclib.models.jsonEntities.UNCEntitiesContainer;
import fr.teamunc.libcosmetics.models.cosmetic.Cosmetic;
import lombok.Getter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerCosmeticsContainer extends UNCEntitiesContainer {

    /**
    Contient la liste de cosmétiques qu'un joueur peut équiper
     */
    @Getter
    private HashMap<Player, ArrayList<Cosmetic>> cosmetics = new HashMap<>();

    /**
    Liste des armor stand équipé sur un joueur et actif
     */
    @Getter
    private HashMap<Player, ArmorStand> armorStands = new HashMap<>();

}
