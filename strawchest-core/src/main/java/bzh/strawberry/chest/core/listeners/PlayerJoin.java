package bzh.strawberry.chest.core.listeners;

import bzh.strawberry.chest.core.manager.Chest;
import bzh.strawberry.chest.core.manager.ItemToClaimManager;
import bzh.strawberry.chest.core.StrawChest;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

/*
 * This file PlayerJoin is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:07 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Chest chest : StrawChest.INSTANCE.getChests()) {
            if (chest.getHologram() != null)
                chest.getHologram().addReceiver(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (Chest chest : StrawChest.INSTANCE.getChests()) {
            if (chest.getHologram() != null)
                chest.getHologram().removeReceiver(player);
        }
        //pas de save ici, les items étant retirés les uns apres les autres de la bdd au claim
        ItemToClaimManager.INSTANCE.removePlayer(player);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType() == Material.TRIPWIRE_HOOK) {
            if (event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StrawChest.INSTANCE, "coffre"), PersistentDataType.STRING)) {
                if (StrawChest.INSTANCE.getChest(event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StrawChest.INSTANCE, "coffre"), PersistentDataType.STRING)) != null)
                    StrawChest.INSTANCE.getChest(event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StrawChest.INSTANCE, "coffre"), PersistentDataType.STRING)).getHologram().setLinesChanged(true);
            }
        }
    }
}
