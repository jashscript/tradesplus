package me.jashscript.tradesplus.utils;

import me.jashscript.tradesplus.TradesPlus;

import java.util.ArrayList;

public class LoreBuilder {

    private ArrayList<String> lore;

    public LoreBuilder(){
        lore = new ArrayList<>();
    }

    public LoreBuilder addLore(String text){
        this.lore.add(TradesPlus.translateText(text));
        return this;
    }

    public ArrayList<String> build(){
        return this.lore;
    }
}
