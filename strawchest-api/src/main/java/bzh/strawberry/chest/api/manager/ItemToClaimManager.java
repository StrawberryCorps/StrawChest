package bzh.strawberry.chest.api.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class ItemToClaimManager {

    public static ItemToClaimManager INSTANCE;
    protected static HashMap<Player, IItemToclaim> map;

    public ItemToClaimManager() {
        INSTANCE = this;
        map = new HashMap<>();
    }

    public static ItemToClaimManager getInstance() {
        return INSTANCE;
    }

    public abstract void addPlayer(Player player, Map<Integer, ItemStack> map, String nomBDD, int local);

    public abstract IItemToclaim getItemToClaim(Player player);

    public abstract void addItem(Player p, int id, ItemStack itemStack);

    public abstract void removePlayer(Player p);
}
