package fr.teamunc.libcosmetics.models.cosmetic;

import lombok.Getter;

public class Cosmetic {

    @Getter
    private int id;

    @Getter
    private String name;

    @Getter
    private CosmeticType cosmeticType;
}
