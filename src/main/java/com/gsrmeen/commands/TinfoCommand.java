package com.gsrmeen.commands;

import com.gsrmeen.itemcrawler.IItemCrawler;
import com.gsrmeen.itemcrawler.PixelmonItemCrawler;
import com.gsrmeen.pokemoncrawler.IPokemonCrawler;
import com.gsrmeen.pokemoncrawler.PixelmonPokemonCrawler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.command.ForgeCommand;

import java.util.Arrays;

public class TinfoCommand extends ForgeCommand {
    private static final String NAME = "tinfo";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender icommandsender) {
        return "Usage: /tinfo [item]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            String msg = "Incorrect number of parameters, usage: /tinfo [item]";
            showErrorMessage(msg, sender);
            return;
        }

        String itemName = args[0];

        IItemCrawler crawler = new PixelmonItemCrawler(itemName);

        if (!crawler.itemValid()) {
            String msg = String.format("Couldn't find item named %s. Try again.", itemName);
            showErrorMessage(msg, sender);
            return;
        }

        TextComponentString description = crawler.getDescription();
        sender.sendMessage(description);
    }

    private void showErrorMessage(String msg, ICommandSender sender) {
        sender.sendMessage(new TextComponentString(msg).setStyle(new Style().setColor(TextFormatting.RED)));
    }

}
