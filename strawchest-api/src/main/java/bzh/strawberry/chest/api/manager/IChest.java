package bzh.strawberry.chest.api.manager;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface IChest {

    String getName();

    AnimationType getType();

    Location getLocation();

    void setMaximumReward(int value);

    int getMaximumReward();

    void setMinimumReward(int value);

    int getMinimumReward();

    void setOpenMessage(String message);

    void addItem(ItemStack itemStack, double percentage);
}