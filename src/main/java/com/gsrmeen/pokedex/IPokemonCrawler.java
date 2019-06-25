package com.gsrmeen.pokedex;

import net.minecraft.util.text.TextComponentString;

public interface IPokemonCrawler {
    TextComponentString getDescription();
    TextComponentString getStats();
    TextComponentString getTypeInfo();
    boolean pokemonValid();
}
