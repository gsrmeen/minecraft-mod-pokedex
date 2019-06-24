package com.gsrmeen.commands;

import com.gsrmeen.pokedex.PokemonCrawler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.command.ForgeCommand;


public class PinfoCommand extends ForgeCommand {

    public static String NAME = "pinfo";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender icommandsender) {
        return "Usage: /pinfo [pokemon]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2) {
            String msg = "Incorrect number of parameters, usage: /pinfo [pokemon]";
            showErrorMessage(msg, sender);
            return;
        }

        String query = args[0];
        String pokemonName = args[1];
        PokemonCrawler crawler = new PokemonCrawler(pokemonName);

        if (!crawler.pokemonValid()) {
            String msg = String.format("Couldn't find pokemon named %s. Try again.", pokemonName);
            showErrorMessage(msg, sender);
            return;
        }

        switch (query) {
            case "description":
            case "desc":
            case "d": {
                String description = crawler.getDescription();
                sender.sendMessage(new TextComponentString(description));
                break;
            }

            case "type": {
                ITextComponent typeInfo = crawler.getTypeInfo();
                sender.sendMessage(typeInfo);
                break;
            }

            case "total": {

            }
        }


    }

    private void showErrorMessage(String msg, ICommandSender sender) {
        sender.sendMessage(new TextComponentString(msg).setStyle(new Style().setColor(TextFormatting.RED)));
    }
}
