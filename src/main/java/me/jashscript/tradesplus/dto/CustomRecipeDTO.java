package me.jashscript.tradesplus.dto;

import org.bukkit.inventory.ItemStack;

public class CustomRecipeDTO {

    public String[] ingredients;
    public String result;
    public int chance;

    public CustomRecipeDTO(String[] ingredients, String result, int chance){
        this.ingredients = ingredients;
        this.result = result;
        this.chance = chance;
    }
}
