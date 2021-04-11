package me.jashscript.tradesplus;

import me.jashscript.tradesplus.utils.IOHelper;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class SaveVillagerTask extends BukkitRunnable {
    @Override
    public void run() {
        try {
            IOHelper.saveVillagers(TradesPlus.villagers);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
