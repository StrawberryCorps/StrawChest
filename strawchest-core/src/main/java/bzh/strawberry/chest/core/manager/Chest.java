package bzh.strawberry.chest.core.manager;

import bzh.strawberry.chest.core.utils.Hologram;
import bzh.strawberry.chest.core.utils.RandomCollection;
import bzh.strawberry.chest.api.manager.AnimationType;
import bzh.strawberry.chest.api.manager.IChest;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashMap;

/*
 * This file Chest is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:07 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class Chest implements IChest {

    private final String name;
    private int maxReward;
    private int minReward;
    private final Location location;
    private final AnimationType animationType;
    private String openMessage;
    private final String flatName;
    private Hologram hologram;

    private final RandomCollection<ItemStack> rewards;
    private final HashMap<ItemStack, Double> itemStackIntegerHashMap;

    public Chest(String name, String flatName, Location location, AnimationType animationType) {
        this.name = name;
        this.flatName = flatName;
        this.location = location;
        this.animationType = animationType;
        this.rewards = new RandomCollection<>();
        this.itemStackIntegerHashMap = new LinkedHashMap<>();
        setMaximumReward(1);
        setMinimumReward(1);
        if (animationType == AnimationType.STANDARD)
            hologram = new Hologram("§a■ " + name + " §a■", "§e» §7Clic droit §aOuvrir", "§e» §7Clic gauche §aRécompense");
        else if (animationType == AnimationType.ASSEZ_IMPORTANT)
            hologram = new Hologram("§3■ " + name + " §3■", "§e» §7Clic droit §aOuvrir", "§e» §7Clic gauche §aRécompense");
        else if (animationType == AnimationType.IMPORTANT)
            hologram = new Hologram("§d■ " + name + " §d■", "§e» §7Clic droit §aOuvrir", "§e» §7Clic gauche §aRécompense");
        else if (animationType == AnimationType.EVENEMENTIEL)
            hologram = new Hologram("§e■ " + name + " §e■", "§e» §7Clic droit §aOuvrir", "§e» §7Clic gauche §aRécompense");
        hologram.setChest(flatName);
        hologram.generateLines(location.clone().add(0.5, 2.5, 0.5));
        hologram.sendLinesForPlayers();
    }

    public String getFlatName() {
        return flatName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AnimationType getType() {
        return animationType;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setMaximumReward(int value) {
        this.maxReward = value;
    }

    @Override
    public int getMaximumReward() {
        return this.maxReward;
    }

    @Override
    public void setMinimumReward(int value) {
        this.minReward = value;
    }

    @Override
    public int getMinimumReward() {
        return this.minReward;
    }

    @Override
    public void setOpenMessage(String message) {
        this.openMessage = message;
    }

    public String getOpenMessage() {
        return openMessage;
    }

    @Override
    public void addItem(ItemStack itemStack, double percentage) {
        this.rewards.add(percentage, itemStack);
        this.itemStackIntegerHashMap.put(itemStack, percentage);
    }

    public RandomCollection<ItemStack> getRewards() {
        return rewards;
    }

    public HashMap<ItemStack, Double> getItemStackHashMap() {
        return itemStackIntegerHashMap;
    }

    public Hologram getHologram() {
        return hologram;
    }
}
