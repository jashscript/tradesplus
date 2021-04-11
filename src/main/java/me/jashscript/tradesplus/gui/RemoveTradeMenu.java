package me.jashscript.tradesplus.gui;

import me.jashscript.tradesplus.TradesPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class RemoveTradeMenu{


    public static Inventory build(){
        Inventory inventory = Bukkit.createInventory(null, 54, TradesPlus.translateText("&d19Remove Trade Menu |"));


        for(int i = 0; i< TradesPlus.professions.size(); i++){
            inventory.setItem(i,
                    professionButton(TradesPlus.professions.get(i).toString().toLowerCase(Locale.ROOT)));
        }

        inventory.setItem(45, returnButton());
        for(int i = 46; i<54;i++){
            inventory.setItem(i, blank());
        }

        return inventory;
    }

    private static ItemStack professionButton(String profession){
        ItemStack stack = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&a"+profession));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack blank(){
        ItemStack stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        stack.setItemMeta(meta);
        return stack;
    }


    private static ItemStack returnButton(){
        ItemStack stack = new ItemStack(Material.REPEATER);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&cReturn"));
        stack.setItemMeta(meta);
        return stack;
    }

}
