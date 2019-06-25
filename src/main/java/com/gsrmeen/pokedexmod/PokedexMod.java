package com.gsrmeen.pokedexmod;

import com.gsrmeen.commands.PinfoCommand;
import com.gsrmeen.commands.TinfoCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = PokedexMod.MODID, name = PokedexMod.NAME, version = PokedexMod.VERSION)
public class PokedexMod {
    public static final String MODID = "pokedexmod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent e) {
        e.registerServerCommand(new PinfoCommand());
        e.registerServerCommand(new TinfoCommand());
    }

}
