package me.jashscript.tradesplus.gui;

import me.jashscript.tradesplus.TradesPlus;
import me.jashscript.tradesplus.model.CustomRecipe;
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
import java.util.Optional;

public class ViewCustomTrades {


    public static Inventory build(String profession, int page) throws IOException {
        Inventory inventory = Bukkit.createInventory(null, 54, TradesPlus.translateText("&dView Custom Trades | "+profession));

        List<List<CustomRecipe>> paginated = Mapper.partition(IOHelper.getCustomRecipes(profession), 45);

        ArrayList<CustomRecipe> currentPage = paginated.size() > 0 ?
                (ArrayList) paginated.get(page)
                : new ArrayList<>();

        for(int i = 0; i<currentPage.size(); i++){
            inventory.setItem(i,
                    RecipeButton(currentPage.get(i)));
        }

        inventory.setItem(45, ReturnButton());
        for(int i = 46; i<53;i++){
            inventory.setItem(i, Blank());
        }

        if(page<paginated.size()-1){
            inventory.setItem(53, NextPage());
        }

        inventory.setItem(49, AddButton(page));

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

    private static ItemStack RecipeButton(CustomRecipe customRecipe){
        ItemStack stack = customRecipe.result;
        ItemMeta meta = stack.getItemMeta();
        ArrayList<String> lore = new LoreBuilder().addLore("&l&cPress Q to remove recipe.").build();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack AddButton(int page){
        ItemStack stack = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TradesPlus.translateText("&l&aAdd Trade"));
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
