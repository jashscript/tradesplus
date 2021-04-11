package me.jashscript.tradesplus.gui;

import me.jashscript.tradesplus.TradesPlus;
import me.jashscript.tradesplus.utils.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MainMenu {



    public static Inventory build(){
        Inventory inventory = Bukkit.createInventory(null, 27, TradesPlus.translateText("&dTradesPlus Main Menu |"));

        inventory.setItem(11, customTrade());
        inventory.setItem(13, removeTrade());
        inventory.setItem(15, forbidTrade());

        return inventory;
    }


    private static ItemStack customTrade(){
        ItemStack stack = new ItemStack(Material.SHULKER_BOX);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&aCustom Trades"));
        ArrayList<String> lore = new LoreBuilder()
                .addLore("&fAdd custom trades to")
                .addLore("&fa specific profession.")
                .build();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }


    private static ItemStack removeTrade(){
        ItemStack stack = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&aRemove Trade"));
        ArrayList<String> lore = new LoreBuilder()
                .addLore("&fRemove an item from")
                .addLore("&fa specific profession's")
                .addLore("&ftrade.")
                .build();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack forbidTrade(){
        ItemStack stack = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&aForbid Trade"));
        ArrayList<String> lore = new LoreBuilder()
                .addLore("&fCompletely remove")
                .addLore("&fan item from being traded")
                .addLore("&fby any profession.")
                .build();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

}
