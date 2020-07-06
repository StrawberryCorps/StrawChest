package bzh.strawberry.chest.core.gui;


import bzh.strawberry.api.gui.AbstractInterface;
import bzh.strawberry.api.util.ItemStackBuilder;
import bzh.strawberry.api.util.SkullItemBuilder;
import bzh.strawberry.chest.api.manager.IItemToclaim;
import bzh.strawberry.chest.core.StrawChest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Set;

/*
 * This file ItemToClaimGUI is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:02 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ItemToClaimGUI extends AbstractInterface {

    private final Player player;
    private int page;
    private final IItemToclaim itemToclaim;

    public ItemToClaimGUI(Player player, IItemToclaim itemToclaim) {
        super("[Vote] » En attente", 54, player);
        this.player = player;
        this.page = 0;
        this.itemToclaim = itemToclaim;
    }

    public void show(Player player) {
        this.inventory.clear();
        int[] bord = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 26, 27, 35, 36, 37, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
        int[] zone = {11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42};
        for (int casetoFill : bord)
            this.addItem(new ItemStackBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1, ""), casetoFill, null);

        // @TODO
//        if (this.page != 0)
//            this.addItem(new SkullItemBuilder(null, "http://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9", "§3Page précédente"), 45, "page_precedente");
//
//        if (this.page != pageMax())
//            this.addItem(new SkullItemBuilder(null, "http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf", "§3Page suivante"), 53, "page_suivante");

        int i = 0;
        Set<Integer> set = this.itemToclaim.getMap().keySet();
        while (i < zone.length && this.itemToclaim.getMap().size() > (this.page * zone.length) + i && set.toArray(new Integer[0])[(this.page * zone.length) + i] != null) {
            this.addItem(this.itemToclaim.getMap().get(set.toArray(new Integer[0])[(this.page * zone.length) + i]), zone[i], String.valueOf(set.toArray(new Integer[0])[(this.page * zone.length) + i]));
            i++;
        }

    }

    @Override
    public void onInventoryClose(Player player) {
    }

    public void onInventoryClick(Player player, ItemStack stack, ClickType clickType, String action) {
        if (action != null) {
            if (action.equalsIgnoreCase("page_suivante")) {
                this.page++;
                this.show(player);
            } else if (action.equalsIgnoreCase("page_precedente")) {
                this.page--;
                this.show(player);
            } else {
                ItemStack obj = this.itemToclaim.getMap().get(Integer.parseInt(action));
                if (stack != null) {
                    if (this.player.getInventory().firstEmpty() != -1 || (this.player.getInventory().contains(stack.getType()) && Objects.requireNonNull(this.player.getInventory().getItem(this.player.getInventory().first(obj))).getAmount() + obj.getAmount() > 64)) {
                        if (this.player.getInventory().firstEmpty() != -1)
                            this.player.getInventory().setItem(this.player.getInventory().firstEmpty(), obj);
                        else
                            this.player.getInventory().setItem(this.player.getInventory().first(obj), obj);
                        this.itemToclaim.removeItem(Integer.parseInt(action));

                        if (page > pageMax())
                            page--;
                        this.show(this.player);
                    } else {
                        this.player.sendMessage(StrawChest.INSTANCE.getPrefix() + "§cImpossible de donner cet item. \u2620");
                    }
                }
            }

        }

    }

    private int pageMax() {
        return (this.itemToclaim.getMap().size() - 1) / 20;
    }

}
