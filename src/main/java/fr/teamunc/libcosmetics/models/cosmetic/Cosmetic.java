package fr.teamunc.libcosmetics.models.cosmetic;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Cosmetic {

    @Getter
    private int id; // Id & modelData id

    @Getter
    private String name;

    @Getter
    private CosmeticType cosmeticType;

    @Getter
    private String cosmeticKey; // Clé pour identifier avec le nom un cosmétique

    public ItemStack getItemStack() {
        // TODO : Changer l'item en config dans le plugin
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(this.id);
        meta.setDisplayName(this.name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("$a"+this.cosmeticType.toString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
