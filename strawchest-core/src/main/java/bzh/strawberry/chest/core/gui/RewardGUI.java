package bzh.strawberry.chest.core.gui;

import bzh.strawberry.api.gui.AbstractInterface;
import bzh.strawberry.chest.api.StrawChest;
import bzh.strawberry.chest.core.manager.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;


/*
 * This file RewardGUI is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:02 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class RewardGUI extends AbstractInterface {

    private final Chest chest;

    public RewardGUI(Chest chest, Player player) {
        super("[Coffre] » " + chest.getFlatName(), 27, player);
        this.chest = chest;
    }

    public void show(Player player) {
        this.inventory.clear();
        DecimalFormat decimalFormat = new DecimalFormat("##.00");

        int slot = 0;
        for (ItemStack itemStack : chest.getItemStackHashMap().keySet()) {
            double percentage = chest.getItemStackHashMap().get(itemStack);
            ItemStack itemStack1 = itemStack.clone();
            ItemMeta itemMeta = itemStack1.clone().getItemMeta();
            if (percentage % 1 != 0d)
                itemMeta.setLore(Arrays.asList("§7Chance: §3" + ((int) percentage + "%")));
            else
                itemMeta.setLore(Arrays.asList("§7Chance: §3" + (decimalFormat.format(percentage) + "%")));
            itemStack1.setItemMeta(itemMeta);
            this.addItem(itemStack1, slot, null);
            slot++;
        }
    }

    public void onInventoryClick(Player player, ItemStack stack, ClickType clickType, String action) {
        if (action.isEmpty()) return;
    }

    @Override
    public void onInventoryClose(Player player) {
    }
}
