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

public class ViewRemovedRecipes {

    public static Inventory build(String profession, int page) throws IOException {
        Inventory inventory = Bukkit.createInventory(null, 54, TradesPlus.translateText("&dView Removed Trades | "+profession));

        List<List<ItemStack>> paginated = Mapper.partition(IOHelper.getRemovedTrades(profession), 45);

        ArrayList<ItemStack> currentPage = paginated.size() > 0 ?
                (ArrayList) paginated.get(page)
                : new ArrayList<>();

        for(int i = 0; i<currentPage.size(); i++){
            inventory.setItem(i,
                    removedTradeButton(currentPage.get(i)));
        }

        inventory.setItem(45, returnButton());
        for(int i = 46; i<53;i++){
            inventory.setItem(i, blank());
        }

        if(page<paginated.size()-1){
            inventory.setItem(53, nextPage());
        }

        inventory.setItem(49, removeButton(page));

        return inventory;
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

    private static ItemStack removedTradeButton(ItemStack removed){
        ItemMeta meta = removed.getItemMeta();
        ArrayList<String> lore = new LoreBuilder().addLore("&l&cPress Q to allow that trade again.").build();
        meta.setLore(lore);
        removed.setItemMeta(meta);
        return removed;
    }

    private static ItemStack removeButton(int page){
        ItemStack stack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&aRemove Trade"));
        ArrayList<String> lore = new LoreBuilder().addLore("Page "+String.format("%03d",page)).build();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack nextPage(){
        ItemStack stack = new ItemStack(Material.ARROW);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&fNext Page"));
        stack.setItemMeta(meta);
        return stack;
    }

}
