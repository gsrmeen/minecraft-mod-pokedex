package com.gsrmeen.commands;

import com.gsrmeen.itemcrawler.IItemCrawler;
import com.gsrmeen.itemcrawler.PixelmonItemCrawler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.command.ForgeCommand;

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
        int firstItemPartIndex;

        if (args[0].equals("drop")) {
            firstItemPartIndex = 1;
        } else {
            firstItemPartIndex = 0;
        }


        String itemName = getItemName(args, firstItemPartIndex);
        IItemCrawler crawler = new PixelmonItemCrawler(itemName);

        if (!crawler.itemValid()) {
            String msg = String.format("Couldn't find item named %s. Try again.", itemName);
            showErrorMessage(msg, sender);
            return;
        }

        if (args[0].equals("drop")) {
            TextComponentString drops = crawler.getPossibleDrops();
            if(drops == null) {
                String msg = String.format("Couldn't find possible drop for item named %s. Try again.", itemName);
                showErrorMessage(msg, sender);
            } else {
                sender.sendMessage(drops);
            }
        } else {
            TextComponentString description = crawler.getDescription();
            sender.sendMessage(description);
        }
    }

    private String getItemName(String[] args, int firstIndex) {
        String[] argsConverted = new String[args.length - firstIndex];
        for (int i = firstIndex; i < args.length; i++) {
            String arg = args[i];
            argsConverted[i - firstIndex] = arg.substring(0, 1).toUpperCase() + arg.substring(1);
        }

        return String.join(" ", argsConverted);
    }

    private void showErrorMessage(String msg, ICommandSender sender) {
        sender.sendMessage(new TextComponentString(msg).setStyle(new Style().setColor(TextFormatting.RED)));
    }
}
