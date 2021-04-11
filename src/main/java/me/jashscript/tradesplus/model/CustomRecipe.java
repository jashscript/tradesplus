package me.jashscript.tradesplus.model;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CustomRecipe {

    public ItemStack[] ingredients;
    public ItemStack result;
    public int chance;

    public CustomRecipe(ItemStack[] ingredients, ItemStack result, int chance){
        this.ingredients = ingredients;
        this.result = result;
        this.chance = chance;
    }
}
