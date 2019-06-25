package com.gsrmeen.itemcrawler;

import net.minecraft.util.text.TextComponentString;

public class PixelmonItemCrawler implements IItemCrawler {
    private String item;

    public PixelmonItemCrawler(String item) {
        this.item = item;
    }

    @Override
    public TextComponentString getDescription() {
        return null;
    }

    @Override
    public boolean itemValid() {
        return false;
    }
}
