package com.gsrmeen.pokedex;

import net.minecraft.util.text.TextFormatting;

public enum PokemonType {
    BUG(0, "Bug", TextFormatting.DARK_GREEN),
    DARK(1, "Dark", TextFormatting.DARK_GRAY),
    DRAGON(2, "Dragon", TextFormatting.LIGHT_PURPLE),
    ELECTRIC(3, "Electric", TextFormatting.YELLOW),
    FAIRY(4, "Fairy", TextFormatting.RED),
    FIGHTING(5, "Fighting", TextFormatting.DARK_RED),
    FIRE(6, "Fire", TextFormatting.RED),
    FLYING(7, "Flying", TextFormatting.AQUA),
    GHOST(8, "Ghost", TextFormatting.GRAY),
    GRASS(9, "Grass", TextFormatting.GREEN),
    GROUND(10, "Ground", TextFormatting.GOLD),
    ICE(11, "Ice", TextFormatting.DARK_AQUA),
    NORMAL(12, "Normal", TextFormatting.WHITE),
    POISON(13, "Poison", TextFormatting.DARK_PURPLE),
    PSYCHIC(14, "Psychic", TextFormatting.DARK_PURPLE),
    ROCK(15, "Rock", TextFormatting.GOLD),
    STEEL(16, "Steel", TextFormatting.DARK_GRAY),
    WATER(17, "Water", TextFormatting.BLUE);

    PokemonType(int id, String name, TextFormatting color) {
        this.id = id;
        this.prettyName = name;
        this.chatColor = color;
    }

    @Override
    public String toString() {
        return this.prettyName;
    }

    public TextFormatting getChatColor() {
        return chatColor;
    }

    private int id;
    private String prettyName;
    private TextFormatting chatColor;
}
