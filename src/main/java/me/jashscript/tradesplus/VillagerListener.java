package me.jashscript.tradesplus;

import me.jashscript.tradesplus.model.CustomRecipe;
import me.jashscript.tradesplus.utils.IOHelper;
import me.jashscript.tradesplus.utils.Mapper;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.io.IOException;
import java.util.*;

public class VillagerListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent e) throws IOException {
        if (!e.getRightClicked().getType().equals(EntityType.VILLAGER)) return;

        Villager villager = (Villager) e.getRightClicked();

        if (TradesPlus.villagers.contains(villager.getUniqueId())) return;

        String profession = villager.getProfession().toString().toLowerCase(Locale.ROOT);

        List<MerchantRecipe> recipes = new ArrayList<>(villager.getRecipes());

        ArrayList<ItemStack> forbiddenTrades = IOHelper.getForbidden();
        if (forbiddenTrades.size() > 0) {


            recipes.removeIf(recipe -> {
                ItemStack result = recipe.getResult();
                Material material = result.getType();

                if (material.equals(Material.ENCHANTED_BOOK)) {
                    if (forbiddenTrades.contains(result)) return true;
                }

                ArrayList<Material> materialList = Mapper.fromRemovedTrades(forbiddenTrades);

                if (materialList.contains(material)) return true;

                return false;
            });

        }

        ArrayList<ItemStack> removedTrades = IOHelper.getRemovedTrades(profession);
        if (removedTrades.size() > 0) {


            recipes.removeIf(recipe -> {
                ItemStack result = recipe.getResult();
                Material material = result.getType();

                if (material.equals(Material.ENCHANTED_BOOK)) {
                    if (removedTrades.contains(result)) return true;
                }

                ArrayList<Material> materialList = Mapper.fromRemovedTrades(removedTrades);

                if (materialList.contains(material)) return true;

                return false;
            });

        }

        ArrayList<CustomRecipe> customRecipes = IOHelper.getCustomRecipes(profession);

        if (customRecipes.size() > 0) {
            customRecipes.forEach(
                    customRecipe -> {
                        int random = new Random().nextInt(101);
                        if (customRecipe.chance >= random) {
                            MerchantRecipe merchantRecipe = new MerchantRecipe(customRecipe.result, 16);
                            if (customRecipe.ingredients[0] != null)
                                merchantRecipe.addIngredient(customRecipe.ingredients[0]);
                            if (customRecipe.ingredients[1] != null)
                                merchantRecipe.addIngredient(customRecipe.ingredients[1]);
                            recipes.add(merchantRecipe);
                        }
                    }
            );
        }

        villager.setRecipes(recipes);
        TradesPlus.villagers.add(villager.getUniqueId());
    }

    @EventHandler
    public void onVillagerDeath(EntityDeathEvent e){
        if(!(e.getEntity().getType().equals(EntityType.VILLAGER))) return;
        UUID uuid = e.getEntity().getUniqueId();
        if(TradesPlus.villagers.contains(uuid)) TradesPlus.villagers.remove(uuid);
    }

}
