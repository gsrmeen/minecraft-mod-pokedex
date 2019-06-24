package com.gsrmeen.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
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
        return super.getUsage(icommandsender);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            String errorMessage = "Incorrect number of parameters, usage: /pinfo [pokemon]";
            sender.sendMessage(new TextComponentString(errorMessage).setStyle(new Style().setColor(TextFormatting.RED)));
            return;
        }

        String pokemonName = args[0];
        String returnMessage = String.format("You are searching info for: %s", pokemonName);
        sender.sendMessage(new TextComponentString(returnMessage));

    }
}
