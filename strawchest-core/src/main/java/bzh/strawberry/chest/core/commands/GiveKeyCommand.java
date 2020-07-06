package bzh.strawberry.chest.core.commands;

import bzh.strawberry.api.command.AbstractCommand;
import bzh.strawberry.chest.core.StrawChest;
import bzh.strawberry.chest.core.manager.Chest;
import bzh.strawberry.chest.core.manager.ItemToClaimManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.stream.Collectors;

/*
 * This file GiveKeyCommand is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:02 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class GiveKeyCommand extends AbstractCommand {

    public GiveKeyCommand() {
        super(StrawChest.getInstance(), "strawchest.admin");
    }

    @Override
    protected boolean onCommand(CommandSender commandSender, String s, String[] strings) {
        if (strings.length > 0) {
            if (strings[0].equals("list")) {
                commandSender.sendMessage(StrawChest.getInstance().getPrefix() + "Type: " + StrawChest.INSTANCE.getChests().stream().map(Chest::getFlatName).collect(Collectors.toList()));
            } else if (strings[0].equals("all") && strings.length == 3) {
                String key = strings[1];
                if (StrawChest.INSTANCE.getChest(key) != null) {
                    try {
                        int number = Integer.parseInt(strings[2]);
                        if (number <= 0 || number > 64)
                            throw new NumberFormatException();

                        ItemStack itemStack = new ItemStack(Material.TRIPWIRE_HOOK);
                        itemStack.setAmount(number);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        itemMeta.setDisplayName("§8[" + StrawChest.INSTANCE.getChest(key).getName() + "§8]");
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StrawChest.getInstance(), "coffre"), PersistentDataType.STRING, key.toUpperCase());

                        itemStack.setItemMeta(itemMeta);
                        for (Player player : StrawChest.INSTANCE.getServer().getOnlinePlayers()) {
                            if (player.getInventory().firstEmpty() != -1)
                                player.getInventory().addItem(itemStack);
                            else
                                ItemToClaimManager.INSTANCE.getItemToClaim(player).addItem(ItemToClaimManager.INSTANCE.getItemToClaim(player).addNouvelItem(itemStack), itemStack);
                        }
                        commandSender.sendMessage(StrawChest.INSTANCE.getPrefix() + "§3Vous venez de give §b" + number + " §3de clées " + StrawChest.INSTANCE.getChest(key).getName());
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(StrawChest.getInstance().getPrefix() + "§cLe nombre de clés doit être compris entre 1 et 64");
                    }
                }
            } else if (strings[0].equals("to") && strings.length == 4) {
                String key = strings[2];
                if (StrawChest.INSTANCE.getChest(key) != null) {
                    try {
                        int number = Integer.parseInt(strings[3]);
                        if (number <= 0 || number > 64)
                            throw new NumberFormatException();

                        ItemStack itemStack = new ItemStack(Material.TRIPWIRE_HOOK);
                        itemStack.setAmount(number);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        itemMeta.setDisplayName("§8[" + StrawChest.INSTANCE.getChest(key).getName() + "§8]");
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StrawChest.getInstance(), "coffre"), PersistentDataType.STRING, key.toUpperCase());

                        itemStack.setItemMeta(itemMeta);
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player != null) {
                            if (player.getInventory().firstEmpty() != -1)
                                player.getInventory().addItem(itemStack);
                            else
                                ItemToClaimManager.INSTANCE.getItemToClaim(player).addItem(ItemToClaimManager.INSTANCE.getItemToClaim(player).addNouvelItem(itemStack), itemStack);

                            commandSender.sendMessage(StrawChest.INSTANCE.getPrefix() + "§3Vous venez de give à §b" + player.getName() + " §b" + number + " §3de clées " + StrawChest.INSTANCE.getChest(key).getName());
                        } else {
                            commandSender.sendMessage(StrawChest.INSTANCE.getPrefix() + "§cCe joueur n'est pas en ligne \u2620");
                        }
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(StrawChest.getInstance().getPrefix() + "§cLe nombre de clés doit être compris entre 1 et 64");
                    }
                }
            } else {
                commandSender.sendMessage("§6§m----------------------------");
                commandSender.sendMessage("§e/" + s + " all <nom> <nombre> §8» §7Give a tous les joueurs en ligne des clées");
                commandSender.sendMessage("§e/" + s + " to <joueur> <nom> <nombre> §8» §7Give a un joueur en ligne des clées");
                commandSender.sendMessage("§e/" + s + " list §8» §7Liste des clées");
                commandSender.sendMessage("§6§m----------------------------");
            }
        } else {
            commandSender.sendMessage("§6§m----------------------------");
            commandSender.sendMessage("§e/" + s + " all <nom> <nombre> §8» §7Give a tous les joueurs en ligne des clées");
            commandSender.sendMessage("§e/" + s + " to <joueur> <nom> <nombre> §8» §7Give a un joueur en ligne des clées");
            commandSender.sendMessage("§e/" + s + " list §8» §7Liste des clées");
            commandSender.sendMessage("§6§m----------------------------");
        }
        return true;
    }
}
