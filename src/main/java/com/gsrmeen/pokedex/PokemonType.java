package com.gsrmeen.pokedex;

public enum PokemonType {
    BUG(0, "Bug"),
    DARK(1, "Dark"),
    DRAGON(2, "Dragon"),
    ELECTRIC(3, "Electric"),
    FAIRY(4, "Fairy"),
    FIGHTING(5, "Fighting"),
    FIRE(6, "Fire"),
    FLYING(7, "Flying"),
    GHOST(8, "Ghost"),
    GRASS(9, "Grass"),
    GROUND(10, "Ground"),
    ICE(11, "Ice"),
    NORMAL(12, "Normal"),
    POISON(13, "Poison"),
    PSYCHIC(14, "Psychic"),
    ROCK(15, "Rock"),
    STEEL(16, "Steel"),
    WATER(17, "Water");

    PokemonType(int id, String name) {
        this.id = id;
        this.prettyName = name;
    }

    @Override
    public String toString() {
        return this.prettyName;
    }

    private int id;
    private String prettyName;
}
