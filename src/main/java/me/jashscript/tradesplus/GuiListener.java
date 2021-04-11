package me.jashscript.tradesplus;

import me.jashscript.tradesplus.gui.*;
import me.jashscript.tradesplus.model.CustomRecipe;
import me.jashscript.tradesplus.utils.IOHelper;
import me.nemo_64.spigotutilities.playerinputs.chatinput.PlayerChatInput;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class GuiListener implements Listener {

    @EventHandler
    public void guiHandler(InventoryClickEvent e) throws IOException {
        String title = e.getView().getTitle();
        if (!title.contains("|")) return;

        int slot = e.getSlot();
        int rawslot = e.getRawSlot();
        ItemStack itemStack = e.getCurrentItem();
        Inventory inventory = e.getClickedInventory();
        Player player = (Player) e.getWhoClicked();


        if (title.contains("TradesPlus Main Menu")) {
            e.setCancelled(true);

            if (slot == 11) {
                player.openInventory(CustomTradeMenu.build());
                return;
            } else if (slot == 13) {
                player.openInventory(RemoveTradeMenu.build());
                return;
            } else if (slot == 15) {
                player.openInventory(ViewForbiddenMenu.build(0));
                return;
            }

        } else if (title.contains("Add Trade")) {
            if (slot != 15 && slot != 13 && slot != 12 && rawslot < 27) e.setCancelled(true);
            if (slot == 26) {
                ItemStack result = inventory.getItem(15);
                if (result != null) {
                    ItemStack recipe1 = inventory.getItem(12);
                    ItemStack recipe2 = inventory.getItem(13);
                    if (recipe1 == null && recipe2 == null) {
                        player.closeInventory();
                        player.sendMessage(TradesPlus.translateText("&l&cThe trade can't be free, it needs at least one item as the cost."));
                        return;
                    }
                    String[] array = ChatColor.stripColor(title).split(" ");
                    String profession = array[array.length - 1];
                    PlayerChatInput.PlayerChatInputBuilder<Integer> builder = new PlayerChatInput.PlayerChatInputBuilder<>(TradesPlus.getInstance(), player);


                    player.closeInventory();
                    builder.isValidInput((p, str) -> {
                        try {
                            int val = Integer.valueOf(str);
                            return val > 0 && val <= 100;
                        } catch (Exception exception) {
                            return false;
                        }
                    }).setValue((p, str) -> {
                        return Integer.valueOf(str);
                    }).onInvalidInput((p, str) -> {
                        p.sendMessage("Invalid input.");
                        return true;
                    }).sendValueMessage(TradesPlus.translateText("&l&aWrite the chance in chat, between 1 and 100. Type cancel to cancel."))
                            .toCancel("cancel").defaultValue(10)
                            .onFinish((p, value) -> {

                                ItemStack[] recipes = new ItemStack[2];
                                if (recipe1 != null) recipes[0] = recipe1;
                                if (recipe2 != null) recipes[1] = recipe2;
                                CustomRecipe customRecipe = new CustomRecipe(recipes, result, value);
                                try {
                                    IOHelper.saveCustomRecipe(customRecipe, profession);
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                                p.sendMessage(TradesPlus.translateText("&l&aTrade saved!"));
                                try {
                                    p.openInventory(ViewCustomTrades.build(profession, 0));
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                            }).build().start();
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(TradesPlus.translateText("&l&cThe recipe's result can't be null, obviously."));
                    return;
                }

            } else if (slot == 45) {
                player.openInventory(CustomTradeMenu.build());
                return;
            }
        } else if (title.contains("Custom Trade Menu")) {
            e.setCancelled(true);

            if (slot == 45) {
                player.openInventory(MainMenu.build());
                return;
            } else if (itemStack != null) {
                if (!itemStack.getType().equals(Material.VILLAGER_SPAWN_EGG)) return;

                player.openInventory(ViewCustomTrades.build(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()), 0));
            }
        } else if (title.contains("View Removed Trades")) {
            e.setCancelled(true);
            ClickType click = e.getClick();
            String[] lines = ChatColor.stripColor(title).split(" ");
            String profession = lines[lines.length - 1];
            int page = Integer.parseInt(ChatColor.stripColor(inventory.getItem(49).getItemMeta().getLore().get(0)).split(" ")[1]);
            if (slot == 53) {
                if (inventory.getItem(53).getType().equals(Material.ARROW)) {
                    player.openInventory(ViewCustomTrades.build(profession, page + 1));
                }
                return;
            } else if (slot == 49) {
                player.openInventory(RemoveTrade.build(profession));
                return;
            } else if (slot == 45) {
                player.openInventory(RemoveTradeMenu.build());
                return;
            } else {
                if (itemStack != null) {
                    if (!itemStack.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                        if (click.equals(ClickType.DROP)) {
                            IOHelper.removeRemovedTrade(slot, profession);
                            player.openInventory(ViewRemovedRecipes.build(profession, page));
                        }
                    }
                    return;
                }
            }

        } else if (title.contains("Remove Trade Menu")) {
            e.setCancelled(true);

            if (slot == 45) {
                player.openInventory(MainMenu.build());
                return;
            } else if (itemStack != null) {
                if (!itemStack.getType().equals(Material.VILLAGER_SPAWN_EGG)) return;

                player.openInventory(ViewRemovedRecipes.build(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()), 0));
            }

        } else if (title.contains("Remove Trade")) {
            if (slot != 13 && rawslot < 27) e.setCancelled(true);

            if (slot == 26) {
                ItemStack remove = inventory.getItem(13);
                if (remove != null) {
                    String[] array = ChatColor.stripColor(title).split(" ");
                    String profession = array[array.length - 1];
                    PlayerChatInput.PlayerChatInputBuilder<Integer> builder = new PlayerChatInput.PlayerChatInputBuilder<>(TradesPlus.getInstance(), player);


                    player.closeInventory();
                    try {
                        IOHelper.saveRemovedTrade(remove, profession);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    player.sendMessage(TradesPlus.translateText("&l&aTrade removed!"));
                    player.openInventory(ViewRemovedRecipes.build(profession, 0));

                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(TradesPlus.translateText("&l&cYou can't just remove emptiness like that, believe me."));
                    return;
                }

            }

        } else if (title.contains("View Custom Trades")) {
            e.setCancelled(true);
            ClickType click = e.getClick();
            String[] lines = ChatColor.stripColor(title).split(" ");
            String profession = lines[lines.length - 1];
            int page = Integer.parseInt(ChatColor.stripColor(inventory.getItem(49).getItemMeta().getLore().get(0)).split(" ")[1]);
            if (slot == 53) {
                if (inventory.getItem(53).getType().equals(Material.ARROW)) {
                    player.openInventory(ViewCustomTrades.build(profession, page + 1));
                }
                return;
            } else if (slot == 49) {
                player.openInventory(AddTrade.build(profession));
                return;
            } else if(slot == 45) {
                player.openInventory(CustomTradeMenu.build());
                return;
            } else {
                if (itemStack != null) {
                    if (!itemStack.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                        if (click.equals(ClickType.DROP)) {
                            IOHelper.deleteCustomRecipe(slot, profession);
                            player.openInventory(ViewCustomTrades.build(profession, page));
                        }
                    }
                    return;
                }
            }
        } else if (title.contains("View Forbidden Trades")) {
            e.setCancelled(true);
            ClickType click = e.getClick();
            int page = Integer.parseInt(ChatColor.stripColor(inventory.getItem(49).getItemMeta().getLore().get(0)).split(" ")[1]);
            if (slot == 53) {
                if (inventory.getItem(53).getType().equals(Material.ARROW)) {
                    player.openInventory(ViewForbiddenMenu.build(page + 1));
                }
                return;
            } else if (slot == 49) {
                player.openInventory(ForbidTrade.build());
                return;
            } else if (slot == 45) {
                player.openInventory(MainMenu.build());
            } else {
                if (itemStack != null) {
                    if (!itemStack.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                        if (click.equals(ClickType.DROP)) {
                            IOHelper.removeForbidden(slot);
                            player.openInventory(ViewForbiddenMenu.build(page));
                        }
                    }
                    return;
                }
            }
        } else if (title.contains("Forbid Trade")) {
            if (slot != 13 && rawslot < 27) e.setCancelled(true);

            if (slot == 26) {
                ItemStack remove = inventory.getItem(13);
                if (remove != null) {
                    player.closeInventory();
                    try {
                        IOHelper.addForbidden(remove);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    player.sendMessage(TradesPlus.translateText("&l&aTrade forbid!"));
                    player.openInventory(ViewForbiddenMenu.build(0));

                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(TradesPlus.translateText("&l&cYou can't just remove emptiness like that, believe me."));
                    return;
                }

            }

        }


    }
}
