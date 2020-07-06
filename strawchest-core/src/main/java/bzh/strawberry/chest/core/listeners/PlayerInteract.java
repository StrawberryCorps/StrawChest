package bzh.strawberry.chest.core.listeners;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.chest.api.StrawChest;
import bzh.strawberry.chest.core.gui.RewardGUI;
import bzh.strawberry.chest.core.manager.Chest;
import bzh.strawberry.chest.core.manager.ItemToClaimManager;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

/*
 * This file PlayerInteract is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:04 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerInteract implements Listener {

    private final ArrayList<UUID> playerOpening = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(bzh.strawberry.chest.core.StrawChest.INSTANCE, "coffre"), PersistentDataType.STRING))
            event.setCancelled(true);
        if (event.getClickedBlock() != null && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.CHEST) && bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(event.getClickedBlock().getLocation()) != null) {
            event.setCancelled(true);
            if (player.getInventory().getItemInMainHand().getItemMeta() != null && bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(event.getClickedBlock().getLocation()) != null) {
                if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(bzh.strawberry.chest.core.StrawChest.INSTANCE, "coffre"), PersistentDataType.STRING) && bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(event.getClickedBlock().getLocation()) != null) {
                    String chest = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(bzh.strawberry.chest.core.StrawChest.INSTANCE, "coffre"), PersistentDataType.STRING);
                    if (chest != null && bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(chest) != null && bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(event.getClickedBlock().getLocation()).getFlatName().toLowerCase().equals(chest.toLowerCase())) {
                        if (!playerOpening.contains(player.getUniqueId())) {
                            playerOpening.add(player.getUniqueId());
                            chestAnimation(event.getClickedBlock().getLocation(), true, player);

                            Chest c = bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(chest);

                            ItemStack newItem = player.getInventory().getItemInMainHand().clone();
                            int amount = player.getInventory().getItemInMainHand().getAmount() - 1;
                            if (amount <= 0)
                                newItem = new ItemStack(Material.AIR);
                            else
                                newItem.setAmount(amount);
                            player.getInventory().setItemInMainHand(newItem);

                            int r = (int) (Math.random() * (c.getMaximumReward() - c.getMinimumReward())) + c.getMinimumReward();
                            ItemStack itemToGrant;
                            for (int i = 0; i < r; i++) {
                                itemToGrant = c.getRewards().random();

                                if (itemToGrant.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StrawChest.getInstance(), "titre"), PersistentDataType.STRING)) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "titreadmin " + player.getName() + " grant " + itemToGrant.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(bzh.strawberry.chest.core.StrawChest.INSTANCE, "titre"), PersistentDataType.STRING));
                                    Bukkit.broadcastMessage(bzh.strawberry.chest.core.StrawChest.INSTANCE.getPrefix() + "§e" + player.getName() + "§b vient d'obtenir le titre du coffre §8§l" + bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(chest).getFlatName() + " §e/store");
                                } else if (itemToGrant.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StrawChest.getInstance(), "argent"), PersistentDataType.INTEGER)) {
                                    // @TODO eco
                                    player.sendMessage(bzh.strawberry.chest.core.StrawChest.INSTANCE.getPrefix() + "§7Vous avez gagné§e " + itemToGrant.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StrawChest.getInstance(), "argent"), PersistentDataType.INTEGER) + " Ecus §7dans le coffre.");
                                } else {

                                    if (itemToGrant.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StrawChest.getInstance(), "coffre"), PersistentDataType.STRING))
                                        player.sendMessage(bzh.strawberry.chest.core.StrawChest.getInstance().getPrefix() + "§7Vous venez de recevoir §e" + itemToGrant.getAmount() + " " + itemToGrant.getItemMeta().getDisplayName());
                                    if (player.getInventory().firstEmpty() != -1)
                                        player.getInventory().addItem(itemToGrant);
                                    else
                                        ItemToClaimManager.INSTANCE.getItemToClaim(player).addItem(ItemToClaimManager.INSTANCE.getItemToClaim(player).addNouvelItem(itemToGrant), itemToGrant);
                                }

                            }
                            if (c.getOpenMessage() != null)
                                bzh.strawberry.chest.core.StrawChest.INSTANCE.getServer().getOnlinePlayers().forEach(o -> o.sendMessage(c.getOpenMessage().replace("{player}", player.getName())));

                            bzh.strawberry.chest.core.StrawChest.INSTANCE.getServer().getScheduler().runTaskLaterAsynchronously(bzh.strawberry.chest.core.StrawChest.INSTANCE, () -> {
                                playerOpening.remove(player.getUniqueId());
                                chestAnimation(event.getClickedBlock().getLocation(), false, player);
                            }, 30);
                        }
                    } else {
                        player.setVelocity(player.getEyeLocation().getDirection().multiply(-1.3));
                        player.sendMessage(bzh.strawberry.chest.core.StrawChest.INSTANCE.getPrefix() + "§cVous devez avoir une clée pour ouvrir ce coffre \u2620");
                        EntityLightning entityLightning = new EntityLightning(((CraftPlayer) player).getHandle().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 0.5, true);
                        entityLightning.setOnFire(0);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityWeather(entityLightning));
                    }
                } else {
                    player.setVelocity(player.getEyeLocation().getDirection().multiply(-1.3));
                    player.sendMessage(bzh.strawberry.chest.core.StrawChest.INSTANCE.getPrefix() + "§cVous devez avoir une clée pour ouvrir ce coffre \u2620");
                    EntityLightning entityLightning = new EntityLightning(((CraftPlayer) player).getHandle().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 0.5, true);
                    entityLightning.setOnFire(0);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityWeather(entityLightning));
                }
            } else {
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-1.3));
                player.sendMessage(bzh.strawberry.chest.core.StrawChest.INSTANCE.getPrefix() + "§cVous devez avoir une clée pour ouvrir ce coffre \u2620");
                EntityLightning entityLightning = new EntityLightning(((CraftPlayer) player).getHandle().getWorld(), event.getClickedBlock().getLocation().getX() + 0.5, event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ() + 0.5, true);
                entityLightning.setOnFire(0);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityWeather(entityLightning));
            }
        }
        if (event.getClickedBlock() != null && event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.CHEST) && bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(event.getClickedBlock().getLocation()) != null) {
            event.setCancelled(true);
            StrawAPI.getAPI().getInterfaceManager().openInterface(new RewardGUI(bzh.strawberry.chest.core.StrawChest.INSTANCE.getChest(event.getClickedBlock().getLocation()), player), player);
        }
    }

    private static void chestAnimation(Location chest, boolean open, Player player) {
        BlockPosition pos = new BlockPosition(chest.getBlockX(), chest.getBlockY(), chest.getBlockZ());
        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(pos, Blocks.CHEST, 1, (open ? 1 : 0));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
