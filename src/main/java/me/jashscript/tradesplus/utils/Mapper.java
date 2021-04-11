package me.jashscript.tradesplus.utils;

import me.jashscript.tradesplus.dto.CustomRecipeDTO;
import me.jashscript.tradesplus.model.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static CustomRecipeDTO fromCustomRecipe(CustomRecipe customRecipe) throws IOException {
        String[] ingredients = new String[2];

        if(customRecipe.ingredients[0] != null) ingredients[0] = Serialization.serializeItemStack(customRecipe.ingredients[0]);
        if(customRecipe.ingredients[1] != null) ingredients[1] = Serialization.serializeItemStack(customRecipe.ingredients[1]);
        return new CustomRecipeDTO(ingredients, Serialization.serializeItemStack(customRecipe.result), customRecipe.chance);
    }

    public static ArrayList<CustomRecipeDTO> fromCustomRecipeList(ArrayList<CustomRecipe> customRecipes) throws IOException {
        ArrayList<CustomRecipeDTO> output = new ArrayList<>();

        customRecipes.forEach(
                customRecipe -> {
                    try {
                        output.add(fromCustomRecipe(customRecipe));
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
        );
        return output;
    }

    public static <E> List<List<E>> partition(List<E> list, int amount) {
        List<List<E>> parition = new ArrayList<>();
        List<E> current = new ArrayList<>();
        for (int index = 0; index < list.size(); index++) {
            if (current.size() == amount) {
                parition.add(current);
                current = new ArrayList<>();
            }
            current.add(list.get(index));
        }
        if (!current.isEmpty()) {
            parition.add(current);
        }
        return parition;
    }

    public static CustomRecipe fromCustomRecipeDTO(CustomRecipeDTO customRecipeDTO) throws IOException, ClassNotFoundException {
        ItemStack[] ingredients = new ItemStack[2];

        if(customRecipeDTO.ingredients[0] != null) ingredients[0] = Serialization.deserializeItemStack(customRecipeDTO.ingredients[0]);
        if(customRecipeDTO.ingredients[1] != null) ingredients[1] = Serialization.deserializeItemStack(customRecipeDTO.ingredients[1]);

        return new CustomRecipe(ingredients, Serialization.deserializeItemStack(customRecipeDTO.result), customRecipeDTO.chance);
    }

    public static ArrayList<CustomRecipe> fromCustomRecipeDTOList(ArrayList<CustomRecipeDTO> customRecipeDTOList) throws IOException {
        ArrayList<CustomRecipe> output = new ArrayList<>();
        customRecipeDTOList.forEach(
                customRecipeDTO -> {
                    try {
                        output.add(fromCustomRecipeDTO(customRecipeDTO));
                    } catch (IOException | ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
        );
        return output;
    }

    public static ArrayList<String> fromListOfStacks(ArrayList<ItemStack> itemStacks){
        ArrayList<String> output = new ArrayList<>();

        itemStacks.forEach(
                itemStack -> {
                    try {
                        output.add(Serialization.serializeItemStack(itemStack));
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
        );

        return output;
    }

    public static ArrayList<Material> fromRemovedTrades(ArrayList<ItemStack> itemStacks){
        ArrayList<Material> output = new ArrayList<>();
        itemStacks.forEach(
                stack -> {
                    output.add(stack.getType());
                }
        );

        return output;
    }


    public static ArrayList<ItemStack> fromListOfBase64(ArrayList<String> serializedList){
        ArrayList<ItemStack> output = new ArrayList<>();

        serializedList.forEach(
                serialized -> {
                    try {
                        output.add(Serialization.deserializeItemStack(serialized));
                    } catch (IOException | ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
        );

        return output;
    }

}
