package me.jashscript.tradesplus;

import me.jashscript.tradesplus.gui.MainMenu;
import me.jashscript.tradesplus.utils.IOHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public final class TradesPlus extends JavaPlugin {

    public static ArrayList<Villager.Profession> professions = new ArrayList<>();

    private static String directory = "plugins/tradesPlus/";

    public static ArrayList<UUID> villagers = new ArrayList<>();

    private static TradesPlus instance;


    @Override
    public void onEnable() {
        Path customRecipesPath = Paths.get(directory+"customRecipes");
        Path removedRecipesPath = Paths.get(directory+"removedRecipes");
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        instance = this;

        if(!Files.exists(customRecipesPath)) try {
            Files.createDirectories(customRecipesPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        if(!Files.exists(removedRecipesPath)) try {
            Files.createDirectories(removedRecipesPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        populateProfessions();

        professions.forEach(
                profession -> {
                    Path path1 = Paths.get(directory+"customRecipes/"+profession.name().toLowerCase(Locale.ROOT));
                    Path path2 = Paths.get(directory+"removedRecipes/"+profession.name().toLowerCase(Locale.ROOT));

                    if(!Files.exists(path1)) try {
                        IOHelper.createNewCRFile(profession.name().toLowerCase(Locale.ROOT));
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    if(!Files.exists(path2)) try {
                        IOHelper.createNewRTFile(profession.name().toLowerCase(Locale.ROOT));
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
        );

        if(!Files.exists(Paths.get(directory+"villagers"))){
            try {
                IOHelper.createNewVillagersFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                villagers = IOHelper.getVillagers();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if(!Files.exists(Paths.get(directory+"forbidden"))){
            try {
                IOHelper.createForbiddenFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        pluginManager.registerEvents(new GuiListener(), this);
        pluginManager.registerEvents(new VillagerListener(), this);

        new SaveVillagerTask().runTaskTimer(this, 6000L, 6000L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!label.equals("tradesplus")) return true;
        Player player = (Player) sender;
        if(!player.hasPermission("tradesplus.use")) return true;

        player.openInventory(MainMenu.build());
        return true;
    }

    private void populateProfessions() {
        professions.add(Villager.Profession.ARMORER);
        professions.add(Villager.Profession.BUTCHER);
        professions.add(Villager.Profession.CARTOGRAPHER);
        professions.add(Villager.Profession.CLERIC);
        professions.add(Villager.Profession.FARMER);
        professions.add(Villager.Profession.FISHERMAN);
        professions.add(Villager.Profession.FLETCHER);
        professions.add(Villager.Profession.LEATHERWORKER);
        professions.add(Villager.Profession.LIBRARIAN);
        professions.add(Villager.Profession.MASON);
        professions.add(Villager.Profession.SHEPHERD);
        professions.add(Villager.Profession.TOOLSMITH);
        professions.add(Villager.Profession.WEAPONSMITH);
    }


    public static String translateText(String text){
        return ChatColor.translateAlternateColorCodes('&',text);
    }

    public static TradesPlus getInstance() { return instance; }

    @Override
    public void onDisable() {
        try {
            IOHelper.saveVillagers(villagers);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
