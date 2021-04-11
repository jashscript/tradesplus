package me.jashscript.tradesplus.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.jashscript.tradesplus.dto.CustomRecipeDTO;
import me.jashscript.tradesplus.model.CustomRecipe;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class IOHelper {

    private static String customRecipesDir = "plugins/tradesPlus/customRecipes/";
    private static String removedRecipesDir = "plugins/tradesPlus/removedRecipes/";
    private static Gson gson = new Gson();

    public static void saveCustomRecipe(CustomRecipe customRecipe, String profession) throws IOException {
        Path path = Paths.get(customRecipesDir +profession);
        ArrayList<CustomRecipe> customRecipes = getCustomRecipes(profession);
        customRecipes.add(customRecipe);
        ArrayList<CustomRecipeDTO> customRecipesDTO = Mapper.fromCustomRecipeList(customRecipes);
        String json = gson.toJson(customRecipesDTO);
        Files.write(path, json.getBytes());
    }

    public static void deleteCustomRecipe(int index, String profession) throws IOException {
        Path path = Paths.get(customRecipesDir +profession);
        ArrayList<CustomRecipe> customRecipes = getCustomRecipes(profession);
        customRecipes.remove(index);
        ArrayList<CustomRecipeDTO> customRecipesDTO = Mapper.fromCustomRecipeList(customRecipes);
        String json = gson.toJson(customRecipesDTO);
        Files.write(path, json.getBytes());
    }

    public static ArrayList<CustomRecipe> getCustomRecipes(String profession) throws IOException {
        Path path = Paths.get(customRecipesDir +profession);
        String json = new String(Files.readAllBytes(path));
        ArrayList<CustomRecipeDTO> customRecipeDTOList = gson.fromJson(json, new TypeToken<ArrayList<CustomRecipeDTO>>(){}.getType());
        return Mapper.fromCustomRecipeDTOList(customRecipeDTOList);
    }

    public static void createNewCRFile(String profession) throws IOException {
        Path path = Paths.get(customRecipesDir +profession);
        String json = gson.toJson(new ArrayList<CustomRecipeDTO>());
        Files.write(path, json.getBytes());
    }
    public static void createNewRTFile(String profession) throws IOException {
        Path path = Paths.get(removedRecipesDir +profession);
        String json = gson.toJson(new ArrayList<String>());
        Files.write(path, json.getBytes());
    }

    public static void createNewVillagersFile() throws IOException {
        Path path = Paths.get("plugins/tradesPlus/villagers");
        String json = gson.toJson(new ArrayList<UUID>());
        Files.write(path, json.getBytes());
    }

    public static void saveRemovedTrade(ItemStack itemStack, String profession) throws IOException {
        Path path = Paths.get(removedRecipesDir +profession);
        ArrayList<ItemStack> itemStacks = getRemovedTrades(profession);
        itemStacks.add(itemStack);
        Files.write(path, gson.toJson(Mapper.fromListOfStacks(itemStacks)).getBytes());
    }

    public static void removeRemovedTrade(int index, String profession) throws IOException {
        Path path = Paths.get(removedRecipesDir +profession);
        ArrayList<ItemStack> itemStacks = getRemovedTrades(profession);
        itemStacks.remove(index);
        Files.write(path, gson.toJson(Mapper.fromListOfStacks(itemStacks)).getBytes());
    }

    public static ArrayList<ItemStack> getRemovedTrades(String profession) throws IOException {
        Path path = Paths.get(removedRecipesDir +profession);
        ArrayList<String> serializedList = gson.fromJson(new String(Files.readAllBytes(path)), new TypeToken<ArrayList<String>>(){}.getType());
        return Mapper.fromListOfBase64(serializedList);
    }

    public static ArrayList<UUID> getVillagers() throws IOException {
        Path path = Paths.get("plugins/tradesPlus/villagers");
        ArrayList<UUID> uuids =  gson.fromJson(new String(Files.readAllBytes(path)), new TypeToken<ArrayList<UUID>>(){}.getType());
        if(uuids == null) return new ArrayList<>();
        return uuids;
    }

    public static void saveVillagers(ArrayList<UUID> villagers) throws IOException {
        Path path = Paths.get("plugins/tradesPlus/villagers");
        Files.write(path, gson.toJson(villagers).getBytes());
    }

    public static void createForbiddenFile() throws IOException {
        Path path = Paths.get("plugins/tradesPlus/forbidden");
        Files.write(path, gson.toJson(new ArrayList<>()).getBytes());
    }

    public static ArrayList<ItemStack> getForbidden() throws IOException {
        Path path = Paths.get("plugins/tradesPlus/forbidden");
        ArrayList<String> serializedList = gson.fromJson(new String(Files.readAllBytes(path)), new TypeToken<ArrayList<String>>(){}.getType());
        return Mapper.fromListOfBase64(serializedList);
    }

    public static void addForbidden(ItemStack newForbidden) throws IOException {
        Path path = Paths.get("plugins/tradesPlus/forbidden");
        ArrayList<String> serializedList = gson.fromJson(new String(Files.readAllBytes(path)), new TypeToken<ArrayList<String>>(){}.getType());
        ArrayList<ItemStack> forbidden = Mapper.fromListOfBase64(serializedList);
        forbidden.add(newForbidden);
        Files.write(path, gson.toJson(Mapper.fromListOfStacks(forbidden)).getBytes());
    }

    public static void removeForbidden(int index) throws IOException {
        Path path = Paths.get("plugins/tradesPlus/forbidden");
        ArrayList<String> serializedList = gson.fromJson(new String(Files.readAllBytes(path)), new TypeToken<ArrayList<String>>(){}.getType());
        ArrayList<ItemStack> forbidden = Mapper.fromListOfBase64(serializedList);
        forbidden.remove(index);
        Files.write(path, gson.toJson(Mapper.fromListOfStacks(forbidden)).getBytes());
    }
}
