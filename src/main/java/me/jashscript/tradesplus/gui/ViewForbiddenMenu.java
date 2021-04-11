package me.jashscript.tradesplus.gui;

import me.jashscript.tradesplus.TradesPlus;
import me.jashscript.tradesplus.utils.IOHelper;
import me.jashscript.tradesplus.utils.LoreBuilder;
import me.jashscript.tradesplus.utils.Mapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewForbiddenMenu {

    public static Inventory build( int page) throws IOException {
        Inventory inventory = Bukkit.createInventory(null, 54, TradesPlus.translateText("&dView Forbidden Trades | "));

        List<List<ItemStack>> paginated = Mapper.partition(IOHelper.getForbidden(), 45);

        ArrayList<ItemStack> currentPage = paginated.size() > 0 ?
                (ArrayList) paginated.get(page)
                : new ArrayList<>();

        for(int i = 0; i<currentPage.size(); i++){
            inventory.setItem(i,
                    ForbiddenTradeButton(currentPage.get(i)));
        }

        inventory.setItem(45, ReturnButton());
        for(int i = 46; i<53;i++){
            inventory.setItem(i, Blank());
        }

        if(page<paginated.size()-1){
            inventory.setItem(53, NextPage());
        }

        inventory.setItem(49, ForbidButton(page));

        return inventory;
    }

    private static ItemStack Blank(){
        ItemStack stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack ReturnButton(){
        ItemStack stack = new ItemStack(Material.REPEATER);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&cReturn"));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack ForbiddenTradeButton(ItemStack removed){
        ItemMeta meta = removed.getItemMeta();
        ArrayList<String> lore = new LoreBuilder().addLore("&l&cPress Q to allow that trade again.").build();
        meta.setLore(lore);
        removed.setItemMeta(meta);
        return removed;
    }

    private static ItemStack ForbidButton(int page){
        ItemStack stack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&aForbid Trade"));
        ArrayList<String> lore = new LoreBuilder().addLore("Page "+String.format("%03d",page)).build();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack NextPage(){
        ItemStack stack = new ItemStack(Material.ARROW);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&fNext Page"));
        stack.setItemMeta(meta);
        return stack;
    }

}
