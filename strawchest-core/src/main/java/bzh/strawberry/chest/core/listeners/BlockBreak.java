package bzh.strawberry.chest.core.listeners;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.chest.core.gui.RewardGUI;
import bzh.strawberry.chest.core.StrawChest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/*
 * This file BlockBreak is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:04 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.CHEST && StrawChest.INSTANCE.getChest(event.getBlock().getLocation()) != null) {
            event.setCancelled(true);
            StrawAPI.getAPI().getInterfaceManager().openInterface(new RewardGUI(StrawChest.INSTANCE.getChest(event.getBlock().getLocation()), player), player);
        }
    }
}
