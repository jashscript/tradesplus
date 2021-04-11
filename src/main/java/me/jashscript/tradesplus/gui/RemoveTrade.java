package me.jashscript.tradesplus.gui;

import me.jashscript.tradesplus.TradesPlus;
import me.jashscript.tradesplus.utils.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RemoveTrade{


    public static Inventory build(String profession){
        Inventory inventory = Bukkit.createInventory(null, 27, TradesPlus.translateText("&dRemove Trade | "+profession));

        for(int i = 0; i<27;i++){
            if(i!=14 && i!=13)
                inventory.setItem(i,Blank());
        }

        inventory.setItem(14, Divider());

        inventory.setItem(26, Confirm());

        return inventory;
    }

    private static ItemStack Blank(){
        ItemStack stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack Divider(){
        ItemStack stack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        ArrayList<String> lore = new LoreBuilder().addLore("&l&a< Item to be removed")
                .build();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }


    private static ItemStack Confirm(){
        ItemStack stack = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&aConfirm"));
        stack.setItemMeta(meta);
        return stack;
    }

}
