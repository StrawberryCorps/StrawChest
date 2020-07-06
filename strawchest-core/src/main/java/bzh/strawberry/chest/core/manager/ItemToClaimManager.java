package bzh.strawberry.chest.core.manager;


import bzh.strawberry.chest.api.manager.IItemToclaim;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/*
 * This file ItemToClaimManager is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:10 by Uicias.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ItemToClaimManager extends bzh.strawberry.chest.api.manager.ItemToClaimManager {

    @Override
    public void addPlayer(Player player, Map<Integer, ItemStack> mapParam, String nomBDD, int local) {
        map.put(player, new ItemToClaim(player, mapParam, nomBDD, local));
    }

    public IItemToclaim getItemToClaim(Player player) {
        return map.get(player);
    }

    public void addItem(Player p, int id, ItemStack itemStack) {
        map.get(p).addItem(id, itemStack);
    }

    public void removePlayer(Player p) {
        map.remove(p);
    }

}
