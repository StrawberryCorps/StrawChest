package bzh.strawberry.chest.api.manager;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface IItemToclaim {

    Map<Integer, ItemStack> getMap();

    void removeItem(int ind);

    void addItem(Integer id, ItemStack item);

    int addNouvelItem(ItemStack itemStack);
}
