package com.gsrmeen.itemcrawler;

import net.minecraft.util.text.TextComponentString;

public interface IItemCrawler {
    TextComponentString getDescription();
    boolean itemValid();
}
