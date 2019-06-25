package com.gsrmeen.commands;

import com.gsrmeen.pokedex.IPokemonCrawler;
import com.gsrmeen.pokedex.PixelmonPokemonCrawler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.command.ForgeCommand;

import java.util.Arrays;


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
        IPokemonCrawler crawler = new PixelmonPokemonCrawler(pokemonName);

        String validQueries[] = {"d", "t", "s", "description", "type", "stats"};
        if (!Arrays.asList(validQueries).contains(query)) {
            String msg = String.format("Couldn't process %s. Try again.", query);
            showErrorMessage(msg, sender);
            return;
        }

        if (!crawler.pokemonValid()) {
            String msg = String.format("Couldn't find pokemon named %s. Try again.", pokemonName);
            showErrorMessage(msg, sender);
            return;
        }

        switch (query) {
            case "d":
            case "description": {
                TextComponentString description = crawler.getDescription();
                sender.sendMessage(description);
                break;
            }

            case "t":
            case "type": {
                ITextComponent typeInfo = crawler.getTypeInfo();
                sender.sendMessage(typeInfo);
                break;
            }

            case "s":
            case "stats": {
                ITextComponent stats = crawler.getStats();
                sender.sendMessage(stats);
                break;
            }
        }
    }

    private void showErrorMessage(String msg, ICommandSender sender) {
        sender.sendMessage(new TextComponentString(msg).setStyle(new Style().setColor(TextFormatting.RED)));
    }
}
